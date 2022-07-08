import { Component, OnInit } from '@angular/core';
import {GetListOfPeersService} from "../get-list-of-peers.service";
import {GetMyPeersPayload} from "./get-my-peers.payload";

@Component({
  selector: 'app-my-peers',
  templateUrl: './my-peers.component.html',
  styleUrls: ['./my-peers.component.scss']
})
export class MyPeersComponent implements OnInit {

  myPeers : GetMyPeersPayload[]

  constructor(private peerList : GetListOfPeersService) {
    this.myPeers = [];
  }

  ngOnInit(): void {
    this.peerList.fetchPeersList().subscribe(data =>{
        console.log(data);
        this.myPeers = data;
    })
  }

}
