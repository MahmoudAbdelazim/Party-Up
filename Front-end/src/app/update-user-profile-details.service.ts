import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterRequestPayload} from "./register/register-request.payload";
import {Observable} from "rxjs";
import {UpdateUserProfilePayload} from "./profile-settings/update-user-profile.payload";

@Injectable({
  providedIn: 'root'
})
export class UpdateUserProfileDetailsService {

  constructor(private _httpClient:HttpClient) { }

  updateProfileDetails(updateProfilePayload : UpdateUserProfilePayload) : Observable<any>{
    return this._httpClient.put('http://localhost:8080/api/profile', updateProfilePayload, {responseType : 'text'});
  }
}
