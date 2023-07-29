import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserDTO } from 'src/app/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedIn = false;
  private URL = 'http://localhost:4000/user';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): boolean {

    const user = this.http.get(`${this.URL}/${username}`).subscribe((res) => {
      console.log(res);

    });
    // const correctPassword: string = user ? user.password : "";
    
    // if (password === correctPassword) {
    //   this.loggedIn = true;
    //   localStorage.setItem('user', username);
    //   return true;
    // }
    return false;
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  logout(): void {
    this.loggedIn = false;
  }
}
