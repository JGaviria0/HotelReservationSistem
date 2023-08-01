import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HotelService } from '../services/hotel/hotel.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { DateClickArg} from '@fullcalendar/interaction';
import { DateFilterFn, MatCalendarCellClassFunction, MatDatepicker } from '@angular/material/datepicker';
import { ReservationService } from '../services/hotel/reservation.service';

interface Client {
  document: string;
}

interface Room {
  type: string;
  clients: Client[];
}

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  hotel: any;
  picker2: MatDatepicker<any> | undefined
  rooms: Room[] = [];

  myHolidayDates = [

    new Date("8/1/2023"),

    new Date("12/20/2020"),

    new Date("12/17/2020"),

    new Date("12/25/2020"),

    new Date("12/4/2020"),

    new Date("12/7/2020"),

    new Date("12/12/2020"),

    new Date("12/11/2020"),

    new Date("12/26/2020"),

    new Date("12/25/2020")

];

  clientForm: FormGroup = this.formBuilder.group({
    document: [0, Validators.required]
  });

  ReservatioForm: FormGroup = this.formBuilder.group({
    init_date: [null, [Validators.required]],
    end_date: [null, [Validators.required]]
  });

  clients: any[] = [];
  id : string | null; 

  constructor(private formBuilder: FormBuilder, private hotelService: HotelService,private authService: AuthService, private route : ActivatedRoute, private reservatoionService: ReservationService) {
    this.id = this.route.snapshot.paramMap.get('id');
    hotelService.gethotelById(this.id? this.id : "").subscribe((e) => {
      this.hotel = e; 
      console.log(this.hotel);
    });
   }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.clientForm.valid) {
      const newClient = this.clientForm.value;
      
      this.authService.getUserByID(newClient.document).subscribe((res) => {
        newClient.name = res.name;
        this.clients.push(newClient);
        this.clientForm.reset();
      },
      (err) => {
        newClient.name = "No registrado";
        this.clients.push(newClient);
        this.clientForm.reset();
      } 
      );
    }
  }

  onDeleteClient(client: any): void {
    const index = this.clients.indexOf(client);
    if (index !== -1) {
      this.clients.splice(index, 1);
    }
  }
  myHolidayFilter: DateFilterFn<Date | null> = (d: Date | null): boolean => {
    if (d === null) {
      return false;
    }

    const time = d.getTime();
    return !this.myHolidayDates.find(x => x.getTime() === time);
  }

  makeReservation(): void {
    if (this.ReservatioForm.valid) {
      const newreservation = this.ReservatioForm.value;
      let user = localStorage.getItem("user");
      let documnet = user? JSON.parse(user).document : 0; 
      let reservationvalues = {
        hotel_id: this.hotel.hotel_id,
        user_id: documnet,
        init_date: newreservation.init_date,
        end_date: newreservation.end_date,
        status: "Active"
      }
      
      console.log(reservationvalues);
      
      this.reservatoionService.createReservation(reservationvalues).subscribe((res) => {
        console.log(res);
      });
      
    }
  }


  addRoom(type: string) {
    this.rooms.push({ type: type, clients: [] });
  }

  removeClient(room: Room, index: number) {
    room.clients.splice(index, 1);
  }
}
