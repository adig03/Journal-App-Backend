package net.adityaGuptaAndroid.Jounal.App.Services;

import net.adityaGuptaAndroid.Jounal.App.Entity.JournalEntry;
import net.adityaGuptaAndroid.Jounal.App.Entity.UserEntry;
import net.adityaGuptaAndroid.Jounal.App.Repositories.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class JournalEntryService {


    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserEntryService userService;


    public void saveJournal(JournalEntry journalEntry,  String name){
        Optional<UserEntry> user = userService.getSpecifiedUser(name);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        if(user.isPresent()) {
            user.get().getJournalEntries().add(saved);
            userService.saveEntry(user.get());
        }
    }

    public void saveJournal(JournalEntry journalEntry){
    journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getASpecificJournal( ObjectId myId){
        return journalEntryRepo.findById(myId);
    }

    public void deleteById(ObjectId myId, String userName){
        Optional<UserEntry> user = userService.getSpecifiedUser(userName);

        if(user.isPresent()){
          user.get().getJournalEntries().removeIf( x -> x.getId().equals(myId));
          userService.saveEntry(user.get());
          journalEntryRepo.deleteById(myId);
        }

    }


}
