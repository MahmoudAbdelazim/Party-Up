import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GetOthersProfileService} from "../get-others-profile.service";
import {GetOthersDetailsPayload} from "./get-others-details.payload";
import {PlayerDetailsService} from "../player-details.service";
import {SendPeerRequestService} from "../send-peer-request.service";
import {ToastrService} from "../toastr.service";
import {GetPeerRequestsAsNotificationService} from "../get-peer-requests-as-notification.service";
import {NotificationsPayload} from "../navbar/notifications.payload";
import {forEach} from "lodash";
import {AcceptingOrRejectingTheRequestService} from "../accepting-or-rejecting-the-request.service";
import {AcceptDeclinePayload} from "./accept-decline.payload";

@Component({
  selector: 'app-others-profile',
  templateUrl: './others-profile.component.html',
  styleUrls: ['./others-profile.component.scss']
})
export class OthersProfileComponent implements OnInit {

  otherPlayerDetails : GetOthersDetailsPayload
  userName: string
  url : string
  showAddPeerButton : boolean

  notifications : NotificationsPayload[];
  isOtherUserAlreadySentMe : boolean;
  accOrDecPayload : AcceptDeclinePayload

  isAcc : boolean
  isDeclined: boolean

  constructor(private pdService : PlayerDetailsService, private getOtherProfile : GetOthersProfileService,
              private sendPeerRequestService : SendPeerRequestService, private popupNotification: ToastrService,
              private getPeerRequests : GetPeerRequestsAsNotificationService,
              private acceptOrDeclineService : AcceptingOrRejectingTheRequestService,
              private router:Router , private actvRoute : ActivatedRoute) {

    this.otherPlayerDetails = {
      username : '',
      handles : [],
      country: {
        name : ''
      },
      peer : false
    };
    this.userName = '';
    this.url = '';
    this.showAddPeerButton = true;
    this.notifications = [];
    this.isOtherUserAlreadySentMe = false;
    this.accOrDecPayload = {
      response: ''
    };

    this.isAcc = false;
    this.isDeclined = false;
  }

  ngOnInit(): void {
    this.userName = this.actvRoute.snapshot.params['username'];
    this.pdService.getPlayerDetails().subscribe(data =>{
      console.log(data);
      if (data.username === this.userName){
        this.router.navigate(['/profile'])
      } else {
        this.checkingIfOtherUserSendMeRequest();
        this.getothersDetails();
      }
    })


  }

  getothersDetails(){
    this.getOtherProfile.getOthersDetails(this.userName).subscribe(data =>{
      this.otherPlayerDetails = data;
      console.log(this.otherPlayerDetails);
    })
  }

  sendPeerRequest(){
      this.sendPeerRequestService.sendPeerRequest(this.userName).subscribe(data =>{
        this.showAddPeerButton = false;
        this.popupNotification.success();
        console.log(data);
      })
  }

  checkingIfOtherUserSendMeRequest(){
    this.getPeerRequests.getPeerRequestsNotifications().subscribe(data =>{
      this.notifications = data;
      console.log(this.notifications)

      for (let notification of this.notifications) {
        console.log(this.isOtherUserAlreadySentMe)
        if (this.userName === notification.username) {
          this.isOtherUserAlreadySentMe = true;
          console.log(this.isOtherUserAlreadySentMe);
          break;
        }
      }
    })
  }

  acceptingTheRequest(){
    this.accOrDecPayload.response = 'accept';
    this.acceptOrDeclineService.accept(this.accOrDecPayload , this.userName).subscribe(data =>{
      console.log(this.accOrDecPayload);
      console.log(data);
      this.isAcc = true;
    })
  }

  rejectingTheRequest(){
    this.accOrDecPayload.response = 'reject';
    this.acceptOrDeclineService.reject(this.accOrDecPayload , this.userName).subscribe(data =>{
      console.log(this.accOrDecPayload);
      console.log(data);
      this.isDeclined = true;
    })
  }

}
