package com.coderdot.dtos;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String name;

    private String password;

}
