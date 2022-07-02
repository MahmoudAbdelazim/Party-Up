import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PersonalityTestRequestPayload} from "./personality-test/personality-test-request.payload";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PersonalityTestService {

  constructor(private _httpClient:HttpClient) {
  }

  sendPersonalityTestAnswers(ptPayloadAnswers : PersonalityTestRequestPayload[]) : Observable<any>{
    return this._httpClient.post('http://localhost:8080/api/personalityTest', ptPayloadAnswers , {responseType : 'text'});
  }
}
