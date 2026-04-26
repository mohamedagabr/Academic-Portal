package com.academic.portal.user.service;

import com.academic.portal.auth.dto.AuthResponse;
import com.academic.portal.entity.User;
import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.DeletedFlag;
import com.academic.portal.user.dto.UserRequestDto;
import com.academic.portal.user.dto.UserResponseDto;
import com.academic.portal.entity.RefreshToken;
import com.academic.portal.enums.Role;
import com.academic.portal.common.exception.BusinessException;
import com.academic.portal.common.exception.ErrorCode;
import com.academic.portal.auth.mapper.AuthMapper;
import com.academic.portal.user.mapper.UserMapper;
import com.academic.portal.token.service.RefreshTokenService;
import com.academic.portal.user.repository.UserRepository;
import com.academic.portal.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthMapper authMapper;

    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        return userMapper.toDtoList(users);
    }

    public UserResponseDto getUserById(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toDto(user);
    }

    public AuthResponse createUser(UserRequestDto request) {

        createUserValidation(request);

        User user = User.builder()

                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .mobileNumber(request.getMobileNumber())
                .gender(request.getGender())
                .isActive(ActiveStatus.ACTIVE)
                .isDeleted(DeletedFlag.NO)
                .build();

        userRepository.save(user);

        User savedUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));


        String accessToken = jwtService.generateAccessToken(savedUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser);

        AuthResponse response = authMapper.toAuthResponse(savedUser);
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken.getToken());

        return response;
    }

    public UserResponseDto updateUser(Integer id, UserRequestDto request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        updateUserValidation(request , user);

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setGender(request.getGender());
        user.setRole(request.getRole());
        user.setIsActive(request.getIsActive());


        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public void softDelete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.setIsDeleted(DeletedFlag.YES);
        user.setIsActive(ActiveStatus.INACTIVE);

        userRepository.save(user);
    }


    private void createUserValidation(UserRequestDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.existsByMobileNumber(dto.getMobileNumber())) {
            throw new BusinessException(ErrorCode.MOBILE_NUMBER_ALREADY_EXISTS);
        }
    }

    private void updateUserValidation(UserRequestDto dto , User existing) {

        if(dto.getUsername() != null &&
                !dto.getUsername().equals(existing.getUsername()) &&
                userRepository.existsByUsername(dto.getUsername())){
           throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);

        }

        if(dto.getEmail() != null &&
                !dto.getEmail().equals(existing.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())){
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);

        }

        if(dto.getMobileNumber() != null &&
                !dto.getMobileNumber().equals(existing.getMobileNumber()) &&
                userRepository.existsByMobileNumber(dto.getEmail())){
            throw new BusinessException(ErrorCode.MOBILE_NUMBER_ALREADY_EXISTS);

        }


    }




}
