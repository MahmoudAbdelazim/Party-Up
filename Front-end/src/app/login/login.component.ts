import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {Router} from "@angular/router";
import {PlayersDataService} from "../players-data.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private router : Router , private logIn: PlayersDataService) { }

  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.email, Validators.required]),
    password: new FormControl(null, [Validators.required ]) //Minimum eight characters, at least one letter and one number
  })

  loginFormMethod(loginForm:FormGroup){
    console.log(this.loginForm.value);
    let flag : boolean[] = this.logIn.checkingCredentials(loginForm);
    if (flag[0] && flag[1]) {
      this.router.navigate(['/findpeers']);
    }else if (flag[0] && !flag[1]) {
      flag[0] = false;
      this.logIn.pickingSpecificPlayerForTest(loginForm.value);

    }

  }


  ngOnInit(): void {
  }

}
