package org.journalApp.controller;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.

import org.journalApp.entity.User;

import org.journalApp.repository.UserRepository;
import org.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userSerivce;
    @Autowired
    private UserRepository userRepository;

//    @GetMapping: we will not use Get all as user only has permission for his details using
//    authentication through his username and password.
//    @PostMapping: is present in public controller as, To create user we don't need authentication.

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser) { //As we include indexing in User class field username we can set value through username field as it is unique.
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();// It will provide userDetail through SecurityContextHolder which we configure in SpringSecurityConfing->
        String userName= authentication.getName();
        User userInDb = userSerivce.findByUserName(userName);
        userInDb.setUserName(newUser.getUserName());
        userInDb.setPassword(newUser.getPassword());
//        userInDb.setRoles(newUser.getRoles());  //only use if there is no user with admin role or else you can add user role=admin ,manually into MongoDb
        userSerivce.saveNewUser(userInDb);
        return new ResponseEntity<>(userInDb, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEntry() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();// It will provide userDetail through SecurityContextHolder which we configure in SpringSecurityConfing->

        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
