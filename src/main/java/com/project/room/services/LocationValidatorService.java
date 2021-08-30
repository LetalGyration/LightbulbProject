package com.project.room.services;

import com.project.room.models.Room;
import com.project.room.utils.InvalidIpAddressException;
import com.project.room.utils.InvalidLocationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationValidatorService implements ILocationValidator {

    @Override
    public void validateLocation(String ip, String roomCountry) {
            if ( ip == null || ip.isEmpty() ) {
                throw new InvalidIpAddressException("Ip address is not valid to IPv4");
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                throw new InvalidIpAddressException("Ip address is not valid to IPv4");
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    throw new InvalidIpAddressException("Ip address is not valid to IPv4");
                }
            }
            if ( ip.endsWith(".") ) {
                throw new InvalidIpAddressException("Ip address is not valid to IPv4");
            }

        String ipLocationFinder = "https://ipapi.co/" + ip + "/country_name/?key=sRA4QNHYCu9kaCOR3c5MD4XnY5IMdE2vMiH6S5ZZKOSuA0R2VZ";
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
