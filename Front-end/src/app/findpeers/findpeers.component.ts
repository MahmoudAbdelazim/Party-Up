import { Component, OnInit } from '@angular/core';
import {FindPeersService} from "../find-peers.service";
import {FindPeersPayload} from "./find-peers-payload";
import {PlayerDetailsService} from "../player-details.service";
import {ProfileDetailsGetPayload} from "../profile/profile-details-get.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-findpeers',
  templateUrl: './findpeers.component.html',
  styleUrls: ['./findpeers.component.scss']
})
export class FindpeersComponent implements OnInit {

  findPeersList : FindPeersPayload[]
  playerDetails : ProfileDetailsGetPayload
  selectedGame : string

  imgBlob : Blob
  imgSrc : string
  imgTrustedSrc : SafeResourceUrl

  constructor(private findPeersService : FindPeersService , private playerDetailsService : PlayerDetailsService) {

    this.findPeersList = [];
    this.playerDetails = {
      username : '',
      email : '',
      firstName : '',
      lastName : '',
      discordTag : '',
      handles : [],
      profilePicture : {
        id : '',
        type : '',
        size : 0,
        url : ''
      },
      country: {
        name : ""
      }
    };
    this.selectedGame = '';
    this.imgSrc = '';
    this.imgBlob = new Blob();
    this.imgTrustedSrc = '';
  }

  favouriteGame = new FormGroup({
    game: new FormControl()
  })

  ngOnInit(): void {
    this.playerDetailsService.getPlayerDetails().subscribe(playerData  =>{
      this.playerDetails = playerData;
      console.log(this.playerDetails);
      console.log(this.playerDetails);
    })

  }
  selectingFavouriteGame(){
    this.selectedGame = this.favouriteGame.get('game')?.value;
    this.findPeersService.findPeersList(this.selectedGame).subscribe(data =>{
      this.findPeersList = data;
      console.log(this.findPeersList);
    })
  }

}
