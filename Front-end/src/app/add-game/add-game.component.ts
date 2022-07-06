import { Component, OnInit } from '@angular/core';
import {PlayerDetailsService} from "../player-details.service";
import {Router} from "@angular/router";
import {ProfileDetailsGetPayload} from "../profile/profile-details-get.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AddGamePayload} from "./add-game.payload";
import {AddGameService} from "../add-game.service";

@Component({
  selector: 'app-add-game',
  templateUrl: './add-game.component.html',
  styleUrls: ['./add-game.component.scss']
})
export class AddGameComponent implements OnInit {

  playerDetails : ProfileDetailsGetPayload
  addGamePayload : AddGamePayload
  constructor(private pdService : PlayerDetailsService , private router:Router , private addGameService : AddGameService) {
    this.playerDetails = {
      username : '',
      email : '',
      firstName : '',
      lastName : '',
      phoneNumber : '',
      handles : []
    }

    this.addGamePayload = {
      gameName : '',
      handle : ''
    }
  }

  ngOnInit(): void {
    this.pdService.getPlayerDetails().subscribe(data =>{
      this.playerDetails = data;
      // this.addHandles();
      console.log(data);
    })
  }

  addGameWithHandle = new FormGroup({
    gameName: new FormControl(), //inside the constructor is like a place holder
    handle: new FormControl()
  })

  sendPlayersGameWithHandle(){
    this.addGamePayload.gameName = this.addGameWithHandle.get('gameName')!.value;
    this.addGamePayload.handle = this.addGameWithHandle.get('handle')?.value;
    console.log(this.addGamePayload);
    this.addGameService.addGameWithHandle(this.addGamePayload).subscribe(data => {
      console.log(data)
      this.router.navigate(['/addGame']);
    });
  }


}
