import { Component } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string | undefined ;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private toastr: ToastrService
    ) {
    let user = localStorage.getItem('user');
    if (user != null){
      let jsonuser = JSON.parse(user? user: "{}");

      if(jsonuser.userType == "Client"){
        this.router.navigate([`/hotels`]);
      }

      if(jsonuser.userType == "owner"){
        this.router.navigate([`/myhotels`]);
      }
    }
    }

  async onSubmit(): Promise<void> {
    try{
      if (await this.authService.login(this.username, this.password)) {
        let user = localStorage.getItem('user');
        let jsonuser = JSON.parse(user? user: "{}")
        if(jsonuser.userType == "owner"){
          this.router.navigate(['/myhotels']); 
        }
        else{
          this.router.navigate(['/hotels']); 
        }
      } else {
        this.notifyError( "Credenciales incorrectas. Inténtalo de nuevo.", "Error");
      }
    } catch(e: any) {
      this.notifyError( e.error.message, "Error");
    }
  }

  notify(message: string, title: string) {
    this.toastr.success(message, title, {
      timeOut: 2000,
      progressBar: true,
      progressAnimation: 'increasing',
      closeButton: true,
      positionClass: 'toast-top-right'
    });
  }

  onKeyPress(event: KeyboardEvent) {
    const inputChar = event.key; 
    const pattern = /^[0-9]*$/;
    if (!pattern.test(inputChar) && event.key != "Backspace") {
      event.preventDefault();
    }
  }

  notifyError(message: string, title: string) {
    this.toastr.error(message, title, {
      timeOut: 4000,
      progressBar: true,
      progressAnimation: 'increasing',
      closeButton: true,
      positionClass: 'toast-top-right'
    });
  }
}
