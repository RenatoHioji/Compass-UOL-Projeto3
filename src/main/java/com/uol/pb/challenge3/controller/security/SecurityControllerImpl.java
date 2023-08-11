package com.uol.pb.challenge3.controller.security;

import com.uol.pb.challenge3.dto.request.LoginDTORequest;
import com.uol.pb.challenge3.dto.request.RegisterDTORequest;
import com.uol.pb.challenge3.security.JwtAuthResponse;
import com.uol.pb.challenge3.service.security.SecurityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/security")
public class SecurityControllerImpl implements SecurityController {
    private final SecurityServiceImpl service;
    @Override
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTORequest request){
        return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
    }

    @Override
    @PostMapping
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTORequest loginDTORequest){
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(service.login(loginDTORequest));
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
