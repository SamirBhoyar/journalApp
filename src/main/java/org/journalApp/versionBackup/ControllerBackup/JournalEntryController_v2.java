package org.journalApp.versionBackup.ControllerBackup;

//++ Second version on code: v2 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.journalApp.versionBackup.EntityBackup.JournalEntry_v2;
import org.journalApp.versionBackup.ServiceBackup.JournalEntrySerivce_v2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
//import java.util.Map;

@RestController
@RequestMapping("/journals")
public class JournalEntryController_v2 {

//    private Map<Long, JournalEntry> journalEntries =new HashMap<>();

//    private JournalEntrySerivce journalEntrySerivce =new JournalEntrySerivce(); //this what spring boot Autowire do internally call dependency injection.
    @Autowired
    private JournalEntrySerivce_v2 journalEntrySerivce;

    @GetMapping
    public  ResponseEntity<?> getAllJournalEntry(){  //use of ResponseEntity with wildcard entry.

            List<JournalEntry_v2> all= journalEntrySerivce.getAllJournals();
            if(all !=null && !all.isEmpty()){
                return new ResponseEntity<>(all,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping  // Use of ResponseEntity<T>
    public ResponseEntity<JournalEntry_v2> createEntry(@RequestBody JournalEntry_v2 myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntrySerivce.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{myId}")  //Use of ResponseEntity<T>
      public ResponseEntity<JournalEntry_v2> getJournalEntreyById(@PathVariable ObjectId myId){ //Optional<T> can either have value or can be empty, So we provied .orElse(null);
       Optional<JournalEntry_v2> journalEntry= journalEntrySerivce.getJournalById(myId);
       if(journalEntry.isPresent()){
           return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}") //use of ResponseEntity<?> with wildcard type
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        journalEntrySerivce.deleteEntryById(myId);
       // return true; //without Use of ResponseEntity. return type will be boolean
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry_v2> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry_v2 newEntry){
       JournalEntry_v2 old = journalEntrySerivce.getJournalById(id).orElse(null);
       if(old !=null){
           old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
           old.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
           journalEntrySerivce.saveEntry(old);
           return new ResponseEntity<>(old,HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
// Note:- 1) GET Request from postman/web only use for getting the data, If you try hitting GET request with JSON body it will not pass body.
//        2) POST Request use for passing the data in raw or JSON body to application.


//* RestAPI without ResponseEntity*//


//@GetMapping
//public List<JournalEntryV2> getAll(){
//    return journalEntrySerivce.getAll();
//}

//    @PostMapping  //without Use of ResponseEntity<T>
//    public JournalEntryV2 createEntry(@RequestBody JournalEntryV2 myEntry){
//        myEntry.setDate(LocalDateTime.now());
//        journalEntrySerivce.saveEntry(myEntry);
//        return myEntry;
//    }

//    @GetMapping("id/{myId}")  //without Use of ResponseEntity<T>
//    public JournalEntryV2 getJournalEntriesById(@PathVariable ObjectId myId){ //Optional<T> can either have value or can be empty, So we provied .orElse(null);
//        return journalEntrySerivce.getJournalById(myId).orElse(null);
//    }
//
//@DeleteMapping("id/{myId}")
//public Boolean deleteEntry(@PathVariable ObjectId myId) {
//    journalEntrySerivce.DeleteEntryById(myId);
//    return true; //without Use of ResponseEntity. return type will be boolean
//}
//
//@PutMapping("id/{id}")
//public JournalEntryV2 updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntryV2 newEntry){
//    JournalEntryV2 old = journalEntrySerivce.getJournalById(id).orElse(null);
//    if(old !=null){
//        old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//        old.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
//    }
//    journalEntrySerivce.saveEntry(old);
//    return old;
//}