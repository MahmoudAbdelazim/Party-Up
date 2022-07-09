import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable } from 'rxjs';
import { GetOthersProfileService } from './get-others-profile.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewGuardGuard implements CanActivate {
  
  constructor(private getOtherProfileDetailsService: GetOthersProfileService, private actvRoute : ActivatedRoute
              , private router:Router){

    

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    // return this.checkIfReviewedIsTrue();
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
