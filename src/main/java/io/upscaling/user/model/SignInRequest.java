package io.upscaling.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder()
public class SignInRequest {
    private String emailOrUserName;
    private String password;

}

