import { Component } from '@angular/core';
import { ReservationService } from '../services/hotel/reservation.service';
import { HotelService } from '../services/hotel/hotel.service';

@Component({
  selector: 'app-myreservations',
  templateUrl: './myreservations.component.html',
  styleUrls: ['./myreservations.component.css']
})
export class MyreservationsComponent {

  myreservations: any; 

  constructor(private reservationService : ReservationService, private hotelService: HotelService){
    let id = localStorage.getItem('user')
    let document = JSON.parse(id? id : " ").document; 
    this.reservationService.getGuestReservations(document).subscribe((res) => {
      console.log(res);
      res.map((hotel: any) => {
        console.log("hotel id " ,hotel.hotel_id);
        this.myreservations = res; 
        this.hotelService.gethotelById(hotel.hotel_id).subscribe((ans) => {
          console.log(ans);
          hotel.hotel = ans; 
        })
      })
    });
  }

  async deletereservation(hotel_id: number, user_id: number ){

    let guestReservations = await this.reservationService.getGuestHotelandUser(hotel_id, user_id).toPromise();
    for (const res of guestReservations) {
      console.log(res);
      try{
        await this.reservationService.deleteGuest(res.hotel_id, res.principal_client_id, res.document).toPromise();
      } catch(e){
        console.log(e);
        console.log("eliminado");
      }
    }
    
    try{
      await this.reservationService.deleteReservation(hotel_id, user_id).toPromise();
    } catch(e){
      window.location.reload();
    }
  }
}
