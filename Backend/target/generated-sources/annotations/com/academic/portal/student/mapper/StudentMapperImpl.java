package com.academic.portal.student.mapper;

import com.academic.portal.entity.Address;
import com.academic.portal.entity.Student;
import com.academic.portal.entity.User;
import com.academic.portal.student.dto.StudentRequestDto;
import com.academic.portal.student.dto.StudentResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-23T12:15:37+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentResponseDto toDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponseDto.StudentResponseDtoBuilder studentResponseDto = StudentResponseDto.builder();

        studentResponseDto.userId( studentUserUserId( student ) );
        studentResponseDto.firstName( studentUserFirstName( student ) );
        studentResponseDto.lastName( studentUserLastName( student ) );
        studentResponseDto.addressName( studentAddressAddressName( student ) );
        studentResponseDto.studentId( student.getStudentId() );
        studentResponseDto.nationalId( student.getNationalId() );
        studentResponseDto.passportNumber( student.getPassportNumber() );
        studentResponseDto.identityType( student.getIdentityType() );

        return studentResponseDto.build();
    }

    @Override
    public List<StudentResponseDto> toDtoList(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<StudentResponseDto> list = new ArrayList<StudentResponseDto>( students.size() );
        for ( Student student : students ) {
            list.add( toDto( student ) );
        }

        return list;
    }

    @Override
    public Student toEntity(StudentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Student.StudentBuilder student = Student.builder();

        student.user( mapUser( dto.getUserId() ) );
        student.address( mapAddress( dto.getAddressName() ) );
        student.nationalId( dto.getNationalId() );
        student.passportNumber( dto.getPassportNumber() );
        student.identityType( dto.getIdentityType() );

        return student.build();
    }

    private Integer studentUserUserId(Student student) {
        if ( student == null ) {
            return null;
        }
        User user = student.getUser();
        if ( user == null ) {
            return null;
        }
        Integer userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private String studentUserFirstName(Student student) {
        if ( student == null ) {
            return null;
        }
        User user = student.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String studentUserLastName(Student student) {
        if ( student == null ) {
            return null;
        }
        User user = student.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String studentAddressAddressName(Student student) {
        if ( student == null ) {
            return null;
        }
        Address address = student.getAddress();
        if ( address == null ) {
            return null;
        }
        String addressName = address.getAddressName();
        if ( addressName == null ) {
            return null;
        }
        return addressName;
    }
}
