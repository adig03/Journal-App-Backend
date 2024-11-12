package net.adityaGuptaAndroid.Jounal.App.Controllers;


import net.adityaGuptaAndroid.Jounal.App.Entity.JournalEntry;
import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Services.JournalEntryService;
import net.adityaGuptaAndroid.Jounal.App.Services.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {



    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userService;


    @GetMapping("{userName}")
public ResponseEntity<?> getAllJournalEntriesForUser(@PathVariable String userName){

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

@PostMapping("{name}")
public  ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry  , @PathVariable String name){

try{

    journalEntryService.saveJournal(myEntry , name);
    return new  ResponseEntity< >(myEntry , HttpStatus.CREATED);
}catch (Exception e){
return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
}



}

@GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getSpecificEntry(@PathVariable ObjectId myId){


    Optional<JournalEntry> journalEntry = journalEntryService.getASpecificJournal(myId);
    if(journalEntry.isPresent()){
        return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
    }
    else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

    @DeleteMapping("{userName}/{myId}")
    public ResponseEntity<?> deleteSpecificJournal(@PathVariable ObjectId myId , @PathVariable String userName) {

        if (journalEntryService.getASpecificJournal(myId).isPresent()) {
            journalEntryService.deleteById(myId , userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("{userName}/{myId}")
    public ResponseEntity<?> updateSpecificJournal(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry , @PathVariable String name) {
        JournalEntry oldJournalEntry = journalEntryService.getASpecificJournal(myId).orElse(null);
        if (oldJournalEntry != null) {
            oldJournalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldJournalEntry.getContent());
            journalEntryService.saveJournal(oldJournalEntry);
            return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

     }

}
