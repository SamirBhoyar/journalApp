package org.journalApp.service;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.

import lombok.extern.slf4j.Slf4j;
import org.journalApp.entity.User;
import org.journalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j // it will create instance with "log" name.
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // You can use @slf4j Annotation to Inject Logger.
//    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    private  final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveNewUser(User user) { //Save User with encrypted password
       try {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           user.setRoles(Arrays.asList("User")); //comment this if your adding first user with role as Admin
           userRepository.save(user);
       }catch (Exception e){
//           logger.error("Error occurred while creating user: {}",user.getUserName(),e); //instance of Logger
           log.error("Error occurred while creating user: {}",user.getUserName(),e);  //instance of SLF4J
       }
    }

    public List<User> getAllUser(){
       return userRepository.findAll();

    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void saveAdminUser(User user) { //Save User with encrypted password and admin role
//        user.setUserName(user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User","Admin")); //comment this if your adding first user with role as Admin
        userRepository.save(user);
    }
}

//Note: standard practice => Collection will call -->Service, will call-->Repository.