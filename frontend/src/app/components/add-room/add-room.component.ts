import { Component, OnInit } from '@angular/core';
import { Room } from 'src/app/models/room.model';
import { RoomService } from 'src/app/services/room.service';
import { getNames } from 'country-list'

@Component({
  selector: 'app-add-room',
  templateUrl: './add-room.component.html',
  styleUrls: ['./add-room.component.css']
})
export class AddRoomComponent implements OnInit {

  countryList = getNames();

  room: Room = {
    name: '',
    countryName: '',
    status: 0
  };

  submitted = false;

  constructor(private roomService: RoomService) { }

  ngOnInit(): void { }

  saveRoom(): void {
    const data = {
      name: this.room.name,
      countryName: this.room.countryName
    };

    this.roomService.create(data).subscribe(
      response => {
        console.log(response);
        this.submitted = true;
      },
      error => {
        console.log(error);
      });
  }

  newRoom(): void {
    this.submitted = false;
    this.room = {
      name: '',
      countryName: '',
      status: false
    }
  }
}
