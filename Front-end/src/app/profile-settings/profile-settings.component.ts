import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PlayerDetailsService } from '../player-details.service';
import { ProfileDetailsGetPayload } from '../profile/profile-details-get.payload';
import {UpdateUserProfilePayload} from "./update-user-profile.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UpdateUserProfileDetailsService} from "../update-user-profile-details.service";

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.scss']
})
export class ProfileSettingsComponent implements OnInit {

  playerDetails : ProfileDetailsGetPayload;
  updateUserProfile: UpdateUserProfilePayload;

  constructor(private pdService : PlayerDetailsService , private updateProfileService : UpdateUserProfileDetailsService, private router:Router) {

    this.playerDetails = {
      username : '',
      email : '',
      firstName : '',
      lastName : '',
      phoneNumber : '',
      handles : []
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

   }

  ngOnInit(): void {
    this.pdService.getPlayerDetails().subscribe(data =>{
      this.playerDetails = data;
      // this.addHandles();
        console.log(data);
    })


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
    })
  }
}
