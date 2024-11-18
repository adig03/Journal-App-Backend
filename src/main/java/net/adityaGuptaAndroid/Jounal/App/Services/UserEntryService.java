package net.adityaGuptaAndroid.Jounal.App.Services;

import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Repositories.UserRepo;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {
    @Autowired
    private UserRepo userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntry userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER"));
        userRepo.save(userEntry);
    }

    public void saveAdminEntry(UserEntry userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER" , "ADMIN"));
        userRepo.save(userEntry);
    }

    public void saveNewUser(UserEntry user){
        userRepo.save(user);
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
