import { Component } from '@angular/core';
import { HotelDTO } from 'src/app/models/hotelDTO.model';
import { HotelService } from '../services/hotel/hotel.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hotels',
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.css']
})
export class HotelsComponent {
  hotels: any;

  constructor(private hotelService: HotelService, private router: Router){
    let user = localStorage.getItem('user');
    let jsonuser = JSON.parse(user? user: "{}");

    if(jsonuser.userType != "Client"){
      this.router.navigate([`/login`]);
    }

    this.hotelService.gethotels().subscribe((res) => {
      this.hotels = res; 
      console.log(this.hotels);
    });
  }


  makeReservation(hotel: HotelDTO): void {
    console.log(`Reservando ${hotel.name}`);
    this.router.navigate([`/reservation/${hotel.hotel_id}`]); 
  }
}
