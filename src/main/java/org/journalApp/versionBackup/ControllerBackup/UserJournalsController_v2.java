package org.journalApp.versionBackup.ControllerBackup;

//++ Second version on code: v2 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.



import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.journalApp.versionBackup.EntityBackup.JournalEntry_v2;
import org.journalApp.versionBackup.EntityBackup.User_v2;
import org.journalApp.versionBackup.ServiceBackup.JournalEntrySerivce_v2;
import org.journalApp.versionBackup.ServiceBackup.UserSerivce_v2;

import java.util.List;
import java.util.Optional;
//import java.util.Map;


@RestController
@RequestMapping("/userJournals")
public class UserJournalsController_v2 {

    @Autowired
    private JournalEntrySerivce_v2 journalEntrySerivce;

    @Autowired
    private UserSerivce_v2 userSerivce;

    @GetMapping("/{userName}")
    public  ResponseEntity<?> getAllUser(@PathVariable String userName){  //use of ResponseEntity with wildcard entry.
        User_v2 user = userSerivce.findByUserName(userName);
            List<JournalEntry_v2> all= user.getJournalEntries();
            if(all !=null && !all.isEmpty()){
                return new ResponseEntity<>(all,HttpStatus.OK);
            } else if (user != null) {
                return new ResponseEntity<>(user,HttpStatus.OK);

            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")  // Use of ResponseEntity<T>
    public ResponseEntity<JournalEntry_v2> createEntry(@RequestBody JournalEntry_v2 myEntry, @PathVariable String userName){
        try {
//            myEntry.setDate(LocalDateTime.now())
            journalEntrySerivce.saveUserJournalEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{Id}")  //Use of ResponseEntity<T>
      public ResponseEntity<User_v2> getJournalEntriesById(@PathVariable ObjectId Id){ //Optional<T> can either have value or can be empty, So we provied .orElse(null);
       Optional<User_v2> user= userSerivce.getUserById(Id);
       if(user.isPresent()){
           return new ResponseEntity<>(user.get(), HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}") //use of ResponseEntity<?> with wildcard type
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId, @PathVariable String userName){

        journalEntrySerivce.deleteJournalEntryById(myId,userName); 
       // return true; //without Use of ResponseEntity. return type will be boolean
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id,
                                         @RequestBody JournalEntry_v2 newEntry,
                                         @PathVariable String userName){
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