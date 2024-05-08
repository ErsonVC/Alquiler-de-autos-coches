package com.coderdot.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;

}
