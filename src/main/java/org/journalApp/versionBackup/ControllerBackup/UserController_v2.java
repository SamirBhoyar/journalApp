package org.journalApp.versionBackup.ControllerBackup;

//++ Second version on code: v2 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.journalApp.versionBackup.EntityBackup.User_v2;
import org.journalApp.versionBackup.ServiceBackup.UserSerivce_v2;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/users")
public class UserController_v2 {

    @Autowired
    private UserSerivce_v2 userSerivce;

    @GetMapping
    public ResponseEntity<?> getAllUser() {  //use of ResponseEntity with wildcard entry.

        List<User_v2> all = userSerivce.getAllUser();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping  // Use of ResponseEntity<T>
    public ResponseEntity<User_v2> createEntry(@RequestBody User_v2 myUser) {
        try {
//            myUser.setDate(LocalDateTime.now());
            userSerivce.saveUser(myUser);
            return new ResponseEntity<>(myUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User_v2 newUser, @PathVariable String userName) { //As we include indexing in User class field username we can set value through username field as it is unique.
        User_v2 userInDb = userSerivce.findByUserName(userName);

        if (userInDb != null) {
            userInDb.setUserName(newUser.getUserName());
            userInDb.setPassword(newUser.getPassword());
            userSerivce.saveUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")  //Use of ResponseEntity<T>
    public ResponseEntity<User_v2> getJournalEntriesById(@PathVariable ObjectId myId) { //Optional<T> can either have value or can be empty, So we provied .orElse(null);
        Optional<User_v2> user = userSerivce.getUserById(myId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}") //use of ResponseEntity<?> with wildcard type
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id) {
        userSerivce.deleteUser(id);
        // return true; //without Use of ResponseEntity. return type will be boolean
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
