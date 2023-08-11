package com.uol.pb.challenge3.controller.security;

import com.uol.pb.challenge3.dto.request.LoginDTORequest;
import com.uol.pb.challenge3.dto.request.RegisterDTORequest;
import com.uol.pb.challenge3.security.JwtAuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface SecurityController {
    ResponseEntity<String> register(@RequestBody RegisterDTORequest request);
    ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTORequest loginDTORequest);
}
