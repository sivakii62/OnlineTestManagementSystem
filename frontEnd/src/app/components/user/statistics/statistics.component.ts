import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

import swal from 'sweetalert';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  allTest: any[];
  allRegisterTest: any[];
  upcomingTest: any[];
  ActiveTest:any[];
  TotalnumberOfTest:number  = 0;
  TotalUpcomingTest: number = 0;
  TotalCurrentlyActiveTest:number = 0;
  TotalPassTest: number = 0;
  count:number=0;
  constructor(private userService:UserService,private router:Router) { 
    userService.show(); 
    let u=localStorage.token
 
    this.userService.getAllTestAssignedToAUser(u).subscribe((data)=>{
 
            this.allRegisterTest = data;
            this.TotalnumberOfTest = this.allRegisterTest.length;
            this.upcomingTest =  this.allRegisterTest.filter(t=>(t.testStatus == 1));
            this.ActiveTest = this.allRegisterTest.filter(t=>(t.testStatus == 0));
            this.TotalUpcomingTest = this.allRegisterTest.filter(t=>(t.testStatus == 1)).length;
            this.TotalCurrentlyActiveTest = this.allRegisterTest.filter(t=>(t.testStatus == 0)).length;
            this.TotalPassTest = this.allRegisterTest.filter(t=>(t.testStatus == -1)).length;
            userService.hide(); 

     },
     (err)=>{
       console.log(err);
           swal(err.error.details);
          userService.hide(); 
     })
  
  this.userService.getAllTest().subscribe((data)=>{
    console.log(data);
    this.allTest  = data;
},
(err)=>{
   swal(err.error.details);
})
}
  ngOnInit() {
   
  }

}
