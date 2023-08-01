import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private URL = 'http://localhost:4100/reservation';
  private URL2 = 'http://localhost:4100/guest';

  constructor(private http: HttpClient) {}

  createReservation(data: any){
    return this.http.post<any>(`${this.URL}`, data);
  }

  saveguest(data: any){
    return this.http.post<any>(`${this.URL2}`, data);
  }
}
