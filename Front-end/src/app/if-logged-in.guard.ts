import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { ToastrService } from './toastr.service';

@Injectable({
  providedIn: 'root'
})
export class IfLoggedInGuard implements CanActivate {
  
  constructor(private router: Router, private authService: AuthService, private toastr : ToastrService){}
  

  canActivate(){
    
    if(this.authService.isUserLoggedIn()){
      console.log("You dont have the right to access any of the login/home/register page while u r logged in!")
      this.router.navigate(['profile']);
      return true;
      
    }
    else(!this.authService.isUserLoggedIn());
    {
      return true;
    }
    return false;
    
  }
}
  

