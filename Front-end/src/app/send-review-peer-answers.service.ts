import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SendReviewAnswersPayload} from "./review-peer/send-review-answers.payload";

@Injectable({
  providedIn: 'root'
})
export class SendReviewPeerAnswersService {

  constructor(private _httpClient:HttpClient) { }

  sendReviewAnswers(userName : string , reviewAnswersPayload : SendReviewAnswersPayload[]) : Observable<any> {
    let url = 'http://localhost:8080/api/peerReview/' + userName;
    return this._httpClient.post(url , reviewAnswersPayload , {responseType : 'text'});
  }
}
