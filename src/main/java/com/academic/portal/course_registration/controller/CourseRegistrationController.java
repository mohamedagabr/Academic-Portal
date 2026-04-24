package com.academic.portal.course_registration.controller;

import com.academic.portal.common.response.ApiResponse;
import com.academic.portal.course_registration.dto.CourseRegistrationRequestDto;
import com.academic.portal.course_registration.dto.CourseRegistrationResponseDto;
import com.academic.portal.course_registration.pdf.CourseSchedulePdfService;
import com.academic.portal.course_registration.service.CourseRegistrationService;
import com.academic.portal.enums.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course-registrations")
@RequiredArgsConstructor
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;
    private final CourseSchedulePdfService pdfService;


    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN','SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<CourseRegistrationResponseDto>> register(
            @RequestBody @Valid CourseRegistrationRequestDto dto) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.COURSE_REGISTRATION_ADDED,
                        courseRegistrationService.register(dto)
                )
        );
    }


    @PutMapping("/cancel")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN','SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<CourseRegistrationResponseDto>> cancel(
            @RequestBody @Valid CourseRegistrationRequestDto dto) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.REGISTRATION_CANCELED,
                        courseRegistrationService.cancel(dto)
                )
        );
    }



    @GetMapping("/course-schedule")
    public ResponseEntity<byte[]> downloadCourseSchedulePdf(
            @RequestParam(required = false) Integer departmentId
    ) {

        byte[] pdf = pdfService.generatePdf(departmentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename("course-schedule.pdf")
                        .build()
        );

        headers.setContentLength(pdf.length);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdf);
    }



//    @GetMapping("/schedule/pdf")
//    public ResponseEntity<byte[]> downloadPdf() {
//
//        byte[] pdf = pdfService.generateCourseSchedulePdf();
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=courses.pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdf);
//    }

}
