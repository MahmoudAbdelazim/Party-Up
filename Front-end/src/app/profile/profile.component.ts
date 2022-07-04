import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {PlayerDetailsService} from "../player-details.service";
import {Router} from "@angular/router";
import {ProfileDetailsGetPayload} from "./profile-details-get.payload";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  PlayerDetails : ProfileDetailsGetPayload

  constructor(private pdService : PlayerDetailsService , private router:Router) {

    this.PlayerDetails = {
      username : '',
      email : '',
      firstName : '',
      lastName : '',
      phoneNumber : '',
      handles : []
    }
  }



  ngOnInit(): void {
    this.pdService.getPlayerDetails().subscribe(data =>{

      this.PlayerDetails = data;
      console.log(this.PlayerDetails);
    })
  }

  showingPlayerDetailsInHtml(){

  }


}
