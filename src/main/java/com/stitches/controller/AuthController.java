package com.stitches.controller;

import com.stitches.security.JwtService;
import com.stitches.dto.request.AuthenticationRequest;
import com.stitches.dto.request.RegisterRequest;
import com.stitches.dto.response.AuthenticationResponse;
import com.stitches.dto.response.HttpResponse;
import com.stitches.dto.response.RegistrationResponse;
import com.stitches.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(UserService userService){
        this.userService = userService;
    }

        @PostMapping("/authenticate")
    public ResponseEntity<HttpResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
            System.out.println("first");
            log.info("second");
            log.info("third : {}" , "third");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .accessToken(jwtService.generateToken(request.getEmail()))
                    .build();
            Map<String, AuthenticationResponse> authRes = new HashMap<>();
            authRes.put("data", authenticationResponse);
            HttpResponse httpResponse  = HttpResponse.builder()
                    .statusCode(200)
                    .timeStamp(LocalDateTime.now().toString())
                    .message("User succesfully Logged in")
                    .data(authRes)
                    .build();

            return ResponseEntity.ok(httpResponse);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/hi")
    public String printHi(){
        return "Hello World";
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(
            @RequestBody RegisterRequest request
    ) {

        RegistrationResponse registrationResponse =
                userService.register(request);

        Map<String, RegistrationResponse> regRes = new HashMap<>();
        regRes.put("data", registrationResponse);

        HttpResponse httpResponse = HttpResponse.builder()
                .statusCode(201)
                .message("User created successfully")
                .status(HttpStatus.CREATED)
                .data(regRes)
                .build();
        return ResponseEntity.ok(httpResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<String> getString(){

        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<HttpResponse> getMerchants(@RequestParam Optional<String> name,
                                                     @RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<Integer> size) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page", userService.getUsers(name.orElse(""), page.orElse(0), size.orElse(10)));

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(responseData)  // Use the created Map here
                        .message("Merchants Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
