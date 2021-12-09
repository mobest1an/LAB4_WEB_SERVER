package com.mobest1an.LAB4_WEB.controller;

import com.mobest1an.LAB4_WEB.jwt.JwtTokenProvider;
import com.mobest1an.LAB4_WEB.model.Check;
import com.mobest1an.LAB4_WEB.model.User;
import com.mobest1an.LAB4_WEB.resvice.CheckService;
import com.mobest1an.LAB4_WEB.resvice.UserDetailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailServiceImpl userDetailService;
    private final CheckService checkService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserDetailServiceImpl userDetailService, CheckService checkService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailService = userDetailService;
        this.checkService = checkService;
    }

    @PostMapping("/getChecks")
    public ResponseEntity<?> getUserChecks(ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        User user = userDetailService.findByUsername(jwtTokenProvider.getUsername(token));
        Map<Object, Object> response = new HashMap<>();
        response.put("checks", checkService.getUserChecks(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addCheck")
    public String addCheck(HttpServletRequest request, @RequestBody Check check) {
        String token = jwtTokenProvider.resolveToken(request);
        User user = userDetailService.findByUsername(jwtTokenProvider.getUsername(token));
        checkService.addCheck(user, check);
        return "Добавлено?";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = userDetailService.findByUsername(request.getUsername());
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", request.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        boolean isAdded = userDetailService.saveUser(user);
        if (isAdded) {
            Map<Object, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
