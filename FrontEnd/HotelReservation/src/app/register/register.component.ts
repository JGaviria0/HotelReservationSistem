import { Component } from '@angular/core';
import { UserModel } from '../models/user.model';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: UserModel;

  constructor(private authService: AuthService, private router: Router) {
    this.user = new UserModel();
  }

  onSubmit(): void {
    console.log(this.user);
    this.authService.register(this.user);
    this.router.navigate(['/login']);


    // Aquí puedes agregar la lógica para enviar los datos al backend o realizar otras acciones
  }
}
