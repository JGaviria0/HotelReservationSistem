import { Component } from '@angular/core';
import { HotelService } from '../services/hotel/hotel.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-myhotels',
  templateUrl: './myhotels.component.html',
  styleUrls: ['./myhotels.component.css']
})
export class MyhotelsComponent {
  hotels: any;
  
  constructor(private hotelService: HotelService, private router: Router){
    let user = localStorage.getItem('user');
    let jsonuser = JSON.parse(user? user: "{}")

    this.hotelService.gethotelsOwner(jsonuser.document).subscribe((res) => {
      this.hotels = res; 
      console.log(this.hotels);
    });
  }

  redirectedit(id: string){
    this.router.navigate([`/edithotel/${id}`]);
  }

  deletehotel(id: string){
    this.hotelService.deleteHotelById(id).subscribe( () => {
      window.location.reload();
    });
    window.location.reload();
    
  }
}
