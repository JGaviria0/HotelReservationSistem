import { Component } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(
    private authService: AuthService, 
    private router: Router

  ) {}
  
  logged: boolean = this.checkLogged();
  owner: boolean = this.checkOwner();

  checkLogged(): boolean{

    let name = localStorage.getItem("user");
    if(name) return true;
    return false; 
  }

  checkOwner(): boolean{
    let res = localStorage.getItem("user");
    let user = res? res : "{}";

    let userjson = JSON.parse(user);
    console.log(userjson);
    
    if(userjson.userType == "owner") return true;
    return false; 
  }

  logout(){
    localStorage.removeItem("user");
    this.router.navigate(['/login']);
  }
}
