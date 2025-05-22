package org.journalApp.versionBackup.ServiceBackup;

//++ Second version on code: v2 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.journalApp.versionBackup.EntityBackup.JournalEntry_v2;
import org.journalApp.versionBackup.EntityBackup.User_v2;
import org.journalApp.versionBackup.repositoryBackup.JournalEntryRepository_v2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntrySerivce_v2 {

    @Autowired
    private JournalEntryRepository_v2 journalEntryRepo;
    @Autowired
    private UserSerivce_v2 userSerivce;

    public void saveEntry(JournalEntry_v2 journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepo.save(journalEntry);
    }

    //@Transactional : it will treat this method as single process if any line in this method failed/error then it will roll back every thing. Which
    //which, provide atomicity and isolation to saveUserJournalEntry method.
    //Note: Not using this here as "Transaction number are only allowed on replica set member" which need cloud and MongoDB atlas.
    public void saveUserJournalEntry(JournalEntry_v2 journalEntry, String userName) {
        User_v2 user = userSerivce.findByUserName(userName);
        JournalEntry_v2 saved=journalEntryRepo.save(journalEntry);
       // user.setJournalEntries(null); this is crated issue, as we want to see @Transactional annotation.
        user.getJournalEntries().add(saved);
        userSerivce.saveUser(user);


    }

    public List<JournalEntry_v2> getAllJournals(){
       return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry_v2> getJournalById(Object id){ //Optional<T> can either have value or can be empty
        return journalEntryRepo.findById(id);
    }

    public  void deleteEntryById(Object id){
         journalEntryRepo.deleteById(id);

    }

    public void deleteJournalEntryById(ObjectId id, String userName) {
        User_v2 user = userSerivce.findByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userSerivce.saveUser(user);
        journalEntryRepo.deleteById(id);

    }
}


//Note: standard practice => Collection will call -->Service, will call-->Repository.