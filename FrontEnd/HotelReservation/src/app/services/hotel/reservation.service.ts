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

  getGuestReservations(id: number){
    return this.http.get<any>(`${this.URL}/getByGuest/${id}`);
  }

  getReservationbyhotel(id: number, init_date: Date, end_date: Date){
    let initdateparse = `${init_date.getFullYear()}-${init_date.getMonth()+1}-${init_date.getDate()}`
    let enddateparse = `${end_date.getFullYear()}-${end_date.getMonth()+1}-${end_date.getDate()}`
    console.log(initdateparse);
    console.log(enddateparse);
    return this.http.get<any>(`${this.URL}/disponibility/${id}?init_date=${initdateparse}&end_date=${enddateparse}`);
  }

  getGuestHotelandUser(hotel_id: number, user_id: number){
    return this.http.get<any>(`${this.URL2}/${hotel_id}/${user_id}`);
  }

  deleteReservation(hotel_id: number, user_id: number){
    return this.http.delete<any>(`${this.URL}/${hotel_id}/${user_id}`);
  }

  deleteGuest(hotel_id: number, principal_client_id: number ,user_id: number){
    return this.http.delete<any>(`${this.URL2}/${hotel_id}/${principal_client_id}/${user_id}`);
  }
}
