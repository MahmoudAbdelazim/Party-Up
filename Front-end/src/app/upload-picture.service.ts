import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UploadPictureService {

  constructor(private _httpClient:HttpClient) { }

  UploadPhoto(photo : any) : Observable<any> {
    return this._httpClient.put('http://localhost:8080/api/profile/profilePic' , photo ,{responseType : 'text'} )
  }
}
