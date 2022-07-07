import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AcceptDeclinePayload} from "./others-profile/accept-decline.payload";

@Injectable({
  providedIn: 'root'
})
export class UnpeerService {

  constructor(private _httpClient:HttpClient) { }

  unPeer(username : string){
    let url = 'http://localhost:8080/api/unpeer/' + username;
    return this._httpClient.post(url , {} , {responseType : 'text'});
  }
}
