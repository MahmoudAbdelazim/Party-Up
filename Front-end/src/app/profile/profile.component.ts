import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  constructor() { }

  registerForm = new FormGroup({
    email: new FormControl(null, [Validators.email, Validators.required]),
    password: new FormControl(null, [Validators.required ]) //Minimum eight characters, at least one letter and one number
  })

  loginForm(registerForm:FormGroup){
    console.log(registerForm.value);

  }

  ngOnInit(): void {
  }

}
