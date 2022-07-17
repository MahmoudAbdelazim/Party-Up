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
  errorMessage :string;

  constructor(private router:Router, private authService : AuthService) {
      this.registerPayload = {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        username: "",
        discordTag: "",
        country: {
          name : ""
        }
      };
      this.errorMessage = '';
  }

  registerForm = new FormGroup({
    firstName: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]),
    lastName: new FormControl(null, [Validators.required, Validators.minLength(3),Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]),
    email: new FormControl(null, [Validators.email, Validators.required]),
    password: new FormControl(null, [Validators.required ]),
    username: new FormControl(null, [Validators.required]),
    discordTag: new FormControl(null,[Validators.required , Validators.pattern('^.{3,32}#[0-9]{4}$')]),
    country: new FormControl(null, [Validators.required])
  })


  ngOnInit(): void {
  }


  submitRegisterForm(registerForm:FormGroup){
      this.registerPayload.firstName = this.registerForm.get('firstName')?.value;
      this.registerPayload.lastName = this.registerForm.get('lastName')?.value;
      this.registerPayload.email = this.registerForm.get('email')?.value;
      this.registerPayload.password = this.registerForm.get('password')?.value;
      this.registerPayload.username = this.registerForm.get('username')?.value;
      this.registerPayload.discordTag = this.registerForm.get('discordTag')?.value;
      this.registerPayload.country.name = this.registerForm.get('country')?.value;

      this.authService.register(this.registerPayload).subscribe(data =>{
        console.log("sign up successfully")
        console.log(data.value)
        localStorage.setItem('userSignedUp' , this.registerPayload.username);
        this.router.navigate(['/personalityTest', this.registerPayload.username]);
      } , error => {
        this.errorMessage = error.error;
      });

  }
}
