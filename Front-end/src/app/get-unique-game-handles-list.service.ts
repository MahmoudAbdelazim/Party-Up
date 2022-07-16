import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GetUniqueGameHandlesListService {

  constructor(private _httpClient:HttpClient) { }

  getUniqueGameList() : Observable<any>{
    return this._httpClient.get('http://localhost:8080/api/myGames');
  }
}
