import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelService } from '../services/hotel/hotel.service';

@Component({
  selector: 'app-edit-hotel',
  templateUrl: './edit-hotel.component.html',
  styleUrls: ['./edit-hotel.component.css']
})
export class EditHotelComponent {

  id: string | null;
  hotel: any; 
  formSubmitted: boolean = false;

  constructor(private route: ActivatedRoute, private hotelService: HotelService, private router: Router){
    this.id = this.route.snapshot.paramMap.get('id');
    this.hotelService.gethotelById(this.id? this.id: "").subscribe((res) => {
      this.hotel = res; 
      console.log(this.hotel);
    });
  }

  onSubmit(): void {
    this.formSubmitted = true;
    this.hotelService.editHotel(this.id? this.id : "", this.hotel).subscribe( (res) => {
      this.router.navigate(['/myhotels']);
    })
  }
}
