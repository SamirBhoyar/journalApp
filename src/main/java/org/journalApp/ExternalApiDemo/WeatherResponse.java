package org.journalApp.ExternalApiDemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.List;

//here we have make pojo out of response we are getting from weatherstack api to store value which are in json.
@Data
public class WeatherResponse {

    private  Current current;

    @Data
    public class Current{

        private int temperature;

        @JsonProperty("Weather_Description")
        private List<Array> weatherDescp;

        private int feelslike;
    }

}
