package com.stitches.controller;


import com.stitches.dto.request.ResetPasswordRequest;
import com.stitches.dto.response.HttpResponse;
import com.stitches.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(@RequestParam(name="email", required = true) String email, @RequestBody ResetPasswordRequest resetPasswordRequest) throws IOException {

        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("result", userService.resetPassword(email, resetPasswordRequest));

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(responseData)  // Use the created Map here
                        .message("Password changed successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
}
