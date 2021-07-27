import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Room } from '../models/room.model';
import { environment } from "../../environments/environment";

const baseUrl = 'http://localhost:8080/api/rooms';
@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private http: HttpClient) { }
  getAll(): Observable<Room[]> {
    return this.http.get<Room[]>(baseUrl);
  }

  get(id: any): Observable<Room> {
    return this.http.get(`${baseUrl}/${id}`)
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  activate(id: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}/activate`, null)
  }
  deactivate(id: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}/deactivate`, null)
  }
}
