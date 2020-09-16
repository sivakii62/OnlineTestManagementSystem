import { Component, OnInit } from '@angular/core';
import { Question, Test } from 'src/app/models/user.model';
import { Router } from '@angular/router';
import { CreateExamService } from 'src/app/services/create-exam.service';
import { LoggingService } from '../../../models/loggingService';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {
  allQues: Question[];
  testQues:Question[];
  test:Test;
  baseUrl: string = "http://localhost:8082/exam";
  constructor(private service:CreateExamService,private logger:LoggingService,private router:Router) { }

  ngOnInit() {

    //if no user exists in the session storage, navigates to all the questions
    if(localStorage.token!=null){
    this.getAllQuestions()
  }
  else{
    this.router.navigate(['/login']);
  }
}

//get all questions function 
  getAllQuestions(){
    this.service.getAllQuestions().subscribe(data => {
      this.logger.logStatus("got all the questions!");
      console.log(data)
      this.allQues=data;
    },
      err => {
        console.log(err.stack);
      });
  
  }

  //delete a particular question function
  deleteQuestion(questionId:number){
    console.log(questionId)
    this.service.deleteQuestion(questionId).subscribe(data => {
      this.logger.logStatus("Question is deleted successfully..!");
      console.log(data);
      this.getAllQuestions()
      alert("Question is deleted successfully..!");
      },
      err => {
        alert(err.error.details);
      });

  }

  //updating a particular question
  updateQuestion(questionId:number){
    this.router.navigate(['/admin/updateQuestion',questionId])
  }
}
