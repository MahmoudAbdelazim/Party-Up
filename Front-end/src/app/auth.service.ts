import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequestPayload } from './login/login.request.payload';
import { LoginResponse } from './login/login.response.payload';
import {RegisterRequestPayload} from "./register/register-request.payload";
import { of, pipe } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LocalStorageService } from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _httpClient:HttpClient, private localStorage: LocalStorageService) { }

  register(registerPayload : RegisterRequestPayload) : Observable<any>{
    return this._httpClient.post('http://localhost:8080/api/auth/signup', registerPayload, {responseType : 'text'});
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<any>{ //in the video is observable<boolean>
     return this._httpClient.post<LoginResponse>('http://localhost:8080/api/auth/signin', loginRequestPayload)
      .pipe(map(data => {
        this.localStorage.store('authenticationToken', data.authenticationToken);
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresAt', data.expiresAt);

        return true;
     }));
  }
}
