package com.project.room.service;

import com.project.room.services.LocationValidatorService;
import com.project.room.utils.InvalidIpAddressException;
import com.project.room.utils.InvalidLocationException;
import org.junit.Before;
import org.junit.Test;

public class LocationValidatorUnitTests {
    private LocationValidatorService service;

    @Before
    public void setUp() {
        service = new LocationValidatorService();
    }

    @Test(expected = Test.None.class)
    public void whenValidationIsValid() {
        service.validateLocation("2.16.103.0", "Russia");
    }

    @Test(expected = InvalidLocationException.class)
    public void whenExceptionThrown_thenAssertionSucceeds() {
        service.validateLocation("2.16.103.0", "Belarus");
    }

    @Test(expected = InvalidIpAddressException.class)
    public void whenInvalidIp_thenThrownInvalidIpAddress() {
        service.validateLocation("2.16.103", "Russia");
    }
}
