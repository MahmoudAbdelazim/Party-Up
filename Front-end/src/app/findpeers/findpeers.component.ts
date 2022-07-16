import { Component, OnInit } from '@angular/core';
import {FindPeersService} from "../find-peers.service";
import {FindPeersPayload} from "./find-peers-payload";
import {PlayerDetailsService} from "../player-details.service";
import {ProfileDetailsGetPayload} from "../profile/profile-details-get.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SafeResourceUrl} from "@angular/platform-browser";
import {GetUploadedImageService} from "../get-uploaded-image.service";

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
  imgSrc : string[]
  imgTrustedSrc : SafeResourceUrl
  dataObjects: any[]

  constructor(private findPeersService : FindPeersService , private playerDetailsService : PlayerDetailsService, private getPhotoService : GetUploadedImageService) {

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
    this.imgSrc = [];
    this.imgBlob = new Blob();
    this.imgTrustedSrc = '';
    this.dataObjects = [];
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
      this.dataObjects = []
      for (let i = 0; i < this.findPeersList.length ; i++) {
        if (this.findPeersList[i].profilePicture){
          this.getPhotoService.getUploadedImage(this.findPeersList[i].profilePicture.url).subscribe(data=>{
            this.imgBlob = data;
            console.log(this.imgBlob);
            let reader = new FileReader();
            reader.readAsDataURL(this.imgBlob);
            reader.onload = (event: any) =>{
              let dataObject = { userName : this.findPeersList[i].username , profilePicture : event.target.result}
              this.dataObjects.push(dataObject)


            }
          })
        }else{
          let dataObject = { userName : this.findPeersList[i].username , profilePicture : ''}
          this.dataObjects.push(dataObject)
        }
      }
      console.log(this.dataObjects);


    })
  }

}