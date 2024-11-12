package net.adityaGuptaAndroid.Jounal.App.Repositories;

import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepo extends MongoRepository<UserEntry, ObjectId> {
    Optional<UserEntry> findByUserName(String name);

    Optional<UserEntry> deleteByUserName(String name);
}
