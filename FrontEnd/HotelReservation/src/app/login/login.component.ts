import { Component } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string | undefined ;

  constructor(private authService: AuthService, private router: Router) {}

  async onSubmit(): Promise<void> {
    try{
      if (await this.authService.login(this.username, this.password)) {
        let user = localStorage.getItem('user');
        let jsonuser = JSON.parse(user? user: "{}")
        if(jsonuser.userType == "owner"){
          this.router.navigate(['/myhotels']); 
        }
        else{
          this.router.navigate(['/']); 
        }
      } else {
        this.errorMessage = "Credenciales incorrectas. Inténtalo de nuevo.";
      }
    } catch(e: any) {
      this.errorMessage = e.error.message;
    }
  }
}
