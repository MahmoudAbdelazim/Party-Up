import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AddGamePayload} from "./add-game/add-game.payload";

@Injectable({
  providedIn: 'root'
})
export class AddGameService {

  constructor(private _httpClient:HttpClient) { }

  addGameWithHandle(addGamePayload : AddGamePayload): Observable<any>{
    return this._httpClient.post('http://localhost:8080/api/addGame' , addGamePayload , {responseType : 'text'});
  }
}
