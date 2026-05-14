package com.ebanking.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse <T>{

    private Boolean success;

    private String message;

    private T data;
}
