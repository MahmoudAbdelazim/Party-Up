import {Injectable} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class PlayersDataService {
  playersDetails: any[]
  missedPlayer:any
  navBarFlag:boolean[]

  constructor(private router:Router) {
    this.playersDetails = [];
    this.navBarFlag = [false , false];
    this.missedPlayer = undefined;

  }

  setPlayersDetails(playerObject:FormGroup){
    this.playersDetails.push(playerObject);
  }
  setPlayersTest(playerPersonalityTest:FormGroup){
    if (this.missedPlayer !== undefined){
      this.playersDetails.forEach(player => {
        if (player.email === this.missedPlayer.email){
          this.playersDetails[this.playersDetails.indexOf(player)].TestDetails = playerPersonalityTest;
        }
      })
    }else {
      this.playersDetails[this.playersDetails.length - 1].TestDetails = playerPersonalityTest;
    }
    this.missedPlayer = undefined;
  }
  pickingSpecificPlayerForTest(playerCredentialsLogIn:FormGroup){
    this.missedPlayer = playerCredentialsLogIn;
    this.router.navigate(['/personalityTest']);
  }
  getPlayersDetails(){
    return this.playersDetails;
  }
  checkingExistingAccount(playerObject:FormGroup):boolean{
    let flag = true;
    this.playersDetails.forEach(player => {
      if (player.email === playerObject.value.email){
        flag = false;
      }
    })
    return flag;
  }
  checkingCredentials(loginCredentials:FormGroup):boolean[]{
    console.log(this.navBarFlag);
    this.playersDetails.forEach(player => {

      if (player.email === loginCredentials.value.email && player.password === loginCredentials.value.password) {
        this.navBarFlag[0] = true;
        if (player.hasOwnProperty('TestDetails')){
          this.navBarFlag[1] = true;
        }
      }


    })
    console.log(this.navBarFlag);
    return this.navBarFlag;
  }


}
