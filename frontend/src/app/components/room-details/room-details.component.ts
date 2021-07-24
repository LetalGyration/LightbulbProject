import { Component, OnInit } from '@angular/core';
import { RoomService } from 'src/app/services/room.service';
import { Room } from 'src/app/models/room.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-room-details',
  templateUrl: './room-details.component.html',
  styleUrls: ['./room-details.component.css']
})
export class RoomDetailsComponent implements OnInit {

  currentRoom: Room = {
    room_name: '',
    country_name: '',
    status: 0
  };
  message = '';
  constructor(
    private roomService: RoomService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.message = '';
    this.getRoom(this.route.snapshot.params.id);
  }

  getRoom(id: string): void {
    this.roomService.get(id).subscribe(
      data => {
        this.currentRoom = data;
        console.log(data);
      },
      error => {
        console.log(error);
      }
    )
  }

  updateStatus(newStatus: boolean): void {
    const data = {
      room_name: this.currentRoom.room_name,
      country_name: this.currentRoom.country_name,
      status: newStatus
    };
  }
}

