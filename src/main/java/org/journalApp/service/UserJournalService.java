package org.journalApp.service;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.

import org.journalApp.entity.JournalEntry;
import org.journalApp.entity.User;
import org.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserJournalService {

    @Autowired
    private JournalEntryRepository journalEntryRepo;
    @Autowired
    private UserService userService;


    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepo.save(journalEntry);
    }

    //@Transactional : it will treat this method as single process if any line in this method failed/error then it will roll back every thing. Which
    //which, provide atomicity and isolation to saveUserJournalEntry method.
    //Note: Not using this here as "Transaction number are only allowed on replica set member" which need cloud and MongoDB atlas.
    public void saveUserJournalEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        JournalEntry saved=journalEntryRepo.save(journalEntry);
       // user.setJournalEntries(null); this is crated issue, as we want to see @Transactional annotation.
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public Optional<JournalEntry> getJournalById(Object id){ //Optional<T> can either have value or can be empty
        return journalEntryRepo.findById(id);
    }

    public boolean deleteJournalEntryById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        boolean removed = user.getJournalEntries().removeIf(x->x.getId().equals(id)); //if it remove it will return true as per Mongodb.remove logic.
        if(removed) {
            userService.saveUser(user);
            journalEntryRepo.deleteById(id);
            return  true;
        }
        return  false;
    }
}

//Note: standard practice => Collection will call -->Service, will call-->Repository.