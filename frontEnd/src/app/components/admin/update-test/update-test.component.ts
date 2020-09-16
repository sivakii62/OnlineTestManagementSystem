import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Question, Test } from 'src/app/models/user.model';
import { CreateExamService } from 'src/app/services/create-exam.service';
import { LoggingService } from '../../../models/loggingService';

@Component({
  selector: 'app-update-test',
  templateUrl: './update-test.component.html',
  styleUrls: ['./update-test.component.css']
})
export class UpdateTestComponent implements OnInit {

  editTestForm: FormGroup;
  submitted: boolean = false;
  baseUrl: string = "http://localhost:8082/exam";
  options:String[];
  question:Question ;
  categoryId:number;
  categoryName:String;
  testId: number;
  message:string;
  option:boolean=false;
  startDateError=false;
  endDateError=false;

  // Injecting all relevant services inside the constructor
  constructor(private formBuilder: FormBuilder,private router: Router,private logger:LoggingService,private service:CreateExamService,private route: ActivatedRoute) {

    //using activated router to get the test id to update the test
    this.route.params.subscribe(params => {      
      this.testId = params['testId'];
    })
  }

  ngOnInit() {
     //if no user exists in the session storage, navigate to login
    if(localStorage.token!=null){

      // form validations for editing test
        this.editTestForm = this.formBuilder.group({
        hibernateLazyInitializer:[],
        test_Id: [{value:'',disabled:true},Validators.required],
        testTitle: [ '', Validators.required],
        testDuration: ['',Validators.required],
        testTotalMarks: [{value:'',disabled:true},Validators.required],
        totalQuestion: [{value:'',disabled:true},Validators.required],
        startDate: ['',Validators.required],
        endDate: ['',Validators.required],
        testStatus: ['',Validators.required],
      });
      this.getTestById(this.testId);  
    }
    else{
      this.router.navigate(['/login']);
    }
  }
  
  //function to get all the test deatils using test id
  getTestById(testId:number){
    console.log(testId)
    this.service.getTest(testId).subscribe(data => {
      this.logger.logStatus("got all the test by test id!");
      console.log(data)
      this.editTestForm.setValue(data);
    },
      err => {
        console.log(2)
        console.log(err.stack);
      });
  }
  // update test function
  updateTest() {

    this.submitted = true;
    this.startDateError=false
    this.endDateError=false;
    //return if form validations did not pass
    if (this.editTestForm.invalid ) {
      return;
    }
    console.log(this.testId)
    console.log(this.editTestForm.getRawValue())

    // calling service to update the test
    this.service.updateTest(this.testId,this.editTestForm.getRawValue()).subscribe(data => {
      this.logger.logStatus("updated the test!");
      alert("Updated Successfully")
      console.log(data)
      this.router.navigate(['/admin/testList']);
    },

    // error block for handling error messages generated from spring 
      err => {
        if(err.error.details=="Start date must be greater then current date..."){
          this.message=err.error.details;
          this.startDateError=true;
        }
        if(err.error.details=="End date should be greater than start date"){
          this.message=err.error.details;
          this.endDateError=true;
        }
      });

  }

  //navigate to test questions
  updateQuestions(){
    this.router.navigate(['/admin/testQuestions',this.testId]);
  }

}