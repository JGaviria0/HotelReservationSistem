import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HotelService } from '../services/hotel/hotel.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
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
  roomsvalues: number[] = [0,0,0,0];
  disponibilityCheck : boolean = false; 

  init_date_form: Date = new Date(); 
  end_date_form: Date = new Date(); 

  myHolidayDates = [ new Date("8/1/2023")];

  clients: any[] = [];
  id : string | null; 
  user_document: number; 
  alldata : any; 

  constructor(
    private formBuilder: FormBuilder,
     private hotelService: HotelService,
     private authService: AuthService, 
     private route : ActivatedRoute, 
     private reservatoionService: ReservationService,
     private router: Router
     ) {
    this.id = this.route.snapshot.paramMap.get('id');
    hotelService.gethotelById(this.id? this.id : "").subscribe((e: any) => {
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
      let numberOfRooms = [0,0,0,0];

      this.rooms.map((ele) => {
        numberOfRooms[ele.noRooms.length-1]++; 
      });

      let reservationvalues = {
        hotel_id: this.hotel.hotel_id,
        user_id: documnet,
        init_date: newreservation.init_date,
        end_date: newreservation.end_date,
        status: "Active",
        no_single: numberOfRooms[0],
        no_double: numberOfRooms[1],
        no_triple: numberOfRooms[2],
        no_quad: numberOfRooms[3]
      }

      
      this.reservatoionService.createReservation(reservationvalues).subscribe((res) => {
        console.log(res);
        let guest = {
          hotel_id: this.hotel.hotel_id,
          principal_client_id: documnet,
          document:documnet
        }
        console.log(guest);
        this.reservatoionService.saveguest(guest).subscribe((val) => {
          console.log(val);
        });

        for (const property in formData) {
          const re = /document-*/;
          let guest = {
            hotel_id: this.hotel.hotel_id,
            principal_client_id: documnet,
            document:formData[property],
          }
          if(property.match(re)) {
            this.reservatoionService.saveguest(guest).subscribe((val) => {
              console.log(val);
            });
          }
        }
        this.router.navigate(['/myreservations']); 
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

    const today = new Date();
    const time = d.getTime();
    return !this.myHolidayDates.find(x => x.getTime() === time) && d >= today;
  }

  addRoom(max: number) {
    this.roomsvalues[max-1]--; 
    let noRooms = [];
    for(let i=0; i<max; i++){
      noRooms.push(i);
    }
    this.rooms.push({ noRooms: noRooms, clients: [] });
  }

  removeClient(room: Room, index: number) {
    room.clients.splice(index, 1);
  }

  checkDisponibility(formData: any){
    console.log(formData);
    this.init_date_form =  formData.init_date;
    this.end_date_form = formData.end_date;
    this.disponibilityCheck = true; 

    this.rooms = []

    this.reservatoionService.getReservationbyhotel(Number(this.id? this.id : "0"), formData.init_date, formData.end_date).subscribe((res)=> {
      
      console.log(res); 
      this.roomsvalues[0] = this.hotel.noSingle - res.available_single;
      this.roomsvalues[1] = this.hotel.noDouble - res.available_double;
      this.roomsvalues[2] = this.hotel.noTriple - res.available_triple;
      this.roomsvalues[3] = this.hotel.noQuad - res.available_quad;
      console.log(this.roomsvalues);
    }, (e) => {
      console.log("error");
      this.roomsvalues[0] = this.hotel.noSingle;
      this.roomsvalues[1] = this.hotel.noDouble;
      this.roomsvalues[2] = this.hotel.noTriple;
      this.roomsvalues[3] = this.hotel.noQuad;
    });

  }
}
