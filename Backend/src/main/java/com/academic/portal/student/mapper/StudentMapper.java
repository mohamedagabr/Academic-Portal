package com.academic.portal.student.mapper;

import com.academic.portal.entity.Address;
import com.academic.portal.entity.Student;
import com.academic.portal.entity.User;
import com.academic.portal.student.dto.StudentRequestDto;
import com.academic.portal.student.dto.StudentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "addressName", source = "address.addressName")
    StudentResponseDto toDto(Student student);

    List<StudentResponseDto> toDtoList(List<Student> students);

    @Mapping(target = "studentId", ignore = true)
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "address", source = "addressName")
    Student toEntity(StudentRequestDto dto);

    default Address mapAddress(String addressName) {
        if (addressName == null) return null;

        Address address = new Address();
        address.setAddressName(addressName);
        return address;
    }
    default User mapUser(Integer userId) {
        if (userId == null) return null;

        User user = new User();
        user.setUserId(userId);
        return user;
    }
}
