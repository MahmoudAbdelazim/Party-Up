import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {feedResponsePayload} from "./feed/feed-response.payload";

@Injectable({
  providedIn: 'root'
})

// m4 fahmenha we hangarab lma amr ye5alas :(
export class FeedService implements OnInit {

  allFeed : Object

  constructor(private _httpClient:HttpClient) {
    this.allFeed = {
      text :'',
      images: [],
      videos:[]
    };
  }

  ngOnInit() {

  }

  getFeedResponse(batch : any , lastKey? : any){

    let query = {
      orderByKey: true,
      limitToFirst: batch,
    }

    if (lastKey) { // @ts-ignore
      query['startAt'] = lastKey
    }

    this._httpClient.get("http://localhost:8080/api/post/feed").subscribe(data => {

      this.allFeed = data;
    });
  }



}
