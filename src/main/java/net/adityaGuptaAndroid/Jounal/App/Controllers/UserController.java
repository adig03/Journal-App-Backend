
package net.adityaGuptaAndroid.Jounal.App.Controllers;


import net.adityaGuptaAndroid.Jounal.App.Entity.JournalEntry;
import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Services.JournalEntryService;
import net.adityaGuptaAndroid.Jounal.App.Services.UserEntryService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserEntryService userService;


    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try{
            List<UserEntry> userEntries  = userService.getAll();
            return new ResponseEntity<>(userEntries , HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e ,HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{name}")
    public ResponseEntity<?> getASpecifiedUser(@PathVariable String name){
        Optional<UserEntry> UserEntry = userService.getSpecifiedUser(name);

        if(UserEntry.isPresent()){
            return new ResponseEntity<>(UserEntry.get() , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{name}")
    public  ResponseEntity<?> updateAnUser(@PathVariable String name, @RequestBody UserEntry newEntry){
        Optional<UserEntry> userInDB = userService.getSpecifiedUser(name);

        if(userInDB.isPresent()){
            userInDB.get().setUserName(newEntry.getUserName() != null && !newEntry.getUserName().isEmpty() ? newEntry.getUserName() : userInDB.get().getUserName());
            userInDB.get().setPassword(newEntry.getPassword() != null && !newEntry.getPassword().isEmpty() ? newEntry.getPassword() : userInDB.get().getPassword());
            userService.saveEntry(userInDB.get());
            return new ResponseEntity<>(userInDB.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> saveAnEntry(@RequestBody UserEntry Entry ){
        try{
            userService.saveEntry(Entry);
            return new  ResponseEntity<>(Entry , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e , HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteAnUser(@PathVariable String name){

        Optional<UserEntry> searchedUser = userService.getSpecifiedUser(name);

        if(searchedUser.isPresent()){
            userService.delete_A_User(name);
            return new ResponseEntity<>("Successfully deleted" , HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }







}
