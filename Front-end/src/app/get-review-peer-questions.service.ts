import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GetReviewPeerQuestionsService {

  constructor(private _httpClient:HttpClient) { }

  getReviewPeersQuestions() : Observable<any>{
    return this._httpClient.get('http://localhost:8080/api/peerReview');
  }
}
