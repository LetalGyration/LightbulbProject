import {Injectable} from "@angular/core";

let SockJs = require("sockjs-client");
let Stomp = require("stompjs");

@Injectable()
export class WebSocketService {

  constructor() {
  }

  connect() {
    let socket = new SockJs(`http://localhost:8080/api/socket`);
    return Stomp.over(socket);
  }
}
