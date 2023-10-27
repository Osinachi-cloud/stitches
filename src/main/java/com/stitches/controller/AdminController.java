package com.stitches.controller;

import com.stitches.dto.request.UserRequest;
import com.stitches.dto.response.HttpResponse;
import com.stitches.dto.response.UserResponse;
import com.stitches.enums.Role;
import com.stitches.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;

    private Logger log = LoggerFactory.getLogger(AdminController.class);

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
                .status(OK)
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
                .status(OK)
                .statusCode(200)
                .build();
        return ResponseEntity.ok(httpResponse);
    }


    @GetMapping("/customers")
    public ResponseEntity<HttpResponse> getMerchants(
            @RequestParam(required = false) String role,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        Map<String, Object> responseData = new HashMap<>();
        log.info("users : {}", userService.getCustomers(Role.valueOf(role), page.orElse(0), size.orElse(10)));
        responseData.put("page", userService.getCustomers(Role.valueOf(role), page.orElse(0), size.orElse(10)));

        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp(now().toString())
                .data(responseData)  // Use the created Map here
                .message("customers Retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build();
        log.info("httpResponse : {}", httpResponse);
        return ResponseEntity.ok().body(httpResponse);
    }


}
