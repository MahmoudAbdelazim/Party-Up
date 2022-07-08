import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AcceptDeclinePayload} from "./others-profile/accept-decline.payload";

@Injectable({
  providedIn: 'root'
})
export class AcceptingOrRejectingTheRequestService {

  constructor(private _httpClient:HttpClient) { }

  accept(response : string , username : string){
    let url = 'http://localhost:8080/api/respondPeerRequest/' + username;
    return this._httpClient.post(url , response , {responseType : 'text'});
  }

  reject(response : string ,username :string){
    let url = 'http://localhost:8080/api/respondPeerRequest/' + username;
    return this._httpClient.post(url , response , {responseType : 'text'});
  }
}
