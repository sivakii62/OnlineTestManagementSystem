import { DOCUMENT } from '@angular/common';
import { Component, HostListener, Inject, Injector, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AttemptExamService } from 'src/app/services/attempt-exam.service';
import {CountdownModule,CountdownComponent} from 'ngx-countdown';
import { Test } from 'src/app/models/user.model';
import { parseJwt } from 'src/app/models/customFunctions';

@Component({
  selector: 'app-test-tab',
  templateUrl: './test-tab.component.html',
  styleUrls: ['./test-tab.component.css']
})

export class TestTabComponent implements OnInit {

  step: number = 1;
  testId: number;
  userId: number;
  SelectOption:FormGroup;
  questionList: any[];
  totalAttempts: number = 0;
  numberOfQuestion:number[] = [];
  answer:number[] = [];
  checked: boolean = false;
  currentQuestion: number  = 0;
  timeLeftSecond: number = 60;
  timeLeftMin: number = 0;
  timeOutAlert: boolean = false;
  answerStatus:number[] = [];
  value: number = 1;
  interval;
  userName: string;
  test?: Test;
  testTitle?:string;
  timer:number = 0;
  timeLeftForAlert:number = 0;

   constructor(@Inject(DOCUMENT) private document: any,private formBuilder:FormBuilder,private injector : Injector,private services:AttemptExamService,) { 
     
     let route=injector.get(ActivatedRoute)
     route.params.subscribe(params=>{
         this.testId = params['testId'];
     })

     this.services.getActiveTests(this.userName, this.testId).subscribe((data)=>{
       this.setTest(data);
       console.log(this.test);
       
       this.timeLeftMin = this.test.testDuration - 1;

       

       if(this.timeLeftMin < 2){
          this.timeOutAlert = true
       }
       for(let i = 0; i < this.test.totalQuestion; i++){
         this.numberOfQuestion[i] = i;
         this.answer[i] = 0;
         this.answerStatus[i] = -1;
       }
     },
     (err)=>{
       console.log(err);
       alert(err.error.details);
       this.step = 1
       window.close()
     })
 
   }  
   elem;

   @HostListener('window:beforeunload', ['$event'])
   onWindowClose(event: any): void {
     
     if(this.step == 3){
      event.preventDefault();
      event.returnValue = false;
     this.submitTestAnswer();
     }
     
  }

  setTest(data) {
    this.test = data;
    this.testTitle = this.test.testTitle;
  }

   openFullscreen() {
    if (this.elem.requestFullscreen) {
      this.elem.requestFullscreen();
    } else if (this.elem.mozRequestFullScreen) {
      /* Firefox */
      this.elem.mozRequestFullScreen();
    } else if (this.elem.webkitRequestFullscreen) {
      /* Chrome, Safari and Opera */
      this.elem.webkitRequestFullscreen();
    } else if (this.elem.msRequestFullscreen) {
      /* IE/Edge */
      this.elem.msRequestFullscreen();
    }
    this.step++;
  }


