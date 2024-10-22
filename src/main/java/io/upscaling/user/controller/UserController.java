package io.upscaling.user.controller;

import io.upscaling.user.document.UserDocument;
import io.upscaling.user.model.*;
import io.upscaling.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<SignUpResponse> createUser(@RequestBody SignUpRequest request) {
        //Checking if user exists already
        UserDocument existingUser = userService.getUserByEmail(request.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        //Persisting request
        SignUpResponse signUpResponse = userService.saveUser(request);

        //Returning response
        if (signUpResponse != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/signIn")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
        try {
            SignInResponse signInResponse = userService.signInUser(request);
            if (signInResponse.getMessage().equals(LoginStatus.SUCCESS)) {
                return ResponseEntity.ok(signInResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(signInResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

