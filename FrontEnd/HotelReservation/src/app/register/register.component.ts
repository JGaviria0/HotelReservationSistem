import { Component } from '@angular/core';
import { UserModel } from '../models/user.model';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: UserModel;
  formSubmitted: boolean = false;

  constructor(private authService: AuthService, private router: Router) {
    this.user = new UserModel();
  }

  onSubmit(): void {
    this.formSubmitted = true;
    console.log(this.user);
    this.authService.register(this.user).subscribe((res) => {
      this.router.navigate(['/login']);
    });
  }
}
