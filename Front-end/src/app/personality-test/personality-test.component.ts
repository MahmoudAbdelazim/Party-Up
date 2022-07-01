import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import { Router } from '@angular/router';


@Component({
  selector: 'app-personality-test',
  templateUrl: './personality-test.component.html',
  styleUrls: ['./personality-test.component.scss']
})
export class PersonalityTestComponent implements OnInit {

/*
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
*/
  step: any = 1;
  forTheSpan: boolean = false;
  submitted: any = false;
  multistep = new FormGroup({
    Page1: new FormGroup({
      q1: new FormControl(null , [Validators.required]),
      q2: new FormControl(null , [Validators.required]),
      q3: new FormControl(null , [Validators.required]),
      q4: new FormControl(null , [Validators.required]),
      q5: new FormControl(null , [Validators.required]),
      q6: new FormControl(null , [Validators.required]),
      q7: new FormControl(null , [Validators.required]),
      q8: new FormControl(null , [Validators.required]),
      q9: new FormControl(null , [Validators.required]),
    }),
    Page2: new FormGroup({
      q10: new FormControl(null , [Validators.required]),
      q11: new FormControl(null , [Validators.required]),
      q12: new FormControl(null , [Validators.required]),
      q13: new FormControl(null , [Validators.required]),
      q14: new FormControl(null , [Validators.required]),
      q15: new FormControl(null , [Validators.required]),
      q16: new FormControl(null , [Validators.required]),
      q17: new FormControl(null , [Validators.required]),
      q18: new FormControl(null , [Validators.required]),
    }),
    Page3: new FormGroup({
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
  })
  constructor(private route: Router) { }

  ngOnInit(): void {
  }

  get Page1() {
    // @ts-ignore
    return this.multistep.controls['Page1']['controls'];
  }

  get Page2() {
    // @ts-ignore
    return this.multistep.controls['Page2']['controls'];
  }

  submit() {
    this.submitted = true;
    if(this.multistep.controls['Page1'].invalid && this.step == 1) {
      this.forTheSpan = true;
      return;
    }
    if(this.multistep.controls['Page2'].invalid && this.step == 2) {
      this.forTheSpan = true;
      return;
    }
    if(this.multistep.controls['Page3'].invalid && this.step == 3) {
      this.forTheSpan = true;
      return;
    }
    this.forTheSpan = false;
    this.step = this.step + 1;
    if(this.step == 4) {
      this.route.navigate(['/login'])
    }
  }

  previous() {
    this.forTheSpan = false;
    this.step = this.step - 1;
  }



}
