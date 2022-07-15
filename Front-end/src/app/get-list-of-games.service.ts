import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GetListOfGamesService {

  constructor(private _httpClient:HttpClient) { }

  getListOfAvailableGames() : Observable<any>{
    return this._httpClient.get('http://localhost:8080/api/games');
  }
}
