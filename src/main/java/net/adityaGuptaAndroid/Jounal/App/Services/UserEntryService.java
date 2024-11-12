package net.adityaGuptaAndroid.Jounal.App.Services;

import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Repositories.UserRepo;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {
    @Autowired
    private UserRepo userRepo;

    public void saveEntry(UserEntry userEntry){
        userRepo.save(userEntry);
    }

    public List<UserEntry> getAll(){
        return userRepo.findAll();
    }

    public Optional<UserEntry> getSpecifiedUser(String name) {
        return userRepo.findByUserName(name);
    }
    public void delete_A_User(String name){
        userRepo.deleteByUserName(name);
    }


}
