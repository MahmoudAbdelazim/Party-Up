import { Component, OnInit } from '@angular/core';
import {FeedService} from "../feed.service";
import {BehaviorSubject} from "rxjs";
import * as _ from 'lodash';
@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements OnInit {

  batch = 2
  lastkey = ''
  finished = false

  constructor(private feedService : FeedService) { }

  ngOnInit(): void {

  }

  onScroll() {
    console.log('Scrolled!')
  }


}
