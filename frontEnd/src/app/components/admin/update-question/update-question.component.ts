import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Question } from 'src/app/models/user.model';
import { CreateExamService } from 'src/app/services/create-exam.service';
import { LoggingService } from '../../../models/loggingService';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.css']
})
export class UpdateQuestionComponent implements OnInit {
  editQueForm: FormGroup;
  submitted: boolean = false;
  baseUrl: string = "http://localhost:8082/exam";
  options:String[];
  question:Question ;
  categoryId:number;
  categoryName:String;
  questionId: number;
  option:boolean=false;
  // Injecting all relevant services inside the constructor
  constructor(private formBuilder: FormBuilder,private router: Router,private service:CreateExamService,private logger:LoggingService,private route: ActivatedRoute) {
    this.route.params.subscribe(params => {      
      this.questionId = params['questionId'];
    })
  }

  ngOnInit() {

     
    if(localStorage.token!=null){

      //form validations for editing question
        this.editQueForm = this.formBuilder.group({
        hibernateLazyInitializer:[],


        questionId: [ {value:'',disabled:true}, Validators.required],
        questionTitle: ['',Validators.required],
        questionAnswer: ['',[Validators.required,Validators.min(1),Validators.max(4)]],
        questionMarks: ['',[Validators.required,Validators.min(1)]],
        questionCategory:[ '',Validators.required],
        questionOptions: ['',Validators.required],
        testQuestions: [''],
      });
      this.getQuestionById(this.questionId);  
    }
    //if no user exists in the session storage, navigate to login
    else{
      this.router.navigate(['/login']);
    }
  }
  
  //get a question by id
  getQuestionById(questionId:number){
    this.service.getQuestion(questionId).subscribe(data => {
      console.log(data)
      this.logger.logStatus("got  the question by question id!");
      this.editQueForm.setValue(data);
      this.categoryName=data.questionCategory.name
      this.options=data.questionOptions
      console.log(this.options.length)
    },
      err => {
        console.log(err.stack);
      });
  }
  // update question function
  updateQuestion() {
    console.log(1)

    this.submitted = true;
    this.option=false;
    // return if the form validations did not pass
    if (this.editQueForm.invalid ) {
      this.logger.logStatus("edited the question succesfully!");
      console.log(this.editQueForm.value)
      console.log(2)
      return;
    }


    this.question=this.editQueForm.getRawValue();
    this.question.questionOptions=this.options;
    this.categoryId=this.question.questionCategory.categoryId
    console.log(this.question)

    //calling service for update question
    this.service.updateQuestion(this.question).subscribe(data => {
      alert("Question is updated Successfully..!")
      console.log(data)
      this.router.navigate(['/admin/questionList']);
    },
      err => {
        this.option=true;
      });

  }
}