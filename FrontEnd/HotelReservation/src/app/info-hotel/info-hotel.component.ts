import { Component } from '@angular/core';
import { HotelService } from '../services/hotel/hotel.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservationService } from '../services/hotel/reservation.service';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-info-hotel',
  templateUrl: './info-hotel.component.html',
  styleUrls: ['./info-hotel.component.css']
})
export class InfoHotelComponent {
  reservations: any;
  id: string | null; 
  
  constructor(
    private hotelService: HotelService, 
    private reservationService: ReservationService, 
    private userService: AuthService,
    private router: Router,
    private route : ActivatedRoute
    ){
    let user = localStorage.getItem('user');
    let jsonuser = JSON.parse(user? user: "{}");

    if(jsonuser.userType != "owner"){
      this.router.navigate([`/login`]);
    }

    this.id = this.route.snapshot.paramMap.get('id');
    this.reservationService.getAllReservationbyhotel(this.id? this.id: "").subscribe((res) => {
      this.reservations = res; 
      console.log(res); 
      res.map((reservation: any) => {
        this.userService.getUserByID(reservation.user_id).subscribe((user) => {
          reservation.userName = user.name; 
          reservation.init_date = reservation.init_date.substring(0,10);
          reservation.end_date = reservation.end_date.substring(0,10);
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

  redirectinfo(hotel_id: string, user_id: string){
    this.router.navigate([`/inforeservation/${hotel_id}/${user_id}`]); 
  }
}
