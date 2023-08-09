import { Component } from '@angular/core';
import { HotelService } from '../services/hotel/hotel.service';
import { ReservationService } from '../services/hotel/reservation.service';
import { AuthService } from '../services/auth/auth.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-info-reservation',
  templateUrl: './info-reservation.component.html',
  styleUrls: ['./info-reservation.component.css']
})
export class InfoReservationComponent {
  guests: any;
  hotel_id: string | null; 
  user_id: string | null; 
  
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
    
    this.hotel_id = this.route.snapshot.paramMap.get('hotel_id');
    this.user_id = this.route.snapshot.paramMap.get('user_id');

    this.reservationService.getGuestHotelandUser(this.hotel_id ?this.hotel_id : '', this.user_id? this.user_id : "").subscribe((res) => {
      this.guests = res;
      this.guests.map((user: any) => {
        user.index = this.guests.indexOf(user) + 1; 
        this.userService.getUserByID(user.document).subscribe((value) => {
          user.user = value;
          console.log(user); 
        }, (err) => {
          console.log("no existe el usuario");
        })
      })
    })
  }


}
