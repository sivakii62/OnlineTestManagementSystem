import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-register-test',
  templateUrl: './register-test.component.html',
  styleUrls: ['./register-test.component.css']
})
export class RegisterTestComponent implements OnInit {

  isActive: boolean = true;
  testDetails: any[];
  feedback: any[];
  searchText3:any;
 
  constructor(private router:Router,private datepipe: DatePipe,private userService:UserService) {
    this.userService.getAllTest().subscribe((data)=>{
      this.testDetails = data;
      this.userService.hide();
      this.testDetails = this.testDetails.filter(t => (t.testStatus != -1));
  },
  (err)=>{
   // swal("No Test is available!");
    this.userService.hide();
  })
   }

  ngOnInit() {
    let u=localStorage.token
  }
  checkStatus(num: number){
    if(num == 0){
      return "Currently Active !";
    }
    if(num == -1){
      return "Test Completed and Review Taken...";
    }
    if(num == 1){
      return "Upcoming Test!";
    }
  }
  getDetails(test: any){
    console.log(test);
    let startdate = this.datepipe.transform(test.startDate, 'dd-MM-yyyy HH:mm:ss');
    let enddate = this.datepipe.transform(test.endDate, 'dd-MM-yyyy HH:mm:ss');
   // swal(test.testTitle, "Test Start Date: " + startdate + "\nTest End Date: " + enddate
    // + "\nTotal Question: "+ test.totalQuestion + "\nTotal Marks: "+ test.testTotalMarks + "\nTest Duration(min): "
     //+  test.testDuration + "\nTest Status: " + this.checkStatus(test.testStatus));
  }
  FeedbackTest(test: any){
    let u=localStorage.token
    this.isActive = false;
    // swal(this.auth.getUserid() + ": userId and testID: " + test.test_Id);
    this.userService.getRegisterInTest(u, test.test_Id).subscribe((data)=>{
      this.isActive = true;
      this.testDetails = this.testDetails.filter(t=>(t.test_Id != test.test_Id));
      //swal("Registered SuccessFully!", "check the Details very carefully..", "success");
  },
  (err)=>{
    console.log(err);
    if(err.error.text == "User Founded... and Test Assigned"){
     // swal("Registered SuccessFully!", "check the Details very carefully..", "success");
      this.isActive = true;
      this.userService.hide();
      return;
    }
    console.log(err.error.text);
    //swal("You have already Register in this test", "Problem occured!", "info")
    this.isActive = true;
    this.userService.hide();
  })
  }
}
