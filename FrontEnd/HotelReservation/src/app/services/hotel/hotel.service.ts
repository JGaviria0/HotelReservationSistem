import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HotelDTO } from 'src/app/models/hotelDTO.model';

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  private URL = 'http://localhost:4100/hotel';
   
  constructor(private http: HttpClient) {}

  gethotels(){
    return this.http.get(`${this.URL}`)
  }

  gethotelById(id: string){
    return this.http.get(`${this.URL}/${id}`)
  }

  deleteHotelById(id: string){
    return this.http.delete(`${this.URL}/${id}`)
  }

  gethotelsOwner(owner_id: String){
    return this.http.get(`${this.URL}/owner/${owner_id}`)
  }

  createHotel(data: HotelDTO){
    return this.http.post<any>(`${this.URL}`, data);
  }

  editHotel(id: string, data: HotelDTO){
    return this.http.put(`${this.URL}/${id}`, data);
  }
}
