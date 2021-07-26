package com.project.room.services;

import com.project.room.models.Room;

public interface ILocationValidator {

    void validateLocation(String ip, String roomCountry);
}
