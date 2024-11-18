package net.adityaGuptaAndroid.Jounal.App.Controllers;


import net.adityaGuptaAndroid.Jounal.App.Entity.JournalEntry;
import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Services.JournalEntryService;
import net.adityaGuptaAndroid.Jounal.App.Services.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {



    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userService;




    @GetMapping()
public ResponseEntity<?> getAllJournalEntriesForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName =  authentication.getName();
        Optional<UserEntry> user = userService.getSpecifiedUser(userName);
        try{
            if(user.isPresent()) {
                List<JournalEntry> journalEntries = user.get().getJournalEntries();
                return new ResponseEntity<>(journalEntries, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(Exception e){
            return new ResponseEntity<>(e , HttpStatus.CONFLICT);
        }

}

@PostMapping()
public  ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name =  authentication.getName();

try{

    journalEntryService.saveJournal(myEntry , name);
    return new  ResponseEntity< >(myEntry , HttpStatus.CREATED);
}catch (Exception e){
return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
}



}

@GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getSpecificEntry(@PathVariable ObjectId myId , @RequestBody UserEntry newEntry){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name =  authentication.getName();
    Optional<UserEntry> user = userService.getSpecifiedUser(name);
    List<JournalEntry> collect = user.get().getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
    if(collect.isEmpty()){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Optional<JournalEntry> journalEntry = journalEntryService.getASpecificJournal(myId);
    if(journalEntry.isPresent()){
        return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
    }
    else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

    @DeleteMapping("/{myId}")
    public ResponseEntity<?> deleteSpecificJournal(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName =  authentication.getName();

        if (journalEntryService.getASpecificJournal(myId).isPresent()) {
            journalEntryService.deleteById(myId , userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/{myId}")
    public ResponseEntity<?> updateSpecificJournal(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name =  authentication.getName();
        Optional<UserEntry> user = userService.getSpecifiedUser(name);
        List<JournalEntry> collect = user.get().getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            JournalEntry oldJournalEntry = journalEntryService.getASpecificJournal(myId).orElse(null);
            if (oldJournalEntry != null) {
                oldJournalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldJournalEntry.getTitle());
                oldJournalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldJournalEntry.getContent());
                journalEntryService.saveNewJournal(oldJournalEntry);
                return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

     }

}