   ngOnInit() {
  this.elem = document.documentElement;
  this.SelectOption = this.formBuilder.group({
    option: ['', Validators.required]
  })
}


nextStep(){
  
  this.services.getAllQuestion(this.userName, this.testId).subscribe((data)=>{
    this.setQuestionList(data);
    // console.log(data);
    this.handle()
    
  },
  (err)=>{
    console.log("Error...Occurs");
    alert(err.error.details);
    window.close();
  })
}


handle(){
  this.step++
  this.timeLeftMin = this.test.testDuration 
  this.timer = ( this.test.testDuration * 60 ) 
  this.startTimer();
}


setQuestionList(data){

  this.questionList = data.questions
  localStorage.removeItem('token')
  localStorage.setItem('token',data.jwt)
  
}

visitQuestion(num: number){
  if(this.answer[this.currentQuestion] == 0){
    this.SelectOption.controls['option'].reset();
    this.answerStatus[this.currentQuestion] = -1;
  }
  else{
    this.answerStatus[this.currentQuestion] = 1;
    
  }
    this.currentQuestion = num;
   
}

startTimer(){


  this.timeLeftForAlert = this.test.testDuration 

  var secAl : number = this.timeLeftForAlert * 1000 * 60 

  if(this.timeLeftForAlert > 2){
    setTimeout(() => {
    
      this.timeOutAlert = true;
      this.timer=this.timer-60;
      console.log(this.timer);
  
     }, ((secAl)-(2000 * 60)));
  }
  else{
    this.timeOutAlert = true;
  }

  
  setTimeout(() => {
    if(this.step==3)
          this.submitTestAnswersTimeOut();
        else
          window.close
   }, this.timeLeftMin*1000*60);



}

clearOption(){
  if(this.answer[this.currentQuestion] == 0){
    alert("Not attempted ...!");
    this.SelectOption.controls['option'].reset();
    return;
  }
  this.answerStatus[this.currentQuestion] = -1;
  this.answer[this.currentQuestion] = 0;
  this.SelectOption.controls['option'].reset();
  this.totalAttempts--;
}
setChooseOption(){
  if(this.answer[this.currentQuestion] != 0 && this.SelectOption.controls.option.invalid){
    this.totalAttempts--;
  }
  if(this.SelectOption.controls.option.invalid){
    alert("No Option Selected ...!");
    this.answer[this.currentQuestion] = 0;
    this.answerStatus[this.currentQuestion] = -1;
    return;
  }
  if(this.answer[this.currentQuestion] == 0){
    this.totalAttempts++;
  }
  this.answerStatus[this.currentQuestion] = 1;
  
  this.answer[this.currentQuestion] = this.SelectOption.controls.option.value;
  this.SelectOption.controls['option'].reset();
  
}

previousQuestion(){
  if(this.answer[this.currentQuestion] == 0){
    this.SelectOption.controls['option'].reset();
    this.answerStatus[this.currentQuestion] = -1;
  }
  else{
    this.answerStatus[this.currentQuestion] = 1;
    
  }
  this.currentQuestion--;
  
}
nextQuestion(){
  if(this.answer[this.currentQuestion] == 0){
    this.SelectOption.controls['option'].reset();
    this.answerStatus[this.currentQuestion] = -1;
  }
  else{
    this.answerStatus[this.currentQuestion] = 1;
  }
  this.currentQuestion++;
  console.log(this.answer);
}
submitTestAnswer(){
  if(this.step != 3){
    return;
  }
  console.log(this.step)
  var getAnswer = {
    answer:this.answer, 
    userName:parseJwt(localStorage.getItem('token')), 
    testId:this.testId 
 }; 
  console.log(getAnswer.answer)
  this.services.setTestAnswer(getAnswer).subscribe((data)=>{
    console.log(data);
 },
 (err)=>{
   console.log(err);
   alert(err.error.message);
 })
 this.step=4

//  localStorage.removeItem('token')
//  localStorage.setItem('token',localStorage.getItem('temp'))
//  localStorage.removeItem('temp')

}
submitTestAnswersTimeOut(){
  if(this.step == 3){
    console.log(this.step)
    console.log(this.answer)
     var getAnswer = {
    answer:this.answer, 
    userName:parseJwt(localStorage.getItem('token')), 
    testId:this.testId
 }
    console.log(this.step)
    this.services.setTestAnswer(getAnswer).subscribe((data)=>{
      console.log(data)
 },
 (err)=>{
   console.log(err);
   alert(err.error.message);
 })
 alert("Time Out!!! \nTest Is Submitted...");
 this.step=4

//  localStorage.removeItem('token')
//  localStorage.setItem('token',localStorage.getItem('temp'))
//  localStorage.removeItem('temp')

}
}

Review(){
  console.log(this.answer);
  confirm("Are you sure do you need to submit the answer ??") ? this.submitTestAnswer() : "";
}

}
