package net.adityaGuptaAndroid.Jounal.App.Controllers;


import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserEntryService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntry> all = userService.getAll();
        if(!all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create-admin")

    public ResponseEntity<?> createAdminUser(@RequestBody UserEntry adminEntry){
        try{
            userService.saveAdminEntry(adminEntry);
            return new ResponseEntity<>(adminEntry , HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }


    }
}
