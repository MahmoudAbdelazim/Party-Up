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

  playerDetails : ProfileDetailsGetPayload

  constructor(private pdService : PlayerDetailsService , private router:Router) {

    this.playerDetails = {
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
      this.playerDetails = data;
      
      console.log(data);
    })
  }
  


}
