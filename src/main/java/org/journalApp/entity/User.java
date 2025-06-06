package org.journalApp.entity;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
//field should be Map with collection-> which is inside @Document and Annotation (key="value") will getOrCreate the Collection(Tablename in NoSql) name.
// Also we have additional @Data, @Getter and @Setter Annotation for Lombok Project

@Document(collection="users")
@Data
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true) //It will provide indexing for faster access and unique values. You have to add this to application.properties file.
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private List<String> roles;

    @DBRef
    private List<JournalEntry> journalEntries= new ArrayList<>(); //[12,15]-> journalEntries.add(12);journalEntries.add(15);


}
