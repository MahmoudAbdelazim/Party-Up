import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable } from 'rxjs';
import { GetOthersProfileService } from './get-others-profile.service';
import {GetOthersDetailsPayload} from "./others-profile/get-others-details.payload";

@Injectable({
  providedIn: 'root'
})
export class ReviewGuardGuard implements CanActivate {

  otherPlayerData : GetOthersDetailsPayload

  constructor(private getOtherProfileDetailsService: GetOthersProfileService, private actvRoute : ActivatedRoute, private router:Router){
    this.otherPlayerData = {
      username : '',
      handles : [],
      country: {
        name : ''
      },
      requested : false,
      otherRequested : false,
      peer : false,
      discordTag : '',
      profilePicture : {
        id : '',
        type : '',
        size : 0,
        url : ''
      },
      reviewed : false
    };
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean{
    let entered = sessionStorage.getItem('enteredOtherProfile');
    if (entered){
      this.getOtherProfileDetailsService.getOthersDetails(entered).subscribe(data=>{
        this.otherPlayerData = data
        if (this.otherPlayerData.reviewed){
          let url = 'profile/' + entered;
          this.router.navigate([url]);
          return false
        }else {
          return true
        }
      })
    }else {
      this.router.navigate(['/profile']);
      return false;
    }
    console.log(entered);
    return true;
  }


  // checkIfReviewedIsTrue(){
  //   let userName = this.actvRoute.snapshot;
  //   console.log("U dont have accesss for the review page again!");
  //   console.log(userName);

  //    return this.getOtherProfileDetailsService.getOthersDetails(userName).
  //    pipe(map(data=>{
  //     if(data.reviewed === true){
  //       this.router.navigate(['profile']);
  //       return true;
  //     }
  //    }))


  // }
}
