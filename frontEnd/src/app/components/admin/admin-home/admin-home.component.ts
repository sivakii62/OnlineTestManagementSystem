import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Question, Test, User } from 'src/app/models/user.model';
import { CreateExamService } from 'src/app/services/create-exam.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  questions:Question[];
  users:User[];
  tests:Test[];

  constructor(private router: Router,private service:CreateExamService) {
  }
 
   ngOnInit() {
    if(localStorage.token!=null){
  
    this.getAllUsers();
    this.getAllQuestions();
    this.getAllTests();
  }
  else{
    this.router.navigate(['/login']);
  }

   }
   
   getAllUsers(){
    this.service.getAllUsers().subscribe(data =>{
      console.log(data);
      this.users=data;
    },
    err=>{
      console.log(err)
    });
   }
   getAllTests(){
    this.service.getAllTests().subscribe(data =>{
      console.log(data);
      this.tests=data;
    },
    err=>{
      console.log(err)
    });

   }
   getAllQuestions(){
    this.service.getAllQuestions().subscribe(data =>{
      console.log(data);
      this.questions=data;
    },
    err=>{
      console.log(err)
    });

   }
 

 }
 