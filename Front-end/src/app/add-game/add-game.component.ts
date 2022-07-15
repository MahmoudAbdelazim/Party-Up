import { Component, OnInit } from '@angular/core';
import {PlayerDetailsService} from "../player-details.service";
import {Router} from "@angular/router";
import {ProfileDetailsGetPayload} from "../profile/profile-details-get.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AddGamePayload} from "./add-game.payload";
import {AddGameService} from "../add-game.service";
import {GetListOfGamesService} from "../get-list-of-games.service";
import {GetGamesListPayload} from "./get-games-list.payload";

@Component({
  selector: 'app-add-game',
  templateUrl: './add-game.component.html',
  styleUrls: ['./add-game.component.scss']
})
export class AddGameComponent implements OnInit {

  playerDetails : ProfileDetailsGetPayload
  addGamePayload : AddGamePayload
  gamesListDto : GetGamesListPayload[]
  errorMessage : string
  constructor(private pdService : PlayerDetailsService , private router:Router , private addGameService : AddGameService , private listOfGames : GetListOfGamesService) {
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
    }

    this.addGamePayload = {
      gameName: "",
      handle: ""
    }

    this.gamesListDto = []
    this.errorMessage = '';
  }

  ngOnInit(): void {
    this.pdService.getPlayerDetails().subscribe(data =>{
      this.playerDetails = data;
      // this.addHandles();
      console.log(data);
      this.listOfGames.getListOfAvailableGames().subscribe(data =>{
        this.gamesListDto = data
        console.log(this.gamesListDto)
      })
    })
  }

  addGameWithHandle = new FormGroup({
    gameName: new FormControl(null , [Validators.required ]), //inside the constructor is like a place holder
    handle: new FormControl(null , [Validators.required ])
  })

  sendPlayersGameWithHandle(){
    this.addGamePayload.gameName = this.addGameWithHandle.get('gameName')?.value;
    this.addGamePayload.handle = this.addGameWithHandle.get('handle')?.value;
    console.log(this.addGamePayload);
    this.addGameService.addGameWithHandle(this.addGamePayload).subscribe(data => {
      console.log(data)
      if (data == 'Handle is already present for this game'){
        this.errorMessage = data;
      }else {
        this.errorMessage = '';
      }
      this.pdService.getPlayerDetails().subscribe(data =>{
        this.playerDetails = data;
        // this.addHandles();
      })
      this.router.navigate(['/addGame']);
    });
  }


}
