import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor() { }

  registerForm = new FormGroup({
    firstName: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]), //inside the constructor is like a place holder
    lastName: new FormControl(null, [Validators.required, Validators.minLength(3),Validators.pattern('^[a-zA-Z]{3,20}$'), Validators.maxLength(20)]),
    email: new FormControl(null, [Validators.email, Validators.required]),
    password: new FormControl(null, [Validators.required ]), //Minimum eight characters, at least one letter and one number
    discordTag: new FormControl(null, [Validators.required, Validators.pattern('^.{3,32}#[0-9]{4}$')]), //pattern for discord tag. Mustafa Taha#1234
    dateOfBirth: new FormControl(null,[Validators.required]),
    country: new FormControl(null, [Validators.required])
  })

  submitRegisterForm(registerForm:FormGroup){
    console.log(registerForm.value);

  }
  ngOnInit(): void {
  }

}
