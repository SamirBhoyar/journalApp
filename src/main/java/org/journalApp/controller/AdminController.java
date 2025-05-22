package org.journalApp.controller;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.
//and use of Junit testing for UserService-->findByUserName("Ram")


import org.journalApp.entity.User;
import org.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userSerivce;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser() {  //use of ResponseEntity with wildcard entry.

        List<User> all = userSerivce.getAllUser();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-adminUser")
    public ResponseEntity<?> CreateAdmin(@RequestBody User user){
        userSerivce.saveAdminUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
