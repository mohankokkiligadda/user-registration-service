package io.upscaling.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder()
public class SignUpResponse {
    private String id;
    private String email;
    private String userName;
}





