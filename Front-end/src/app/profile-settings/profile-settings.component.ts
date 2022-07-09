import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PlayerDetailsService } from '../player-details.service';
import { ProfileDetailsGetPayload } from '../profile/profile-details-get.payload';
import {UpdateUserProfilePayload} from "./update-user-profile.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UpdateUserProfileDetailsService} from "../update-user-profile-details.service";
import {ToastrService} from "../toastr.service";
import {UploadPictureService} from "../upload-picture.service";
import {GetUploadedImageService} from "../get-uploaded-image.service";

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.scss']
})
export class ProfileSettingsComponent implements OnInit {

  playerDetails : ProfileDetailsGetPayload;
  updateUserProfile: UpdateUserProfilePayload;
  formData : FormData
  imgBlob: Blob
  imgSrc: string;

  constructor(private pdService : PlayerDetailsService , private uploadPhotoService : UploadPictureService,
              private updateProfileService : UpdateUserProfileDetailsService, private getUploadedPhoto : GetUploadedImageService,
              private router:Router, private toast : ToastrService) {

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
      }
    };

    this.updateUserProfile = {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      username: "",
      discordTag: "",
      country: {
        name : ""
      }
    };
    this.formData = new FormData();
    this.imgBlob = new Blob();
    this.imgSrc = '';
   }


  updateUserForm = new FormGroup({
    firstName: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]), //inside the constructor is like a place holder
    lastName: new FormControl(null, [Validators.required, Validators.minLength(3),Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]),
    email: new FormControl(null, [Validators.email, Validators.required]),
    password: new FormControl(null, [Validators.required ]), //Minimum eight characters, at least one letter and one number
    username: new FormControl(null, [Validators.required]), //pattern for discord tag. Mustafa Taha#1234
    discordTag: new FormControl(null,[Validators.required]),
    country: new FormControl(null, [Validators.required])
  })

  ngOnInit(): void {
    this.pdService.getPlayerDetails().subscribe(data =>{
      this.playerDetails = data;
      console.log(this.playerDetails)
      this.updateUserForm.patchValue({
        firstName: this.playerDetails.firstName ,
        lastName: this.playerDetails.lastName ,
        email: this.playerDetails.email ,
        password: '' ,
        username: this.playerDetails.username ,
        discordTag: this.playerDetails.discordTag,
        country: ''
      })
      console.log(this.updateUserForm.value);
      // this.updateUserForm.({
      //   firstname: this.playerDetails.firstName ,
      //   lastName: this.playerDetails.lastName ,
      //   email: this.playerDetails.email ,
      //   password: '' ,
      //   username: this.playerDetails.username ,
      //   discordTag: this.playerDetails.discordTag,
      //   country: ''
      // })
      if (this.playerDetails.profilePicture){
        this.getUploadedPhoto.getUploadedImage(this.playerDetails.profilePicture.url).subscribe(data=>{
          this.imgBlob = data;
          console.log(this.imgBlob);
          let reader = new FileReader();
          reader.readAsDataURL(this.imgBlob);
          reader.onload = (event: any) =>{
            this.imgSrc = event.target.result;

          }
        })
      }

      // this.addHandles();
        console.log(data);
    })


  }



  updateUser(updateForm : FormGroup){
    this.updateUserProfile.firstName = this.updateUserForm.get('firstName')?.value;
    this.updateUserProfile.username = this.updateUserForm.get('username')?.value;
    this.updateUserProfile.country.name = this.updateUserForm.get('country')?.value;
    this.updateUserProfile.email = this.updateUserForm.get('email')?.value;
    this.updateUserProfile.lastName = this.updateUserForm.get('lastName')?.value;
    this.updateUserProfile.discordTag = this.updateUserForm.get('discordTag')?.value;
    this.updateUserProfile.password = this.updateUserForm.get('password')?.value;


    this.updateProfileService.updateProfileDetails(this.updateUserProfile).subscribe(data =>{
      console.log(this.updateUserProfile);
      console.log(data);
      if(this.formData.has('picture'))
        this.uploadProfilePhoto(this.formData);
      this.toast.success("update profile done");
      setInterval(()=> window.location.reload() , 2000)

    })
  }

  onFileSelected(event : any){
    this.formData.append('picture' , event.target.files[0]);
  }

  uploadProfilePhoto(photo : any){
    this.uploadPhotoService.UploadPhoto(photo).subscribe(data =>{
      console.log(data);
    })
  }
}
