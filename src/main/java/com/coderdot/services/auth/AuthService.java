package com.coderdot.services.auth;

import com.coderdot.dtos.SignupRequest;
import com.coderdot.dtos.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);

}
