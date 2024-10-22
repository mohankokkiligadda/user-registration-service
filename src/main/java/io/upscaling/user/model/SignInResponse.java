package io.upscaling.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder()
public class SignInResponse {
    private LoginStatus message;
}


