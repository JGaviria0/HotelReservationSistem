import { Component } from '@angular/core';
import { ReservationService } from '../services/hotel/reservation.service';
import { HotelService } from '../services/hotel/hotel.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-myreservations',
  templateUrl: './myreservations.component.html',
  styleUrls: ['./myreservations.component.css']
})
export class MyreservationsComponent {

  myreservations: any; 

  constructor(
    private reservationService : ReservationService,
    private hotelService: HotelService,
    private router : Router, 
    ){
    let id = localStorage.getItem('user')
    let document = JSON.parse(id? id : " ").document; 
    let jsonuser = JSON.parse(id? id: "{}");

    if(jsonuser.userType != "Client"){
      this.router.navigate([`/login`]);
    }
    this.reservationService.getGuestReservations(document).subscribe((res) => {
      console.log(res.init_date);
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

  async deletereservation(hotel_id: string, user_id: string ){

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
      let value = await this.reservationService.deleteReservation(hotel_id, user_id).toPromise();
      console.log(value);
    } catch(e){
      console.log(e);
      window.location.reload();
    }
  }


  edit(hotel_id: string ){
    this.router.navigate([`/editreservation/${hotel_id}`]);
  }

}
