package com.uol.pb.challenge3.service.security;

import com.uol.pb.challenge3.dto.request.LoginDTORequest;
import com.uol.pb.challenge3.dto.request.RegisterDTORequest;

public interface SecurityService {
    String register(RegisterDTORequest request);
    String login(LoginDTORequest loginDTORequest);
}
