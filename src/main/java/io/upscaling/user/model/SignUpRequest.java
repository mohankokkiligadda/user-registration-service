package io.upscaling.user.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {
    private String email;
    private String userName;
    private String password;


}
