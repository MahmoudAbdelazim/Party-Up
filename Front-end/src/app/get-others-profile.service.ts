import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GetOthersProfileService {

  constructor(private _httpClient:HttpClient) { }

  getOthersDetails(userName : string): Observable<any>{
    let url = 'http://localhost:8080/api/profile/' + userName;
    return this._httpClient.get(url);
  }
}
