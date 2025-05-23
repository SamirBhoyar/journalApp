package org.journalApp.ExternalApiDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-weather") //we will use username and password to authenticate this request
public class WeatherController {

@Autowired
private WeatherService weatherService;
    //Note: Using this get for External Api Demo
    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse= weatherService.getWeather("Mumbai");
        String greeting= "";
        if(weatherResponse != null){
            greeting=", weather feels like" + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi"+authentication.getName()+greeting, HttpStatus.OK);
    }
}
