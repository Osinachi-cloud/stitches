package com.stitches.service.serviceImpl;

import com.stitches.security.JwtService;
import com.stitches.dto.request.AuthenticationRequest;
import com.stitches.dto.request.RegisterRequest;
import com.stitches.dto.request.UserRequest;
import com.stitches.dto.response.AuthenticationResponse;
import com.stitches.dto.response.RegistrationResponse;
import com.stitches.dto.response.UserResponse;
import com.stitches.exceptions.ApiRequestException;
import com.stitches.model.Address;
import com.stitches.model.AppUser;
import com.stitches.repository.MerchantRepository;
import com.stitches.repository.UserRepository;
import com.stitches.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final MerchantRepository merchantRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final UserInfoUserDetailsService userDetailsService;

    public UserServiceImpl(
            MerchantRepository merchantRepository,
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService, UserInfoUserDetailsService userDetailsService){
        this.merchantRepository = merchantRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    private UserResponse convertUserToDTO(AppUser user){
        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();
        return userResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        UsernamePasswordAuthenticationToken authenticationToken;
        try {
            log.info("Authentication manager => {}",authenticationManager);
             UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
                authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
            log.info("Auth token => {}",authenticationToken);
                Authentication auth = authenticationManager.authenticate(authenticationToken);
                log.info("auth ====<<< {} ====>",auth.isAuthenticated());
                if(!auth.isAuthenticated()){
                    throw new UsernameNotFoundException("invalid user request !");

                }
                System.out.println("Authentication successful.");

        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }catch (Exception e){
            System.out.println("General Exception caught => "+e.getMessage());
        }
        AppUser user = userRepository.findAppUserByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public RegistrationResponse register(RegisterRequest request) {
            Optional<AppUser> userDetails = userRepository.findAppUserByEmail(request.getEmail());
            if(userDetails.isPresent()){
                throw new ApiRequestException("User already exists");
            }

        Address address = Address.builder()
                .city(request.getCity())
                .country(request.getCountry())
                .street(request.getStreet())
                .state(request.getState())
                .build();

        AppUser user = AppUser.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .mobile(request.getMobile())
                .address(address)
                .role(request.getRole())
                .build();
        log.info("User Request ====>>>> {}", user);
//        AppUser savedUser = userRepository.save(user);

        AppUser savedUser = userRepository.saveAndFlush(user);
        if (savedUser == null) {
            throw new ApiRequestException("Failed to persist user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("saved User ====>>>> {}", savedUser);
        return RegistrationResponse.builder()
                .response("User Successfully registered")
                .build();
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
        if(!user.isPresent()){
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

        if(!userOptional.isPresent()){
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
