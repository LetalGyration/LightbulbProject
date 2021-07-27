package com.project.room.services;

import com.project.room.utils.InvalidLocationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationValidatorService implements ILocationValidator {

    @Override
    public void validateLocation(String ip, String roomCountry) {

        String ipLocationFinder = ("https://ipapi.co/" + ip + "/country_name");
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(ipLocationFinder, String.class);

        if (response == null || response.equals("Undefined")) {
            response = "Belarus";
        }

        if (!response.equals(roomCountry)) {
            throw new InvalidLocationException("Room country not equals to your country");
        }
    }
}
