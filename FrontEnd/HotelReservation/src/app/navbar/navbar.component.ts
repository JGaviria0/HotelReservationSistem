import { Component } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  name: string = ""; 

  constructor(
    private authService: AuthService, 
    private router: Router

    ) {}
  
  logged: boolean = this.checkLogged();

  checkLogged(): boolean{

    let name = localStorage.getItem("user");
    if(name) return true;
    return false; 
  }

  logout(){
    localStorage.removeItem("user");
    this.router.navigate(['/login']);
  }
}
