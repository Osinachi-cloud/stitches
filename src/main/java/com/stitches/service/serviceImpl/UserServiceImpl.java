package com.stitches.service.serviceImpl;

import com.stitches.dto.request.*;
import com.stitches.dto.response.AppUserResponse;
import com.stitches.enums.Role;
import com.stitches.security.JwtService;
import com.stitches.dto.response.RegistrationResponse;
import com.stitches.dto.response.UserResponse;
import com.stitches.exceptions.ApiRequestException;
import com.stitches.model.Address;
import com.stitches.model.AppUser;
import com.stitches.repository.UserRepository;
import com.stitches.service.UserService;
import com.stitches.utils.Helper;
import jakarta.transaction.Transactional;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final Helper helper;

    private final UserInfoUserDetailsService userDetailsService;

    public UserServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService, Helper helper, UserInfoUserDetailsService userDetailsService){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.helper = helper;
        this.userDetailsService = userDetailsService;
    }

    private UserResponse convertUserToDTO(AppUser user){
        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();
        return userResponse;
    }

    @Override
    public Page<AppUserResponse> getCustomers(
            Role role,
            int page,
            int size
    ) {

        Page<AppUser> merchantPage = userRepository.findAllByRole(role, of(page, size));

        ModelMapper modelMapper = new ModelMapper();
        // Create a mapping configuration
        TypeMap<AppUser, AppUserResponse> typeMap = modelMapper.createTypeMap(AppUser.class, AppUserResponse.class);

        Page<AppUserResponse> userResponseDtoPage = new PageImpl<>(
                merchantPage.getContent().stream()
                        .map(merchant -> modelMapper.map(merchant, AppUserResponse.class))
                        .collect(Collectors.toList()),
                merchantPage.getPageable(),
                merchantPage.getTotalElements()
        );

        return userResponseDtoPage;
    }

    @Override
    public boolean forgotPassword(String email) throws IOException {
        Optional<AppUser> optionalAppUser = userRepository.findAppUserByEmail(email);
        if(optionalAppUser.isEmpty()){
            throw new ApiRequestException("User does not exist");
        }
        if(!optionalAppUser.get().isOtpVerified()){
            throw new ApiRequestException("User has not confirmed OTP after registration, Go and register again or confirm your OTP");
        }
        String otp = helper.generateOtp();
        boolean isMailSent = helper.sendMail(email, otp, "Stitch Forgot Password OTP");
        if(isMailSent){
            AppUser user = optionalAppUser.get();
            user.setOtp(otp);
            userRepository.save(user);
        }

        return isMailSent;
    }

    @Override
    public Boolean newPassword(String email, NewPasswordRequest newPasswordRequest) {

        Optional<AppUser> optionalAppUser = userRepository.findAppUserByEmail(email);
        if(optionalAppUser.isEmpty()){
            throw new ApiRequestException("User does not exist");
        }

        if(!optionalAppUser.get().isOtpVerified()){
            throw new ApiRequestException("User has not confirmed OTP after registration, Go and register again or confirm your OTP");
        }
        if(!newPasswordRequest.getPassword().equals(newPasswordRequest.getConfirmPassword())){
            throw new ApiRequestException("Password does not match");
        }else {
            AppUser user = optionalAppUser.get();
            String newPassword = passwordEncoder.encode(newPasswordRequest.getPassword());
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public Boolean resetPassword(String email, ResetPasswordRequest resetPasswordRequest) {

        Optional<AppUser> optionalAppUser = userRepository.findAppUserByEmail(email);

        if(optionalAppUser.isEmpty()){
            throw new ApiRequestException("User does not exist");
        }
        AppUser user = optionalAppUser.get();

        if(!optionalAppUser.get().isOtpVerified()){
            throw new ApiRequestException("User has not confirmed OTP after registration, Go and register again or confirm your OTP");
        }

        if(!passwordEncoder.matches(resetPasswordRequest.getOldPassword(), user.getPassword())){
            throw new ApiRequestException("Your password does not match with existing password");
        }
        if(passwordEncoder.matches(resetPasswordRequest.getNewPassword(), user.getPassword())){
            throw new ApiRequestException("Your can not reuse the same password");
        }
        if(!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())){
            throw new ApiRequestException("Password does not match");
        }

        else {
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        }
    }

//    public AuthenticationResponse authenticate(AuthenticationRequest request){
//        UsernamePasswordAuthenticationToken authenticationToken;
//        try {
//            log.info("Authentication manager => {}",authenticationManager);
//             UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
//                authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
//            log.info("Auth token => {}",authenticationToken);
//                Authentication auth = authenticationManager.authenticate(authenticationToken);
//                log.info("auth ====<<< {} ====>",auth.isAuthenticated());
//                if(!auth.isAuthenticated()){
//                    throw new UsernameNotFoundException("invalid user request !");
//
//                }
//                System.out.println("Authentication successful.");
//
//        } catch (AuthenticationException e) {
//            System.out.println("Authentication failed: " + e.getMessage());
//        }catch (Exception e){
//            System.out.println("General Exception caught => "+e.getMessage());
//        }
//        AppUser user = userRepository.findAppUserByEmail(request.getEmail())
//                .orElseThrow();
//        var jwtToken = jwtService.generateToken(user.getUsername());
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .build();
//    }

    public Authentication authenticate(AuthenticationRequest request){

        Optional<AppUser> optionalAppUser = userRepository.findAppUserByEmail(request.getEmail());
        if(optionalAppUser.isEmpty()){
            throw new ApiRequestException("User does not exist");
        }
        if(optionalAppUser.get().isOtpVerified()){
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }else {
            throw new ApiRequestException("you have not completed your registration");
        }
    }

    @Transactional
    public RegistrationResponse register(AppUserRequest request) throws IOException {

            Optional<AppUser> userDetails = userRepository.findAppUserByEmail(request.getEmail());
            if(userDetails.isPresent() && userDetails.get().isOtpVerified()){
                throw new ApiRequestException("User already exists");
            }
            if(userDetails.isPresent() && !userDetails.get().isOtpVerified()) {
                userRepository.deleteById(userDetails.get().getId());
                return null;
            }
            else {
                Address address = Address.builder()
                        .city(request.getCity())
                        .country(request.getCountry())
                        .street(request.getStreet())
                        .state(request.getState())
                        .build();

                String otp = helper.generateOtp();
                boolean isMailSent = helper.sendMail(request.getEmail(), otp, "Stitch Otp Verification");

                AppUser user = AppUser.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .dateOfBirth(request.getDateOfBirth())
                        .mobile(request.getMobile())
                        .address(address)
                        .isOtpVerified(false)
                        .role(request.getRole())
                        .otp(otp)
                        .build();
                log.info("User Request ====>>>> {}", user);

                RegistrationResponse res = new RegistrationResponse();

                if(isMailSent){
                    AppUser savedUser = userRepository.save(user);
                    log.info("saved User ====>>>> {}", savedUser);
                    res.setResponse("User Successfully registered, an OTP has been sent to your email");
                }else {
                    res.setResponse("Registration was not successful, try again");
                }
                return res;
            }
    }

    public boolean verifyOTP(String email, String otp){

        Optional<AppUser> optionalAppUser = userRepository.findAppUserByEmail(email);
        if(optionalAppUser.isEmpty()){
            throw new ApiRequestException("User does not exist");
        }else {
            AppUser user = optionalAppUser.get();
            if(user.getOtp() == null){
                throw new ApiRequestException("You have already registered, you may proceed to login");
            }
            if(user.getOtp().equals(otp)){
                user.setOtp(null);
                user.setOtpVerified(true);
                userRepository.save(user);
                return true;
            }{
                throw new ApiRequestException("OTP does not match");
            }
        }
    }

    @Override
    public Page<AppUser> getUsers(String name, int page, int size) {
        log.info("Fetching users for page {} of size {}", page, size);
        return userRepository.findByEmailContaining(name, of(page, size));
    }

    @Override
    public Optional<UserResponse> findAppUserByEmail(String email){
        Optional<AppUser> user =  userRepository.findAppUserByEmail(email);
        Optional<UserResponse> optionalUserResponse = Optional.of(new UserResponse());
        if(user.isEmpty()){
            throw new ApiRequestException("User :" + email + " not found");
        }else
        {
            optionalUserResponse.get().setRole(user.get().getRole());
            optionalUserResponse.get().setEmail(user.get().getEmail());
            return optionalUserResponse;
        }
    }

    public UserResponse updateUser(String email, UserRequest userRequest){
        Optional<AppUser> userOptional =  userRepository.findAppUserByEmail(email);
        UserResponse userResponse = new UserResponse();

        if(userOptional.isEmpty()){
            throw new ApiRequestException("User " + email + " not found");
        }else{
            AppUser u = userOptional.get();
            u.setRole(userRequest.getRole());
            u.setPassword(userRequest.getPassword());
            AppUser savedUser = userRepository.save(u);

            userResponse.setEmail(savedUser.getEmail());
            userResponse.setRole(savedUser.getRole());
            return userResponse;
        }
    }
}
