package io.upscaling.user.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder()
@Document(collection = "users")
public class UserDocument {
    @Id
    private String id;
    private String email;
    private String userName;
    private String password;
}
