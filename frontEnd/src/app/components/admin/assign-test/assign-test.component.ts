import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Test, User } from 'src/app/models/user.model';
import { AdminService } from 'src/app/services/admin.service';
import { CreateExamService } from 'src/app/services/create-exam.service';

@Component({
  selector: 'app-assign-test',
  templateUrl: './assign-test.component.html',
  styleUrls: ['./assign-test.component.css']
})
export class AssignTestComponent implements OnInit {

  constructor(private service:AdminService,private router:Router,private services:CreateExamService) { }
  userId:number=-2;
  testId:number=-2;
  result='';
  users:User[];
  tests:Test[];

  ngOnInit(){
    if(localStorage.token!=null){
    this.getAllTests();
    this.getAllUsers();
  }
  else{
    this.router.navigate(['/login']);
  }
}

  getAllUsers(){
    this.services.getAllUsers().subscribe(data =>{
      console.log(data);
      this.users=data;
    },
    err=>{
      console.log(err)
    });
   }
   getAllTests(){
    this.services.getAllTests().subscribe(data =>{
      console.log(data);
      this.tests=data;
    },
    err=>{
      console.log(err)
    });

   }

  assign()
  {
    console.log(this.userId)
    console.log(this.testId)
    if(this.userId<0 || this.testId<0){
      this.userId=-1
      this.testId=-1
    }
    if(this.userId!=-1 && this.testId!=-1)
    {
      this.result='The test has been succesfully assigned'
      this.service.assignTest(this.testId,this.userId).subscribe(data => {
        alert("Test is assigned to the user")
        console.log(data)
      },
        err => {
          alert(err.error.details);
        });
    }
  }

}
