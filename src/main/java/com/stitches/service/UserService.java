package com.stitches.service;

import com.stitches.dto.request.AuthenticationRequest;
import com.stitches.dto.request.RegisterRequest;
import com.stitches.dto.request.UserRequest;
import com.stitches.dto.response.AuthenticationResponse;
import com.stitches.dto.response.RegistrationResponse;
import com.stitches.dto.response.UserResponse;
import com.stitches.model.AppUser;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
    RegistrationResponse register(RegisterRequest request);
    Page<AppUser> getUsers(String name, int page, int size);
    Optional<UserResponse> findAppUserByEmail(String email);
    UserResponse updateUser(String email, UserRequest userRequest);

}
