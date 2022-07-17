import { Component, OnInit } from '@angular/core';
import {GetListOfPeersService} from "../get-list-of-peers.service";
import {GetMyPeersPayload} from "./get-my-peers.payload";
import {SafeResourceUrl} from "@angular/platform-browser";
import {GetUploadedImageService} from "../get-uploaded-image.service";

@Component({
  selector: 'app-my-peers',
  templateUrl: './my-peers.component.html',
  styleUrls: ['./my-peers.component.scss']
})
export class MyPeersComponent implements OnInit {

  myPeers : GetMyPeersPayload[]
  imgBlob : Blob

  dataObjects: any[]

  constructor(private peerList : GetListOfPeersService, private getPhotoService : GetUploadedImageService) {
    this.myPeers = [];
    this.imgBlob = new Blob();
    this.dataObjects = [];
  }

  ngOnInit(): void {
    this.peerList.fetchPeersList().subscribe(data =>{
        console.log(data);
        this.myPeers = data;
      for (let i = 0; i < this.myPeers.length ; i++) {

        if (this.myPeers[i].profilePicture){
          this.getPhotoService.getUploadedImage(this.myPeers[i].profilePicture.url).subscribe(data=>{
            this.imgBlob = data;
            console.log(this.imgBlob);
            let reader = new FileReader();
            reader.readAsDataURL(this.imgBlob);
            reader.onload = (event: any) =>{

              let dataObject = { userName : this.myPeers[i].username , profilePicture : event.target.result}
              this.dataObjects.push(dataObject)
            }
          })
        }else {
          let dataObject = { userName : this.myPeers[i].username , profilePicture : ''}
          this.dataObjects.push(dataObject)
        }
      }
    })
  }

}
