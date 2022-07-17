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

  sendPersonalityTestAnswers(ptPayloadAnswers : PersonalityTestRequestPayload[] , userName : string) : Observable<any>{
    let url : string = 'http://localhost:8080/api/personalityTest/' + userName;
    return this._httpClient.post( url , ptPayloadAnswers , {responseType : 'text'});
  }

  getPersonalityTestQuestions() : Observable<any>{
    return this._httpClient.get("http://localhost:8080/api/personalityTest/");
  }

}
