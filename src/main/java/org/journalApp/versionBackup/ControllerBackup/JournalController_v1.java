package org.journalApp.versionBackup.ControllerBackup;

//++ First version on code: v1 ++//
//this version do not contain use of Mongo db not data remained store if we reset the application

import org.journalApp.versionBackup.EntityBackup.Journal_v1;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/journal")
public class JournalController_v1 {

    private Map<Long, Journal_v1> journalEntries =new HashMap<>();

    @GetMapping                         //If you do not specify any value to it @Getmapping, then HTTP request will enter through context path "/journal" and as per "GET or POST request" it will choose method to execute.code will not work if we have 2 @Getmapping without value.
    public List<Journal_v1> getAllJournal(){ //Methods inside a controller class should be public so that they can be accessed and invoke by spring framework or external HTTP requests.
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody Journal_v1 myEntry){ //@RequestBody will say "hey spring,Please take the data from Request and turn it into a java object that i can use in code".
        journalEntries.put(myEntry.getId(),myEntry);
        return true;
    }

    @GetMapping("id/{myId}")  //Here in value of @GetMapping("id/{myId}")=> "id" is path and "{myId}" is a path variable. Postman request will look like GET:localhost:8080/journal/id/2
    public Journal_v1 getJournalById(@PathVariable long myId){
       return journalEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")  // With same annotation value "(id/{myId})" we can not have two @GetMapping. So we need to us Annotation as per method working.We can Use @DeleteMapping which is different than, @GetMapping with same value eg: @DeleteMapping("id/{myId}").
    public Journal_v1 deleteEntry(@PathVariable long myId){
        return journalEntries.remove(myId);
    }

    @PutMapping("id/{id}") // If you want to update any entry value in Map-list with its id or (key),use PutMapping Annotation in application and request with updated JSON body with POST through Postman/web.
    public Journal_v1 updateEntry(@PathVariable long id, @RequestBody Journal_v1 myEntry){ //path value{../{id}} and  variable of (@PathVariable type "id") should be same.
        return journalEntries.put(id,myEntry);
    }

}
// Note:- 1) GET Request from postman/web only use for getting the data, If you try hitting GET request with JSON body it will not pass body.
//        2) POST Request use for passing the data in raw or JSON body to application.