package org.journalApp.ExternalApiDemo;

import org.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class WeatherService {

    //we are using this apiKey which is provided by weatherstack.com
//    private static final String apiKey= "b4e80d9fb9f0d7fb14d0ee4cccc28950";
    //OR
    @Value("${weather.api.key}") //You can provide this value of apiKey through .yml using @value annotation.
    private String apiKey;

    private static final String API="http://api.weatherstack.com/current?accesskey=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){ //With HttpMethod.GET

        String finalApi= API.replace("CITY",city).replace("API_KEY",apiKey);
        // converting json format repose to object format => "deSerializing"
        ResponseEntity<WeatherResponse> response =restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body= response.getBody(); //body = current=>(temperature,weatherDescp,feelslike)
        return  body;
    }

    public WeatherResponse postWeather(String city){ //With HttpMethod.POST

        String finalApi= API.replace("CITY",city).replace("API_KEY",apiKey);

        //to pass string request
        String requestBody="{\n" + "\"userName\":\"Ram\"\n +" + "\"password\":\"Ram\"\n" + "} ";
        HttpEntity<String> httpEntity= new HttpEntity<>(requestBody);

        ResponseEntity<WeatherResponse> response =restTemplate.exchange(finalApi, HttpMethod.POST,httpEntity, WeatherResponse.class);
        WeatherResponse body= response.getBody(); //body = current=>(temperature,weatherDescp,feelslike)
        return  body;
    }


}
