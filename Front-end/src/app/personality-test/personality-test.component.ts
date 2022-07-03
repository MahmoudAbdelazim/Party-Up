import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from '@angular/router';
import {PersonalityTestRequestPayload} from "./personality-test-request.payload";
import {PersonalityTestService} from "../personality-test.service";


@Component({
  selector: 'app-personality-test',
  templateUrl: './personality-test.component.html',
  styleUrls: ['./personality-test.component.scss']
})
export class PersonalityTestComponent implements OnInit {

  ptAnswersPayload : PersonalityTestRequestPayload[];
  userName: string
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
  constructor(private ptAnswersService : PersonalityTestService, private router:Router , private actvRoute : ActivatedRoute) {
    this.ptAnswersPayload =[];
    this.userName = ''
  }

  ngOnInit(): void {
    this.userName = this.actvRoute.snapshot.params['username'];
  }

  get Page1() {
    // @ts-ignore
    return this.multistep.controls['Page1']['controls'];
  }

  get Page2() {
        // @ts-ignore
        return this.multistep.controls['Page2']['controls'];
      }
  get Page3() {
    // @ts-ignore
    return this.multistep.controls['Page3']['controls'];
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
      this.submitPersonalityTestAnswers();
    }
  }

  previous() {
    this.forTheSpan = false;
    this.step = this.step - 1;
  }


  submitPersonalityTestAnswers(){

    this.ptAnswersPayload.push({id : 1 , answer : this.Page1.q1.value});
    this.ptAnswersPayload.push({id : 2 , answer : this.Page1.q2.value});
    this.ptAnswersPayload.push({id : 3 , answer : this.Page1.q3.value});
    this.ptAnswersPayload.push({id : 4 , answer : this.Page1.q4.value});
    this.ptAnswersPayload.push({id : 5 , answer : this.Page1.q5.value});
    this.ptAnswersPayload.push({id : 6 , answer : this.Page1.q6.value});
    this.ptAnswersPayload.push({id : 7 , answer : this.Page1.q7.value});
    this.ptAnswersPayload.push({id : 8 , answer : this.Page1.q8.value});
    this.ptAnswersPayload.push({id : 9 , answer : this.Page1.q9.value});
    this.ptAnswersPayload.push({id : 10 , answer : this.Page2.q10.value});
    this.ptAnswersPayload.push({id : 11 , answer : this.Page2.q11.value});
    this.ptAnswersPayload.push({id : 12 , answer : this.Page2.q12.value});
    this.ptAnswersPayload.push({id : 13 , answer : this.Page2.q13.value});
    this.ptAnswersPayload.push({id : 14 , answer : this.Page2.q14.value});
    this.ptAnswersPayload.push({id : 15 , answer : this.Page2.q15.value});
    this.ptAnswersPayload.push({id : 16 , answer : this.Page2.q16.value});
    this.ptAnswersPayload.push({id : 17 , answer : this.Page2.q17.value});
    this.ptAnswersPayload.push({id : 18 , answer : this.Page2.q18.value});
    this.ptAnswersPayload.push({id : 19 , answer : this.Page3.q19.value});
    this.ptAnswersPayload.push({id : 20 , answer : this.Page3.q20.value});
    this.ptAnswersPayload.push({id : 21 , answer : this.Page3.q21.value});
    this.ptAnswersPayload.push({id : 22 , answer : this.Page3.q22.value});
    this.ptAnswersPayload.push({id : 23 , answer : this.Page3.q23.value});
    this.ptAnswersPayload.push({id : 24 , answer : this.Page3.q24.value});
    this.ptAnswersPayload.push({id : 25 , answer : this.Page3.q25.value});
    this.ptAnswersPayload.push({id : 26 , answer : this.Page3.q26.value});
    this.ptAnswersPayload.push({id : 27 , answer : this.Page3.q27.value});


    console.log(this.ptAnswersPayload)


    console.log(this.userName);
    this.ptAnswersService.sendPersonalityTestAnswers(this.ptAnswersPayload , this.userName ).subscribe(data =>{
      console.log("Answers submitted successfully")
      this.router.navigate(['/login']);
    });

  }

 
}

let myHttp = new XMLHttpRequest();
let allQuestions;
myHttp.open("GET", "http://localhost:8080/api/personalityTest")
myHttp.send();

myHttp.addEventListener("readystatechange", function(){
  if(myHttp.readyState == 4 && myHttp.status == 200){
    allQuestions = JSON.parse(myHttp.response);
    console.log(myHttp.readyState);
    console.log(myHttp.status);

    console.log(allQuestions);
  }
})