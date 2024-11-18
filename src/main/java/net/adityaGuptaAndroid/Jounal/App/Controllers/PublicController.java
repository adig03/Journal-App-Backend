package net.adityaGuptaAndroid.Jounal.App.Controllers;


import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userService;


    @PostMapping("/create-user")
    public ResponseEntity<?> saveAnEntry(@RequestBody UserEntry Entry ){
        try{
            userService.saveEntry(Entry);
            return new  ResponseEntity<>(Entry , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e , HttpStatus.BAD_REQUEST);
        }
    }
}
