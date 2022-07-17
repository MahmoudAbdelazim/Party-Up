import { Component, OnInit } from '@angular/core';
import {FindPeersService} from "../find-peers.service";
import {FindPeersPayload} from "./find-peers-payload";
import {PlayerDetailsService} from "../player-details.service";
import {FormControl, FormGroup} from "@angular/forms";
import {SafeResourceUrl} from "@angular/platform-browser";
import {GetUploadedImageService} from "../get-uploaded-image.service";
import {UniqueGameListPayload} from "./unique-game-list.payload";
import {GetUniqueGameHandlesListService} from "../get-unique-game-handles-list.service";

@Component({
  selector: 'app-findpeers',
  templateUrl: './findpeers.component.html',
  styleUrls: ['./findpeers.component.scss']
})
export class FindpeersComponent implements OnInit {

  findPeersList : FindPeersPayload[]
  uniqueGameList: UniqueGameListPayload[]
  selectedGame : string

  imgBlob : Blob

  constructor(private findPeersService : FindPeersService ,
              private playerDetailsService : PlayerDetailsService,
              private getPhotoService : GetUploadedImageService, private getUniqueList : GetUniqueGameHandlesListService) {

    this.findPeersList = [];
    this.uniqueGameList = []
    this.selectedGame = '';
    this.imgBlob = new Blob();
  }

  favouriteGame = new FormGroup({
    game: new FormControl()
  })

  ngOnInit(): void {
    this.getUniqueList.getUniqueGameList().subscribe(data =>{
      this.uniqueGameList = data
      console.log(this.uniqueGameList)
    })
  }
  selectingFavouriteGame(){
    this.selectedGame = this.favouriteGame.get('game')?.value;
    this.findPeersService.findPeersList(this.selectedGame).subscribe(data =>{
      this.findPeersList = data;
      console.log(this.findPeersList);
      for (let i = 0; i < this.findPeersList.length ; i++) {
        if (this.findPeersList[i].profilePicture){
          this.getPhotoService.getUploadedImage(this.findPeersList[i].profilePicture.url).subscribe(data=>{
            this.imgBlob = data;
            console.log(this.imgBlob);
            let reader = new FileReader();
            reader.readAsDataURL(this.imgBlob);
            reader.onload = (event: any) =>{
              this.findPeersList[i].profilePicture.url = event.target.result;


            }
          })
        }
      }
      console.log(this.findPeersList)


    })
  }

}
