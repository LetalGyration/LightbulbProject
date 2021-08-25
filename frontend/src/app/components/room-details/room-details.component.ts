import {Component, OnInit} from '@angular/core';
import {RoomService} from 'src/app/services/room.service';
import {Room} from 'src/app/models/room.model';
import {ActivatedRoute} from '@angular/router';
import {WebSocketService} from "./websocket.service";
import {HttpClient} from "@angular/common/http";

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
    private service: WebSocketService,
    private http: HttpClient
  ) {
    this.initializeWebSocketConnection(service);
  }

  ngOnInit(): void {
    this.getRoom(this.route.snapshot.params.id);
  }

  getRoom(id: string): void {
    this.roomService.get(id).subscribe(
      data => {
        this.currentRoom = data;
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

  private initializeWebSocketConnection(webSocketService: WebSocketService) {

    let stompClient = webSocketService.connect();
    stompClient.connect({}, () => {
      stompClient.subscribe('/topic/notification', (data: any) => {
        console.log("Something has happened")
        console.log(data)
        this.getRoom(this.route.snapshot.params.id)
      })
    });
  }
}

