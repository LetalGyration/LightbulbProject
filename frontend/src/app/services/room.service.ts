import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Room } from '../models/room.model';

@Injectable({providedIn: 'root'})
export class RoomService {

  private baseUrl = 'http://localhost:8080/api/rooms';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Room[]> {
    return this.http.get<Room[]>(this.baseUrl);
  }

  get(id: any): Observable<Room> {
    return this.http.get(`${this.baseUrl}/${id}`)
  }

  create(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }

  activate(id: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/activate`, null)
  }

  deactivate(id: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/deactivate`, null)
  }
}
