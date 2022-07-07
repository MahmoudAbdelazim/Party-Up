import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../auth.service";
import {GetPeerRequestsAsNotificationService} from "../get-peer-requests-as-notification.service";
import {NotificationsPayload} from "./notifications.payload";
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit{

  bellDown: boolean
  notifications: NotificationsPayload[];

  constructor(private authService : AuthService , private getPeerRequests : GetPeerRequestsAsNotificationService) {

    this.bellDown = false;
    this.notifications = [];
  }

  ngOnInit(): void {
  }

  getRequests(){
    this.getPeerRequests.getPeerRequestsNotifications().subscribe(data =>{
      console.log(data)
      this.notifications = data
    })
  }

  checkIfUserLoggedIn(){
    return this.authService.isUserLoggedIn();
  }

  expandingNotifications(){
    this.bellDown = !this.bellDown;
    if (this.bellDown)
      this.getRequests();
    else
      this.notifications = [];
    console.log(this.bellDown);
  }

  shrinkingNotificationWhenLogout(){
    this.bellDown = false;
  }
}
