package org.journalApp.versionBackup.ControllerBackup;
//++ First version on code: v1 ++//
//this version do not contain use of Mongo db not data remained store if we reset the application

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck_v1 {  //class used for health check of application

   @GetMapping("/health-check")
   public String healthCheck(){
        return "Ok";
    }
}
