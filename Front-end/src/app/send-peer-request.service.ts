import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SendPeerRequestService {

  constructor(private _httpClient:HttpClient) { }

  sendPeerRequest(userName : string) : Observable<any> {
    let url = 'http://localhost:8080/api/addPeer/' + userName;
    return this._httpClient.post(url , {} , {responseType : 'text'});
  }
}
