import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class EventService {

  private getEventByIdUrl = 'http://localhost:8080/events/event/';
  

  constructor(private http: HttpClient, private router: Router) { }

  getEvent(id: string) {
    return this.http.get(`${this.getEventByIdUrl}` + id);
  }

  updateEvent(id: string, value: any) {
    return this.http.put(`${this.getEventByIdUrl}` + id, value);
    //is this right?
  }

  deleteEvent(id: string) {
    return this.http.delete(`${this.getEventByIdUrl}` + id, { responseType: 'text'});
    //is this right?
  }


}