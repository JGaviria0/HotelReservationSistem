import { Component } from '@angular/core';
import { UserModel } from '../models/user.model';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: UserModel;
  formSubmitted: boolean = false;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private toastr: ToastrService
    ) {
    this.user = new UserModel();
  }

  onSubmit(): void {
    this.formSubmitted = true;
    console.log(this.user);
    this.authService.register(this.user).subscribe((res) => {
      this.router.navigate(['/login']);
    },(err) =>{
      this.notifyError(err.error.message, "Error")
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
