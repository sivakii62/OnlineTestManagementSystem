import { Component, OnInit } from '@angular/core';
import { Test, TopResults, User, User_Test } from 'src/app/models/user.model';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-top-performers',
  templateUrl: './top-performers.component.html',
  styleUrls: ['./top-performers.component.css']
})
export class TopPerformersComponent implements OnInit {

  user_tests:User_Test[]=[];
  user_test:User_Test;
  topResults:TopResults[]=[];
  user:User;
  test:Test;
  testTitle='';
  username='';
  at:number=0;
  constructor(
    private service:AdminService) { }

  ngOnInit() {
    

    this.topp();
    
  }


  topp(){
    this.service.topPerformers().subscribe(data => {
      this.user_tests=data;
      console.log(data);
      console.log(this.user_tests)
    },
      err => {
        console.log(err.stack);
      });
  
  }

  // top()
  // {


  //   this.service.topPerformers().subscribe(data => {
  //     this.user_tests=data;

  //     for (var index in data) {
        
  //       this.user=data[index].user;
  //       console.log(this.user)

  //       var obj:TopResults;
  //       obj={
  //         test:data[index].test.test_Id+'',
  //         attempt:this.user_tests[index].totalAttempt,
  //         user:data[index].user.userId+''
  //       }
  //       this.topResults.push(obj);
  //        }
  //        console.log(this.topResults)
  //   },
  //     err => {
  //       console.log(err.stack);
  //     });


     

    
     
  // }


  

}
