package org.journalApp.controller;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.

import org.journalApp.entity.User;
import org.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userSerivce;

    @GetMapping("/health")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<Object> createUser(@RequestBody User myUser) {
        try {
            userSerivce.saveNewUser(myUser);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}