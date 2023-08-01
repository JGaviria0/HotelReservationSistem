import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private URL = 'http://localhost:4100/reservation';

  constructor(private http: HttpClient) {}

  createReservation(data: any){
    return this.http.post<any>(`${this.URL}`, data);
  }
}
