import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {LoginRequestPayload} from "./login-request.payload";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginPayload : LoginRequestPayload;
  constructor(private router:Router, private authService : AuthService) {
    this.loginPayload = {
      usernameOrEmail: "",
      password: ""
    };
  }

  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.email, Validators.required]),
    password: new FormControl(null, [Validators.required ]) //Minimum eight characters, at least one letter and one number
  })


  ngOnInit(): void {
  }

  submitLoginForm(loginForm:FormGroup){
    this.loginPayload.usernameOrEmail = this.loginForm.get('email')?.value;
    this.loginPayload.password = this.loginForm.get('password')?.value;

    this.authService.signIn(this.loginPayload).subscribe(data =>{
        console.log("LOGin successfully")
        sessionStorage.setItem('token', data.sessionId);
        console.log(data)
        this.router.navigate(['/profile']);
    });

  }


}
