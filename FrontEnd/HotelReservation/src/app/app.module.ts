import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './login/login.component';
import { Route, RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import { HotelsComponent } from './hotels/hotels.component';
import { MyhotelsComponent } from './myhotels/myhotels.component';
import { CreateHotelComponent } from './create-hotel/create-hotel.component';
import { EditHotelComponent } from './edit-hotel/edit-hotel.component';
import { ReservationComponent } from './reservation/reservation.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MyreservationsComponent } from './myreservations/myreservations.component';
import { EditReservationComponent } from './edit-reservation/edit-reservation.component';
import { ToastrModule } from 'ngx-toastr';
import { InfoHotelComponent } from './info-hotel/info-hotel.component';
import { InfoReservationComponent } from './info-reservation/info-reservation.component';

const appRoutes: Routes= [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'hotels', component: HotelsComponent},
  {path: 'myhotels', component: MyhotelsComponent},
  {path: 'createhotel', component: CreateHotelComponent},
  {path: 'edithotel/:id', component: EditHotelComponent},
  {path: 'reservation/:id', component: ReservationComponent},
  {path: 'myreservations', component: MyreservationsComponent},
  {path: 'infohotel/:id', component: InfoHotelComponent},
  {path: 'inforeservation/:hotel_id/:user_id', component: InfoReservationComponent},
  {path: 'editreservation/:hotel_id', component: EditReservationComponent},
]

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    HotelsComponent,
    MyhotelsComponent,
    CreateHotelComponent,
    EditHotelComponent,
    ReservationComponent,
    MyreservationsComponent,
    EditReservationComponent,
    InfoHotelComponent,
    InfoReservationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    FullCalendarModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      progressBar: true,
      progressAnimation: 'increasing',
      closeButton: true,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
      newestOnTop: true
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
