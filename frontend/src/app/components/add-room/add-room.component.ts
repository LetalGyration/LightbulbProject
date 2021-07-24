import { Component, OnInit } from '@angular/core';
import { Room } from 'src/app/models/room.model';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-add-room',
  templateUrl: './add-room.component.html',
  styleUrls: ['./add-room.component.css']
})
export class AddRoomComponent implements OnInit {

  room: Room = {
    room_name: '',
    country_name: '',
    status: 0
  };
  submitted = false;

  constructor(private roomService: RoomService) { }

  ngOnInit(): void {
  }

  saveRoom(): void {
    const data = {
      room_name: this.room.room_name,
      country_name: this.room.country_name
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
      room_name: '',
      country_name: '',
      status: false
    }
  }

}
