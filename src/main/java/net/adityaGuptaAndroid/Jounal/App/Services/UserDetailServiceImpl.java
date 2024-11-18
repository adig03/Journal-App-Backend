 package net.adityaGuptaAndroid.Jounal.App.Services;

 import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
 import net.adityaGuptaAndroid.Jounal.App.Repositories.UserRepo;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.userdetails.User;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.core.userdetails.UserDetailsService;
 import org.springframework.security.core.userdetails.UsernameNotFoundException;
 import org.springframework.stereotype.Component;

 import java.util.Optional;
@Component
 public class UserDetailServiceImpl implements UserDetailsService {
// UserDetailsService interface is used in Spring Security to load User-Specified Data.
     @Autowired
     private UserRepo userRepo;


     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntry> user = userRepo.findByUserName(username);

        if(user.isPresent()){
            return User.builder()
                    .username(user.get().getUserName())
                    .password(user.get().getPassword())
                    .roles(user.get().getRoles().toArray(new String[0]))
                    .build();


        }
      throw new UsernameNotFoundException("User not found with Username" + username);
     }
 }
