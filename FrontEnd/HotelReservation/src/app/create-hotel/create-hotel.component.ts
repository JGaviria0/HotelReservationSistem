import { Component } from '@angular/core';
import { HotelService } from '../services/hotel/hotel.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-create-hotel',
  templateUrl: './create-hotel.component.html',
  styleUrls: ['./create-hotel.component.css']
})
export class CreateHotelComponent {

  newHotel: any = {};
  formSubmitted: boolean = false;
  constructor(private hotelService: HotelService, private router: Router) {
    let user = localStorage.getItem('user');
    let jsonuser = JSON.parse(user? user: "{}");

    if(jsonuser.userType != "owner"){
      this.router.navigate([`/login`]);
    }
  }

  onSubmit(): void {
    this.formSubmitted = true;
    console.log(this.newHotel);
    
    let res = localStorage.getItem("user");
    let user = res? res : "{}";

    let userjson = JSON.parse(user);
    this.newHotel.owner_id = userjson.document;
    this.hotelService.createHotel(this.newHotel).subscribe( (res) => {
      this.router.navigate(['/myhotels']);
    })
  }
}
