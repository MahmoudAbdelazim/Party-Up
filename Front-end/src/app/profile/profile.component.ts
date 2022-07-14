import { Component, OnInit } from '@angular/core';
import {PlayerDetailsService} from "../player-details.service";
import {Router} from "@angular/router";
import {ProfileDetailsGetPayload} from "./profile-details-get.payload";
import {GetUploadedImageService} from "../get-uploaded-image.service";
import {DomSanitizer, SafeResourceUrl, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  playerDetails : ProfileDetailsGetPayload
  imgBlob : Blob
  imgSrc : string
  imgTrustedSrc : SafeResourceUrl

  constructor(private pdService : PlayerDetailsService ,
              private getPhotoService : GetUploadedImageService, private sanitizer : DomSanitizer,
              private router:Router) {

    this.playerDetails = {
      username : '',
      firstName: '',
      lastName: '',
      email : '',
      discordTag : '',
      handles : [],
      profilePicture : {
        id : '',
        type : '',
        size : 0,
        url : ''
      }
    };
    this.imgSrc = '';
    this.imgBlob = new Blob();
    this.imgTrustedSrc = '';
  }



  ngOnInit(): void {
    this.pdService.getPlayerDetails().subscribe(data =>{
      this.playerDetails = data;
      console.log(this.playerDetails);
      if (this.playerDetails.profilePicture){
        this.getPhotoService.getUploadedImage(this.playerDetails.profilePicture.url).subscribe(data=>{
          this.imgBlob = data;
          console.log(this.imgBlob);
          let reader = new FileReader();
          reader.readAsDataURL(this.imgBlob);
          reader.onload = (event: any) =>{
            this.imgSrc = event.target.result;

          }
        })
      }



    })
  }



}
