import { Component, OnInit } from '@angular/core';
import { Room } from 'src/app/models/room.model';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css']
})
export class RoomListComponent implements OnInit {

  rooms?: Room[];
  currentRoom: Room = {};
  currentIndex = -1;

  constructor(private roomService: RoomService) { }

  ngOnInit(): void {

  }

  retrieveRooms(): void {
    this.roomService.getAll().subscribe(
      data => {
        this.rooms = data;
        console.log(data);
      },
      error => {
        console.log(error);
      });
  }

  refreshList(): void {
    this.retrieveRooms();
    this.currentRoom = {};
    this.currentIndex = -1;
  }

  setActiveRoom(room: Room, index: number): void {
    this.currentRoom = room;
    this.currentIndex = index;
  }

}
