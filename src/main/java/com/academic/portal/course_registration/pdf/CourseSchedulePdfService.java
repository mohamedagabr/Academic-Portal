package com.academic.portal.course_registration.pdf;

import com.academic.portal.course.repository.CourseRepository;
import com.academic.portal.course_registration.dto.CourseScheduleReportDto;
import com.academic.portal.entity.Course;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class CourseSchedulePdfService {

    private final CourseRepository courseRepository;

    @Cacheable(
            value = "course_schedule",
            key = "#departmentId != null ? #departmentId : 'ALL'"
    )
    public byte[] generatePdf(Integer departmentId) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Course Schedule Report", titleFont));
            document.add(new Paragraph(" "));

            Map<String, List<CourseScheduleReportDto>> data =
                    getReport(departmentId)
                            .stream()
                            .collect(Collectors.groupingBy(
                                    CourseScheduleReportDto::getDepartmentName,
                                    TreeMap::new,
                                    Collectors.toList()
                            ));

            for (var entry : data.entrySet()) {

                // Department Header
                Font deptFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                document.add(new Paragraph("Department: " + entry.getKey(), deptFont));
                document.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);

                Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
                Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

                // Headers
                table.addCell(createCell("Course", headerFont));
                table.addCell(createCell("Code", headerFont));
                table.addCell(createCell("Capacity", headerFont));
                table.addCell(createCell("Registered", headerFont));
                table.addCell(createCell("Available", headerFont));

                // Data rows
                entry.getValue().stream()
                        .sorted(Comparator.comparing(CourseScheduleReportDto::getCourseName))
                        .forEach(dto -> {

                            table.addCell(createCell(dto.getCourseName(), cellFont));
                            table.addCell(createCell(dto.getCourseCode(), cellFont));
                            table.addCell(createCell(String.valueOf(dto.getCapacity()), cellFont));
                            table.addCell(createCell(String.valueOf(dto.getRegisteredCount()), cellFont));
                            table.addCell(createCell(String.valueOf(dto.getAvailableSeats()), cellFont));
                        });

                document.add(table);
                document.add(new Paragraph(" "));
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }


    private PdfPCell createCell(String text, Font font) {

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);

        return cell;
    }


    public List<CourseScheduleReportDto> getReport(Integer departmentId) {

        List<Object[]> rows = courseRepository.getCourseScheduleData(departmentId);

        return rows.stream().map(row -> {

            Integer courseId = (Integer) row[0];
            String courseName = (String) row[1];
            String courseCode = (String) row[2];
            Integer capacity = (Integer) row[3];
            String deptName = (String) row[4];
            Long registered = (Long) row[5];

            long available = capacity - registered;

            return new CourseScheduleReportDto(
                    null,
                    deptName,
                    courseName,
                    courseCode,
                    capacity,
                    registered,
                    available
            );

        }).toList();
    }



}
