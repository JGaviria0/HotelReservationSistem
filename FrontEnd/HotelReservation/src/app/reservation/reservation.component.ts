import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  noRooms: number[];
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
    end_date: [null, [Validators.required]],
  });

  clients: any[] = [];
  id : string | null; 
  user_document: number; 
  alldata : any; 

  constructor(private formBuilder: FormBuilder, private hotelService: HotelService,private authService: AuthService, private route : ActivatedRoute, private reservatoionService: ReservationService) {
    this.id = this.route.snapshot.paramMap.get('id');
    hotelService.gethotelById(this.id? this.id : "").subscribe((e) => {
      this.hotel = e; 
      console.log(this.hotel);
    });

    let ls = localStorage.getItem("user");
    this.user_document = JSON.parse(ls? ls: "").document;
    
   }

  registerForm = this.formBuilder.group({
    init_date: ['', Validators.required],
    end_date: ['', Validators.required],
  });

  ngOnInit(): void {
  }

  onSubmit(formData: any) {
    console.log(formData);

      const newreservation = formData;
      let user = localStorage.getItem("user");
      let documnet = user? JSON.parse(user).document : 0; 
      let reservationvalues = {
        hotel_id: this.hotel.hotel_id,
        user_id: documnet,
        init_date: newreservation.init_date,
        end_date: newreservation.end_date,
        status: "Active"
      }
      
      this.reservatoionService.createReservation(reservationvalues).subscribe((res) => {
        console.log(res);
        let guest = {
          hotel_id: this.hotel.hotel_id,
          principal_client_id: documnet,
          document:documnet
        }
        this.reservatoionService.saveguest(documnet).subscribe((val) => {
          console.log(val);
        });

        for (const property in formData) {
          const re = /document-*/;
          let guest = {
            hotel_id: this.hotel.hotel_id,
            principal_client_id: documnet,
            document:formData[property]
          }
          if(property.match(re)) {
            this.reservatoionService.saveguest(guest).subscribe((val) => {
              console.log(val);
            });
          }
        }
      });
   
  }

  onDeleteClient(client: any): void {
    const index = this.rooms.indexOf(client);
    this.rooms.splice(index, 1);
    console.log(this.rooms, index);
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

  addRoom(max: number) {
    
    let noRooms = [];
    for(let i=0; i<max; i++){
      noRooms.push(i);
    }
    this.rooms.push({ noRooms: noRooms, clients: [] });
  }

  removeClient(room: Room, index: number) {
    room.clients.splice(index, 1);
  }
}
