package org.journalApp.versionBackup.repositoryBackup;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.journalApp.versionBackup.EntityBackup.User_v2;

@Repository
public interface UserRepository_v2 extends MongoRepository<User_v2, ObjectId> {

     User_v2 findByUserName(String username);
//     User deleteByUserName(String username);
}
