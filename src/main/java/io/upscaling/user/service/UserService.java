package io.upscaling.user.service;

import io.upscaling.user.document.UserDocument;
import io.upscaling.user.model.*;
import io.upscaling.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    public UserDocument getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDocument getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }
    public SignUpResponse saveUser(SignUpRequest request) {

        UserDocument userDocument = UserDocument.builder()
                .email(request.getEmail())
                .userName(request.getUserName())
                .password(request.getPassword())
                .build();

        UserDocument dbResponse = userRepository.save(userDocument);

        //SignupResponse

        SignUpResponse signUpResponse = SignUpResponse.builder()
                .id(dbResponse.getId())
                .email(dbResponse.getEmail())
                .userName(dbResponse.getUserName())
                .build();
        return signUpResponse;
    }

    public SignInResponse signInUser(SignInRequest request) {
        boolean isEmail = request.getEmailOrUserName().contains("@");

        UserDocument dbResponse;
        if (isEmail) {
            dbResponse = userRepository.findByEmail(request.getEmailOrUserName());
        } else {
            dbResponse = userRepository.findByUserName(request.getEmailOrUserName());
        }

        SignInResponse response;
        if (dbResponse != null && dbResponse.getPassword().equals(request.getPassword())) {
            response = SignInResponse.builder()
                    .message(LoginStatus.SUCCESS)
                    .build();
        } else {
            response = SignInResponse.builder()
                    .message(LoginStatus.UN_AUTHORIZED)
                    .build();
        }

        return response;
    }

}

