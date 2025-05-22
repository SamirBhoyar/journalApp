package org.journalApp.versionBackup.ServiceBackup;


//++ Second version on code: v2 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.journalApp.versionBackup.EntityBackup.User_v2;
import org.journalApp.versionBackup.repositoryBackup.UserRepository_v2;

import java.util.List;
import java.util.Optional;

@Service
public class UserSerivce_v2 {

    @Autowired
    private UserRepository_v2 userRepository;

    public void saveUser(User_v2 user) {

        userRepository.save(user);
    }

    public List<User_v2> getAllUser() {
        return userRepository.findAll();
    }

    public User_v2 findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Optional<User_v2> getUserById(ObjectId id) { //Optional<T> can either have value or can be empty
        return userRepository.findById(id);
    }

    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);

    }

}




//Note: standard practice => Collection will call -->Service, will call-->Repository.