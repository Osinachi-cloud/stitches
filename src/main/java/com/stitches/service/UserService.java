package com.stitches.service;

import com.stitches.dto.request.*;
import com.stitches.dto.response.AppUserResponse;
import com.stitches.dto.response.AuthenticationResponse;
import com.stitches.dto.response.RegistrationResponse;
import com.stitches.dto.response.UserResponse;
import com.stitches.enums.Role;
import com.stitches.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    Authentication authenticate(AuthenticationRequest request);
    RegistrationResponse register(AppUserRequest request) throws IOException;
    boolean verifyOTP(String email, String otp);
    Page<AppUser> getUsers(String name, int page, int size);
    Optional<UserResponse> findAppUserByEmail(String email);
    UserResponse updateUser(String email, UserRequest userRequest);

    Page<AppUserResponse> getCustomers(Role role, int page, int size);

    boolean forgotPassword(String email) throws IOException;

    Boolean newPassword(String email, NewPasswordRequest newPasswordRequest);

    Boolean resetPassword(String email, ResetPasswordRequest resetPasswordRequest);
}
