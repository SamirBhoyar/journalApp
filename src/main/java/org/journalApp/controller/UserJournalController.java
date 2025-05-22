package org.journalApp.controller;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.

import org.journalApp.entity.JournalEntry;
import org.journalApp.entity.User;
import org.journalApp.service.UserJournalService;
import org.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userJournals")
public class UserJournalController {

    @Autowired
    private UserJournalService userJournalSerivce;
    @Autowired
    private UserService userSerivce;



    @GetMapping //It will get user and password from "" so we dont need to pass "@PathVariable String userName" anymore.
    //just need to pass user and password in header of request.
    public  ResponseEntity<?> getAllUser(){  //use of ResponseEntity with wildcard entry. and //
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();// It will provide userDetail through SecurityContextHolder which we configure in SpringSecurityConfing->
        String userName= authentication.getName();
        User user = userSerivce.findByUserName(userName);
            List<JournalEntry> all= user.getJournalEntries();
            if(all !=null && !all.isEmpty()){
                return new ResponseEntity<>(all,HttpStatus.OK);
            } else if (user != null) {
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
             Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName= authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            userJournalSerivce.saveUserJournalEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")  //Use of ResponseEntity<T> , we are passing user and password in auth header and journal id through request.
      public ResponseEntity<JournalEntry> getJournalEntreyById(@PathVariable ObjectId myId){ //Optional<T> can either have value or can be empty, So we provied .orElse(null);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user= userSerivce.findByUserName(userName);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry= userJournalSerivce.getJournalById(myId);
       if(journalEntry.isPresent()){
           return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
       }}
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}") //use of ResponseEntity<?> with wildcard type
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        boolean removed= userJournalSerivce.deleteJournalEntryById(myId,userName);
        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userSerivce.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = userJournalSerivce.getJournalById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                userJournalSerivce.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
// Note:- 1) GET Request from postman/web only use for getting the data, If you try hitting GET request with JSON body it will not pass body.
//        2) POST Request use for passing the data in raw or JSON body to application.

