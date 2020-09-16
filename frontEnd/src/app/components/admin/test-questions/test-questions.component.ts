import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Question, Test } from 'src/app/models/user.model';
import { ActivatedRoute, Router } from '@angular/router';
import { CreateExamService } from 'src/app/services/create-exam.service';
import { LoggingService } from '../../../models/loggingService';

@Component({
  selector: 'app-test-questions',
  templateUrl: './test-questions.component.html',
  styleUrls: ['./test-questions.component.css']
})
export class TestQuestionsComponent implements OnInit {

  allQues: Question[];
  testQues:Question[];
  test:Test;
  testId:number;
  baseUrl: string = "http://localhost:8082/exam";
  constructor(private service:CreateExamService,private route: ActivatedRoute,private logger:LoggingService,private router:Router) { 

  }

  ngOnInit() {
    //if no user exists in the session storage, navigate to login
    if(localStorage.token!=null){

      //using activated router to get the test id to get the particular questions
      this.route.params.subscribe(params => {      
      this.testId = params['testId'];
    })
    this.getAllQuestions()
    this.getTestQuestions(this.testId);
    this.getTest(this.testId);
  }
  else{
    this.router.navigate(['/login']);
  }
}

//get all the questions
  getAllQuestions(){
    //valling service to get the remaining questions after adding to the new test
    this.service.getRemainingQuestions().subscribe(data => {
      this.logger.logStatus("got all the remaining questions!");
      this.allQues=data;
    },
      err => {
        console.log(err.stack);
      });
  
  }

  //get all the test questions
  getTestQuestions(testId:number){

    //calling service to get all questions by test id
    this.service.getTestQuestions(testId).subscribe(data => {
      this.logger.logStatus("got all the  test questions by test id!");
      this.testQues=data;
      console.log(data)
    },
      err => {
        console.log(err.stack);
      });

  }

  //get the test by test id
  getTest(testId:number){

    //calling service to get test by test id
    this.service.getTest(testId).subscribe(data => {
      this.logger.logStatus("got all the test details!");
      this.test=data;
      console.log(data)
    },
      err => {
        console.log(err.stack);
      });

  }

  //delete question from the test by question id
  deleteQuestionFromTest(questionId:number){
    console.log(questionId)
    console.log(this.testId)

    //calling service to delete question from a test
    this.service.deleteQuestionFromTest(questionId,this.testId).subscribe(data => {
      this.logger.logStatus("deleted question from the test");
      console.log(data);
      this.getAllQuestions()
      this.getTestQuestions(this.testId);
      this.getTest(this.testId);
    },
      err => {
        alert(err.error.details);
      });

  }

  // add a new question to test
  addQuestionToTest(questionId:number){

    //calling service to add question to a test
    this.service.addQuestionToTest(questionId,this.testId).subscribe(data => {
      this.logger.logStatus("added a question to the test");
      console.log(data);
      this.getAllQuestions()
      this.getTestQuestions(this.testId);
      this.getTest(this.testId);
    },
      err => {
        alert(err.error.details);
      });

  }
  

}
