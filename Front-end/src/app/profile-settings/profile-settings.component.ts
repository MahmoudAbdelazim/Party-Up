import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PlayerDetailsService } from '../player-details.service';
import { ProfileDetailsGetPayload } from '../profile/profile-details-get.payload';

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.scss']
})
export class ProfileSettingsComponent implements OnInit {

  playerDetails : ProfileDetailsGetPayload;
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
      // this.addHandles();    
        console.log(data);
    })
  

  }

  // addHandles(): any{
  //   let cartoona : any = ``;
  //   for(let i = 0; i<this.playerDetails.handles.length; i++){
  //     cartoona += `
  //     <div class="col-md-6 text-primary"><label class="labels">Game ${i+1}:</label><input type="text" class="form-control"
  //      placeholder="" value="${this.playerDetails.handles[i].game}"></div>
  //      <div class="col-md-6 text-primary"><label class="labels">Handle ${i+1}:</label><input type="text" class="form-control"
  //      placeholder="" value="${this.playerDetails.handles[i].handle}"></div>`
  //   }
  //   document.getElementById("addHandlesId")!.innerHTML = cartoona;
  // }
}
