import { Component, OnInit } from '@angular/core';
import {GetReviewPeerQuestionsService} from "../get-review-peer-questions.service";
import {ReviewQuestionsPayload} from "./review-questions.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SendReviewAnswersPayload} from "./send-review-answers.payload";
import {GetOthersProfileService} from "../get-others-profile.service";
import {SendReviewPeerAnswersService} from "../send-review-peer-answers.service";
import {ActivatedRoute, Router} from "@angular/router";
import {GetOthersDetailsPayload} from "../others-profile/get-others-details.payload";
import { from } from 'rxjs';
@Component({
  selector: 'app-review-peer',
  templateUrl: './review-peer.component.html',
  styleUrls: ['./review-peer.component.scss']
})
export class ReviewPeerComponent implements OnInit {

  listOfReviewQuestions : ReviewQuestionsPayload[]
  listOfReviewAnswers : SendReviewAnswersPayload[]
  otherPlayerDetails : GetOthersDetailsPayload
  username: string
  index : number
  constructor(private reviewQuestions : GetReviewPeerQuestionsService,
              private otherProfileDetails : GetOthersProfileService,
              private sendReviewAnswers : SendReviewPeerAnswersService,
              private actvRoute : ActivatedRoute,
              private router:Router) {

    this.listOfReviewQuestions = [];
    this.listOfReviewAnswers = [];
    this.otherPlayerDetails = {
      username : '',
      handles : [],
      country: {
        name : ''
      },
      requested : false,
      otherRequested : false,
      peer : false,
      discordTag : '',
      profilePicture : {
        id : '',
        type : '',
        size : 0,
        url : ''
      },
      reviewed : false
    };
    this.username = '';
    this.index = 0;
  }

  ngOnInit(): void {
    this.reviewQuestions.getReviewPeersQuestions().subscribe(data =>{
      this.listOfReviewQuestions = data;

      for (let i = 0; i < this.listOfReviewQuestions.length ; i++) {
        this.listOfReviewQuestions[i].index = ++this.index;
      }
      console.log(this.listOfReviewQuestions)
    })
  }

  reviewAnswersForm = new FormGroup({
    q1: new FormControl(null, [Validators.required]),
    q2: new FormControl( null,[Validators.required]),
    q3: new FormControl( null,[Validators.required]),
    q4: new FormControl( null,[Validators.required]),
    q5: new FormControl( null,[Validators.required]),
    q6: new FormControl( null,[Validators.required]),
    q7: new FormControl( null,[Validators.required]),
    q8: new FormControl( null,[Validators.required]),
    q9: new FormControl( null,[Validators.required]),
    q10: new FormControl( null,[Validators.required])
  })

  submitReviewAnswers(){
    console.log(this.listOfReviewQuestions)
    console.log(this.listOfReviewAnswers)
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[0].id, answer: this.reviewAnswersForm.get('q1')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[1].id, answer: this.reviewAnswersForm.get('q2')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[2].id, answer: this.reviewAnswersForm.get('q3')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[3].id, answer: this.reviewAnswersForm.get('q4')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[4].id, answer: this.reviewAnswersForm.get('q5')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[5].id, answer: this.reviewAnswersForm.get('q6')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[6].id, answer: this.reviewAnswersForm.get('q7')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[7].id, answer: this.reviewAnswersForm.get('q8')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[8].id, answer: this.reviewAnswersForm.get('q9')?.value})
    this.listOfReviewAnswers.push({id :this.listOfReviewQuestions[9].id, answer: this.reviewAnswersForm.get('q10')?.value})
    console.log(this.listOfReviewAnswers)
    this.username = this.actvRoute.snapshot.params['username'];
    console.log(this.listOfReviewAnswers);
    this.sendReviewAnswers.sendReviewAnswers(this.username,this.listOfReviewAnswers).subscribe(data =>{
      console.log(data);
      this.router.navigate(['/profile']);
    })

  }


}
