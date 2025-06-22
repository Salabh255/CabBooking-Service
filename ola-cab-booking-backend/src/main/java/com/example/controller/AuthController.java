package com.example.controller;

import com.example.config.JwtUtil;
import com.example.domain.UserRole;
import com.example.exception.UserException;
import com.example.model.Driver;
import com.example.model.User;
import com.example.repository.DriverRepository;
import com.example.repository.UserRepository;
import com.example.request.DriverSignupRequest;
import com.example.request.UserLoginRequest;
import com.example.request.UserSignupRequest;
import com.example.response.JwtResponse;
import com.example.service.CustomerUserDetailsService;
import com.example.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private DriverRepository driverRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private CustomerUserDetailsService customerUserDetailsService;
    private DriverService driverService;

    public AuthController(DriverRepository driverRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, DriverService driverService, CustomerUserDetailsService customerUserDetailsService){
        this.driverRepository=driverRepository;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
        this.driverService=driverService;
        this.customerUserDetailsService=customerUserDetailsService;
    }

    //api/auth/user/signup
    @PostMapping("/user/signup")
    public ResponseEntity<JwtResponse> signupHandler(@RequestBody UserSignupRequest req) throws UserException{

        String email = req.getEmail();
        String fullName = req.getFullName();
        String mobile = req.getMobile();
        String password = req.getPassword();

        User user = userRepository.findByEmail(email);

        if(user!=null) throw new UserException("User Already exists with email " + email);

        String encodedPassword = passwordEncoder.encode(password);

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(encodedPassword);
        createdUser.setFullName(fullName);
        createdUser.setMobile(mobile);
        createdUser.setRole(UserRole.USER);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setErrorDetails(null);
        jwtResponse.setType(UserRole.USER);
        jwtResponse.setMessage("Account Created Successfully: " + savedUser.getFullName());

        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
    }

    //api/driver/signup
    @PostMapping("/driver/signup")
    public ResponseEntity<JwtResponse> driverSignupHandler(@RequestBody DriverSignupRequest driverSignupRequest){

        Driver driver = driverRepository.findByEmail(driverSignupRequest.getEmail());

        JwtResponse jwtResponse = new JwtResponse();

        if(driver != null) {
            jwtResponse.setAuthenticated(false);
            jwtResponse.setErrorDetails("email already used with another account");
            jwtResponse.setError(true);

            return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.BAD_REQUEST);
        }

        Driver createdDriver = driverService.registerDriver(driverSignupRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdDriver.getEmail(), createdDriver.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);

        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setErrorDetails(null);
        jwtResponse.setType(UserRole.DRIVER);
        jwtResponse.setMessage("Account Created Successfully: " + createdDriver.getName());

        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.ACCEPTED);
    }


//    @PostMapping("/driver/signup")
//    public ResponseEntity<JwtResponse> driverSignup(@RequestBody DriverSignupRequest req){
//        System.out.println("Driver signup request received with email: " + req.getEmail());
//
//        Driver driver = driverRepository.findByEmail(req.getEmail());
//
//        JwtResponse jwtResponse = new JwtResponse();
//
//        if(driver != null){
//            System.out.println("Driver already exists with email: " + req.getEmail());
//            jwtResponse.setAuthenticated(false);
//            jwtResponse.setErrorDetails("email already used with another account");
//            jwtResponse.setError(true);
//            return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
//        }
//
//        Driver createdDriver = driverService.registerDriver(req);
//        System.out.println("Driver registered successfully: " + createdDriver.getEmail());
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(createdDriver.getEmail(), createdDriver.getPassword());
//        System.out.println("Authentication object created: " + authentication);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("Authentication set in security context.");
//
//        String jwt = jwtUtil.generateJwtToken(authentication);
//        System.out.println("JWT generated: " + jwt);
//
//        jwtResponse.setJwt(jwt);
//        jwtResponse.setAuthenticated(true);
//        jwtResponse.setError(false);
//        jwtResponse.setErrorDetails(null);
//        jwtResponse.setType(UserRole.USER);
//        jwtResponse.setMessage("Account Created Successfully: " + createdDriver.getName());
//
//        return new ResponseEntity<>(jwtResponse, HttpStatus.ACCEPTED);
//    }ACCEPTED

    //api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody UserLoginRequest req) {
        String username = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(password, username);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setErrorDetails(null);
        jwtResponse.setType(UserRole.USER);
        jwtResponse.setMessage("Login successful: " + username);

        return new ResponseEntity<>(jwtResponse, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String password, String username) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password from authenticate method!");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
