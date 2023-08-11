package com.uol.pb.challenge3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTORequest {
    private String name;
    private String username;
    private String email;
    private String password;

}
