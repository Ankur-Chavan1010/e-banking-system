package com.ebanking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponse {

    private UUID userId;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String role;
}
