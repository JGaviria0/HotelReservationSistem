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

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.authService.login(this.username, this.password)) {
      console.log("logeado mi fai")
      this.router.navigate(['/']); // Cambia 'dashboard' por la ruta a la página tras el login exitoso
    } else {
      alert('Credenciales incorrectas. Inténtalo de nuevo.');
    }
  }
}
