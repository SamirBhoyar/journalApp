package org.journalApp.entity;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
//field should be Map with collection-> which is inside @Document and Annotation (key="value") will getOrCreate the Collection(Tablename in NoSql) name.
// Also we have additional @Data, @Getter and @Setter Annotation for Lombok Project

@Document(collection="userjournal")
//@Getter
@Data // It will Generate @Getter, @Setter, @RequireArgConstructor, @ToString, @EqualAndHashcode And @value.
@NoArgsConstructor  //during deseializaition(Json to pojo) need NoArgConstructor and If you not implement it will call RequestArgConstructor as you add @Data Annotation.
public class JournalEntry {
    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

}
