import {Component, OnInit} from '@angular/core';
import {RoomService} from 'src/app/services/room.service';
import {Room} from 'src/app/models/room.model';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-room-details',
  templateUrl: './room-details.component.html',
  styleUrls: ['./room-details.component.css']
})
export class RoomDetailsComponent implements OnInit {

  currentRoom: Room = {
    name: '',
    countryName: '',
    status: 0
  };

  constructor(
    private roomService: RoomService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.getRoom(this.route.snapshot.params.id);
  }

  getRoom(id: string): void {
    this.roomService.get(id).subscribe(
      data => {
        this.currentRoom = data;
        this.ngOnInit();
      },
      error => {
        console.log(error);
      }
    )
  }

  activateStatus(): void {
    this.roomService.activate(this.currentRoom.id).subscribe(
      error => {
        console.log(error);
      }
    )
  }

  deactivateStatus(): void {
    this.roomService.deactivate(this.currentRoom.id).subscribe(
      error => {
        console.log(error);
      }
    )
  }
}

