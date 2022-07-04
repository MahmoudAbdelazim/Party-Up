import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router'
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {RegisterRequestPayload} from "./register-request.payload";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerPayload : RegisterRequestPayload;

  constructor(private router:Router, private authService : AuthService) {
      this.registerPayload = {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        username: "",
        dataOfBirth: "",
        country: ""
      };
  }

  registerForm = new FormGroup({
    firstName: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]), //inside the constructor is like a place holder
    lastName: new FormControl(null, [Validators.required, Validators.minLength(3),Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]),
    email: new FormControl(null, [Validators.email, Validators.required]),
    password: new FormControl(null, [Validators.required ]), //Minimum eight characters, at least one letter and one number
    discordTag: new FormControl(null, [Validators.required]), //pattern for discord tag. Mustafa Taha#1234
    dateOfBirth: new FormControl(null,[Validators.required]),
    country: new FormControl(null, [Validators.required])
  })


  ngOnInit(): void {
  }


  submitRegisterForm(registerForm:FormGroup){
      this.registerPayload.firstName = this.registerForm.get('firstName')?.value;
      this.registerPayload.lastName = this.registerForm.get('lastName')?.value;
      this.registerPayload.email = this.registerForm.get('email')?.value;
      this.registerPayload.password = this.registerForm.get('password')?.value;
      this.registerPayload.username = this.registerForm.get('discordTag')?.value;
      this.registerPayload.dataOfBirth = this.registerForm.get('dateOfBirth')?.value;
      this.registerPayload.country = this.registerForm.get('country')?.value;

      this.authService.register(this.registerPayload).subscribe(data =>{
        console.log("sign up successfully")
        console.log(data.value)
        sessionStorage.setItem('userSignedUp' , this.registerPayload.username);
        this.router.navigate(['/personalityTest', this.registerPayload.username]);
      });

  }
}
