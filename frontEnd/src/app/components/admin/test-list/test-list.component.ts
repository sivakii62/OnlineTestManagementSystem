import { Component, OnInit } from '@angular/core';
import { Question, Test } from 'src/app/models/user.model';
import { Router } from '@angular/router';
import { CreateExamService } from 'src/app/services/create-exam.service';
import { LoggingService } from '../../../models/loggingService';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.css']
})
export class TestListComponent implements OnInit {
  allQues: Question[];
  testQues:Question[];
  tests:Test[];
  testId:number=3;
  baseUrl: string = "http://localhost:8082/exam";
  constructor(private service:CreateExamService,private router:Router,private logger:LoggingService,) { }

  ngOnInit() {
    //if no user exists in the session storage, navigates to  the login
    if(localStorage.token!=null){
      this.getAllTests()
    }
    else{
      this.router.navigate(['/login']);
    }
  }


  //function to get all tests
  getAllTests(){
    this.service.getAllTests().subscribe(data => {
      this.logger.logStatus("got all the tests!");
      console.log(data)
      this.tests=data;
    },
      err => {
        console.log(err.stack);
      });
  
  }

  //function to update a test
  updateTest(testId:number){
    // alert(testId)
    this.router.navigate(['/admin/updateTest',testId]);
  }

  //function to delete a test
  deleteTest(test:Test){
      if(confirm('All questions in the test will be deleted..!   Are you sure you want to proceed?')){
        this.service.deleteTest(test.test_Id).subscribe(data => {
          this.logger.logStatus("deletes the test!");
      console.log(data);
      this.getAllTests()
      },
      err => {
        alert(err.error.details);
      });
  }
  }

  
}
