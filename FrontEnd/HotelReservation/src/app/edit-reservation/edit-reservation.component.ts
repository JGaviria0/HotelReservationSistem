import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HotelService } from '../services/hotel/hotel.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { DateFilterFn, MatCalendarCellClassFunction, MatDatepicker } from '@angular/material/datepicker';
import { ReservationService } from '../services/hotel/reservation.service';
import { ToastrService } from 'ngx-toastr';

interface Client {
  document: string;
} 

interface Room {
  noRooms: number[];
  clients: Client[];
}

@Component({
  selector: 'app-edit-reservation',
  templateUrl: './edit-reservation.component.html',
  styleUrls: ['./edit-reservation.component.css']
})
export class EditReservationComponent {
  hotel: any;
  picker2: MatDatepicker<any> | undefined
  rooms: Room[] = []; 
  indexrooms: number = 0
  roomsvalues: number[] = [0,0,0,0];
  disponibilityCheck : boolean = true; 
  defaultValue: [number[]] = [ [0]]

  init_date_form: Date = new Date(); 
  end_date_form: Date = new Date(); 

  myHolidayDates = [ new Date("8/1/2023")];

  clients: any[] = [];
  id : string | null; 
  user_document: number; 
  alldata : any; 
  initDate: Date | undefined; 
  endDate: Date | undefined; 

  constructor(
    private formBuilder: FormBuilder,
     private hotelService: HotelService,
     private authService: AuthService, 
     private route : ActivatedRoute, 
     private reservatoionService: ReservationService,
     private router: Router,
     private toastr: ToastrService
     ) {
    this.id = this.route.snapshot.paramMap.get('hotel_id');
    let ls = localStorage.getItem("user");
    if(ls ==null){
      this.router.navigate([`/login`]);
    }
    this.user_document = JSON.parse(ls? ls: "").document;
    let jsonuser = JSON.parse(ls? ls: "{}");

    if(jsonuser.userType != "Client"){
      this.router.navigate([`/login`]);
    }
    

    hotelService.gethotelById(this.id? this.id : "").subscribe((e: any) => {
      this.hotel = e; 
      this.reservatoionService.getReservation(this.id ? this.id: "", this.user_document).subscribe((res) => {
        
        this.initDate =new Date(res.init_date); 
        this.endDate =new Date(res.end_date); 
        this.roomsvalues = [
          this.hotel.noSingle - res.no_single,
          this.hotel.noDouble - res.no_double,
          this.hotel.noTriple - res.no_triple,
          this.hotel.noQuad -   res.no_quad
        ]
  
        this.reservatoionService.getGuestHotelandUser(this.id ? this.id: "", this.user_document.toString()).subscribe((val) =>{
  
          let norooms = [res.no_single,
            res.no_double,
            res.no_triple,
            res.no_quad,
          ]

          let pos = 0;
          for(let i=0; i<4; i++){
            for(let j=0; j<norooms[i]; j++){
              let documents: number[] = [];
              for(let k=0; k<i+1; k++){
                documents.push(val[pos].document)
                pos++; 
              }
              this.addRoom(documents);
            }
          }
  
        });
      })
    });

   }

  deleteguest(){
    return new Promise( async(res, rej) => {
      let guestReservations = await this.reservatoionService.getGuestHotelandUser(this.id ? this.id: "", this.user_document.toString()).toPromise();
      for (const res of guestReservations) {
        try{
          await this.reservatoionService.deleteGuest(res.hotel_id, res.principal_client_id, res.document).toPromise();
        } catch(e){
          console.log("eliminado");
        }
      }
      res("ok"); 
    })
  }

  addguest(formData: any, documnet: any){
    return new Promise( async(res, rej) => {
      console.log(formData)
      for (const property in formData){
        const re = /document-*/;
        let guest = {
          hotel_id: this.hotel.hotel_id,
          principal_client_id: documnet,
          document:formData[property],
        }
        if(property.match(re)) {
          this.reservatoionService.saveguest(guest).subscribe((val) => {
            console.log("guardado  " ,val);
          });
        }
      }
      res("ok"); 
    })
  }

  async onSubmit(formData: any) {

    console.log("form data: ", formData);
    const newreservation = formData;
    let user = localStorage.getItem("user");
    let documnet = user? JSON.parse(user).document : 0; 
    let numberOfRooms = [0,0,0,0];
    let numberOfRoomscheck = [0,0,0,0];

    this.rooms.map((ele) => {
      numberOfRooms[ele.noRooms.length-1]++; 
    });

    if(numberOfRooms[0] == 0 && numberOfRooms[1] == 0 &&numberOfRooms[2] == 0 &&numberOfRooms[3] == 0){
      this.norifyError("Debe escoger al menos una habitacion", "Error");
      return; 
    }

    for (const property in formData){
      const re = /document-*/;
      if(property.match(re) &&  formData[property] <= 10000) {
        this.norifyError("Los documentos deben ser mayores a 5 digitos", "Error");
        return;
      }
    }

    let reservationvalues = {
      no_single: numberOfRooms[0],
      no_double: numberOfRooms[1],
      no_triple: numberOfRooms[2],
      no_quad: numberOfRooms[3]
    }


    await this.deleteguest();    
    await this.addguest(formData, documnet); 

    this.reservatoionService.updateReservation(this.id ? this.id: "", this.user_document, reservationvalues).subscribe(() => {

      this.router.navigate(['/myreservations']); 
    })
  }

  onDeleteClient(client: any): void {
    const index = this.rooms.indexOf(client);
    this.rooms.splice(index, 1);
  }

  myHolidayFilter: DateFilterFn<Date | null> = (d: Date | null): boolean => {
    if (d === null) {
      return false;
    }

    const today = new Date();
    const time = d.getTime();
    return !this.myHolidayDates.find(x => x.getTime() === time) && d >= today;
  }

  addRoom(max: number[]) {
    this.roomsvalues[max.length -1]--; 
    let noRooms = [];
    for(let i=0; i<max.length; i++){
      noRooms.push(max[i]);
    }
    this.rooms.push({ noRooms: noRooms, clients: []});

  }

  addRoom2(max: number) {
    this.roomsvalues[max -1]--; 
    let noRooms = [];
    for(let i=0; i<max; i++){
      noRooms.push(i);
    }
    this.rooms.push({ noRooms: noRooms, clients: []});

  }

  removeClient(room: Room, index: number) {
    room.clients.splice(index, 1);
  }

  norifyError(message: string, title: string) {
    this.toastr.error(message, title, {
      timeOut: 4000,
      progressBar: true,
      progressAnimation: 'increasing',
      closeButton: true,
      positionClass: 'toast-top-right'
    });
  }
}
