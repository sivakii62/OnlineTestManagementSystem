import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Question } from 'src/app/models/user.model';
import { CreateExamService } from 'src/app/services/create-exam.service';
import { LoggingService } from '../../../models/loggingService';

@Component({
  selector: 'app-add-test',
  templateUrl: './add-test.component.html',
  styleUrls: ['./add-test.component.css']
})
export class AddTestComponent implements OnInit {
  addTestForm: FormGroup;
  submitted: boolean = false;
  baseUrl: string = "http://localhost:8082/exam";
  options:String[];
  question:Question;
  categoryId:number;
  categoryName:String;
  testId: number=2;
  message:string;
  option:boolean=false;
  startDateError=false;
  endDateError=false;
  titleError: boolean=false;
  // Injecting all relevant services inside the constructor
  constructor(private formBuilder: FormBuilder,private router: Router,private logger:LoggingService,private service:CreateExamService) {
  }

  ngOnInit() {
    if(localStorage.token!=null){
      //adding validations for adding a new test form
      this.addTestForm = this.formBuilder.group({
        testTitle: [ '', Validators.required],
        testDuration: ['',[Validators.required,Validators.min(0)]],
        startDate: ['',Validators.required],
        endDate: ['',Validators.required],
      });
    }
    else{
      this.router.navigate(['/login']);
    }
  }
  
  // updateUser() function
  addTest() {

    this.submitted = true;
    this.startDateError=false
    this.endDateError=false;
    this.titleError=false;
    //if the form is invalid return
    if (this.addTestForm.invalid ) {
      return;
    }

    console.log(this.testId)
    console.log(this.addTestForm.getRawValue())

    //calling a service function to add a new test
    this.service.addTest(this.addTestForm.getRawValue()).subscribe(data => {
      alert("Test is added Successfully...!")
      this.logger.logStatus("Test is added Successfully...!");
      this.router.navigate(['/admin/testList']);
      console.log(data)
    },

      //error block for adding a new test
      err => {
        console.log(err.error)
        //start date must be greater then current date
        if(err.error.details=="Start date must be greater then current date..."){
          this.message=err.error.details;
          this.startDateError=true;
        }
        //End date should be greater than start date
        if(err.error.details=="End date should be greater than start date"){
          this.message=err.error.details;
          this.endDateError=true;
        }
        //Title already exists
        if(err.error.details=="Title already exists"){
          this.message=err.error.details;
          this.titleError=true;
        }
        
      });

  }
}