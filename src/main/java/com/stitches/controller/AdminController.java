package com.stitches.controller;

import com.stitches.dto.request.UserRequest;
import com.stitches.dto.response.HttpResponse;
import com.stitches.dto.response.UserResponse;
import com.stitches.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vi/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/findUserByEmail")
    public ResponseEntity<HttpResponse> findUserByEmail(@RequestParam String email){
        Optional<UserResponse> optionalUser =  userService.findAppUserByEmail(email);

        Map<String, Optional<UserResponse>> appUserMap = new HashMap<>();
        appUserMap.put("result", optionalUser);
        HttpResponse httpResponse = HttpResponse.builder()
                .message("successful")
                .data(appUserMap)
                .status(HttpStatus.OK)
                .statusCode(200)
                .build();

        return ResponseEntity.ok(httpResponse);
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<HttpResponse> updateUser(@RequestParam String email, @RequestBody UserRequest userRequest){
        UserResponse userResponse =  userService.updateUser(email, userRequest);

        Map<String,UserResponse> userResponseMap = new HashMap<>();
        userResponseMap.put("result", userResponse);
        HttpResponse httpResponse = HttpResponse.builder()
                .message("successful")
                .data(userResponseMap)
                .status(HttpStatus.OK)
                .statusCode(200)
                .build();
        return ResponseEntity.ok(httpResponse);
    }


}
