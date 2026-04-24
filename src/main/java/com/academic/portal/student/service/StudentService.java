package com.academic.portal.student.service;

import com.academic.portal.address.repository.AddressRepository;
import com.academic.portal.common.exception.BusinessException;
import com.academic.portal.common.exception.ErrorCode;
import com.academic.portal.entity.Address;
import com.academic.portal.entity.Student;
import com.academic.portal.entity.User;
import com.academic.portal.enums.IdentityType;
import com.academic.portal.enums.Role;
import com.academic.portal.student.dto.StudentRequestDto;
import com.academic.portal.student.dto.StudentResponseDto;
import com.academic.portal.student.mapper.StudentMapper;
import com.academic.portal.student.repository.StudentRepository;
import com.academic.portal.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final StudentMapper studentMapper;


    @Transactional
    public StudentResponseDto addStudent(StudentRequestDto dto) {

        validateIdentity(dto.getIdentityType(), dto.getNationalId(), dto.getPassportNumber());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Address address = null;

        if (dto.getAddressName() != null && !dto.getAddressName().isBlank()) {

            String name = dto.getAddressName().trim();

            address = addressRepository.findByAddressName(name)
                    .orElseGet(() -> {
                        Address newAddress = new Address();
                        newAddress.setAddressName(name);
                        return addressRepository.save(newAddress);
                    });
        }

        Student student = new Student();
        student.setUser(user);
        user.setRole(Role.STUDENT);
        student.setAddress(address);
        student.setIdentityType(dto.getIdentityType());
        student.setNationalId(dto.getNationalId());
        student.setPassportNumber(dto.getPassportNumber());


        Student saved = studentRepository.save(student);

        return studentMapper.toDto(saved);
    }


    public List<StudentResponseDto> getAllStudents() {
        return studentMapper.toDtoList(studentRepository.findAll());
    }


    private void validateIdentity(IdentityType type, String nationalId, String passportNumber) {

        if (type == IdentityType.NATIONAL_ID) {
            if (nationalId == null || passportNumber != null) {
                throw new BusinessException(ErrorCode.INVALID_NATIONAL_ID);
            }
        } else if (type == IdentityType.PASSPORT) {
            if (passportNumber == null || nationalId != null) {
                throw new BusinessException(ErrorCode.INVALID_PASSPORT_NUMBER);
            }
        }
    }
}