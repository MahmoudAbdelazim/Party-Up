import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {RegisterRequestPayload} from "./register/register-request.payload";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _httpClient:HttpClient) { }

  register(registerPayload : RegisterRequestPayload) : Observable<any>{
    return this._httpClient.post('http://localhost:8080/api/auth/signup', registerPayload, {responseType : 'text'});
  }
}
