package com.uol.pb.challenge3.service.security;

import com.uol.pb.challenge3.dto.request.LoginDTORequest;
import com.uol.pb.challenge3.dto.request.RegisterDTORequest;
import com.uol.pb.challenge3.entity.security.Role;
import com.uol.pb.challenge3.entity.security.User;
import com.uol.pb.challenge3.exceptions.UserAlreadyExistsException;
import com.uol.pb.challenge3.repository.security.RoleRepository;
import com.uol.pb.challenge3.repository.security.UserRepository;
import com.uol.pb.challenge3.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public String register(RegisterDTORequest request) {
        if(userRepository.existsByUsername((request.getUsername()))){
            throw new UserAlreadyExistsException("Username is already exists!");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("Email is already exists!");
        }
        RegisterDTORequest requestEncoded = new RegisterDTORequest(request.getUsername(), request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        User user = new User(requestEncoded);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return  "User registered sucessfully";
    }
    @Override
    public String login(LoginDTORequest loginDTORequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTORequest.getUsernameOrEmail(), loginDTORequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

}
