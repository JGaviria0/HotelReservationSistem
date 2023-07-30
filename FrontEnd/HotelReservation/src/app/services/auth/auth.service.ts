import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from 'src/app/models/user.model';
import { UserDTO } from 'src/app/models/userDTO.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedIn = false;
  private URL = 'http://localhost:4100/user';

  constructor(private http: HttpClient) {}

  async login(username: string, password: string): Promise<boolean> {
    
    const user = await this.http.get<UserDTO>(`${this.URL}/${username}`).toPromise();
    const correctPassword = user? user.password: "";

    if (password === correctPassword) {
      this.loggedIn = true;
      localStorage.setItem('user', username);
      return true;
    } else {
      return false;
    }
  }

  register(data: UserModel){
    this.http.post<any>(`${this.URL}`, data).subscribe( (res) => {
      console.log("succesfully", res);
    })
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  logout(): void {
    this.loggedIn = false;
  }
}
