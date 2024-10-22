package io.upscaling.user.repository;

import io.upscaling.user.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
    UserDocument findByEmail(String email);
    UserDocument findByUserName(String userName);
}



