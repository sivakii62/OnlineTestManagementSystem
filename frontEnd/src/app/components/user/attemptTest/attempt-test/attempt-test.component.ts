import { Component, Injector, OnInit } from '@angular/core';
import { AttemptExamService } from 'src/app/services/attempt-exam.service';
import { Question, Test } from 'src/app/models/user.model';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { Session } from 'protractor';

@Component({
  selector: 'app-attempt-test',
  templateUrl: './attempt-test.component.html',
  styleUrls: ['./attempt-test.component.css']
})
export class AttemptTestComponent implements OnInit {

  testDetails?: Test[];
  userId: number;
  datepipe : any;
  searchText1:any;
  userName:string

  constructor(private router: Router,private injector : Injector,private attemptTestService : AttemptExamService) { 

    this.userName=localStorage.getItem('token')

    this.attemptTestService.getTestsAssignedToUser(this.userName).subscribe((data)=>{
      this.handle(data);
  },
    (err)=>{
      if(localStorage.getItem('token'))
        alert("User Is Not Registered in any Test.!");
    })
  }

  ngOnInit() {
  }

  handle(data){
    this.testDetails=data
  }


  checkStatus(num: number){
    if(num == 0){
      return "Currently Active !";
    }
    if(num == -1){
      return "Test Passed! Go and Check Result";
    }
    if(num == 1){
      return "Upcoming Test!";
    }
  }
  getDetails(test: Test){
    this.datepipe = this.injector.get(DatePipe)
    let startdate = this.datepipe.transform(test.startDate, 'dd-MM-yyyy HH:mm:ss');
    let enddate = this.datepipe.transform(test.endDate, 'dd-MM-yyyy HH:mm:ss');
    let msg = (test.testTitle, "Test Start Date: " + startdate + "\nTest End Date: " + enddate
     + "\nTotal Question: "+ test.totalQuestion + "\nTotal Marks: "+ test.testTotalMarks + "\nTest Duration(min): "
     +  test.testDuration + "\nTest Status: " + this.checkStatus(test.testStatus));
    alert(msg)
  }

  AttemptTest(test: any){
    console.log(test);
    let link = "Test/" + this.userName + "/" + test.test_Id;
    this.router.navigate([]).then(result=>{window.open(link, '_blank');});
  }
}
