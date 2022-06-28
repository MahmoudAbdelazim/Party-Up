import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {PlayersDataService} from "../players-data.service";

@Component({
  selector: 'app-personality-test',
  templateUrl: './personality-test.component.html',
  styleUrls: ['./personality-test.component.scss']
})
export class PersonalityTestComponent implements OnInit {

  playerPersonalityTest = new FormGroup({
    q1: new FormControl(null , [Validators.required]),
    q2: new FormControl(null , [Validators.required]),
    q3: new FormControl(null , [Validators.required]),
    q4: new FormControl(null , [Validators.required]),
    q5: new FormControl(null , [Validators.required]),
    q6: new FormControl(null , [Validators.required]),
    q7: new FormControl(null , [Validators.required]),
    q8: new FormControl(null , [Validators.required]),
    q9: new FormControl(null , [Validators.required]),
    q10: new FormControl(null , [Validators.required]),
    q11: new FormControl(null , [Validators.required]),
    q12: new FormControl(null , [Validators.required]),
    q13: new FormControl(null , [Validators.required]),
    q14: new FormControl(null , [Validators.required]),
    q15: new FormControl(null , [Validators.required]),
    q16: new FormControl(null , [Validators.required]),
    q17: new FormControl(null , [Validators.required]),
    q18: new FormControl(null , [Validators.required]),
    q19: new FormControl(null , [Validators.required]),
    q20: new FormControl(null , [Validators.required]),
    q21: new FormControl(null , [Validators.required]),
    q22: new FormControl(null , [Validators.required]),
    q23: new FormControl(null , [Validators.required]),
    q24: new FormControl(null , [Validators.required]),
    q25: new FormControl(null , [Validators.required]),
    q26: new FormControl(null , [Validators.required]),
    q27: new FormControl(null , [Validators.required])
})

  constructor() { }

  ngOnInit(){
  }

}
