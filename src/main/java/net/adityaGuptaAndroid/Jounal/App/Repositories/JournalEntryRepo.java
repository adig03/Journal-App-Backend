package net.adityaGuptaAndroid.Jounal.App.Repositories;

import net.adityaGuptaAndroid.Jounal.App.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface JournalEntryRepo extends MongoRepository<JournalEntry , ObjectId>{




}
