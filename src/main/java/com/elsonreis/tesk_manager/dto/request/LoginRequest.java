package com.elsonreis.tesk_manager.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
