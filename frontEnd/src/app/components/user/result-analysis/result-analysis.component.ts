import { Component, OnInit } from '@angular/core';
import { CreateExamService } from 'src/app/services/create-exam.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-result-analysis',
  templateUrl: './result-analysis.component.html',
  styleUrls: ['./result-analysis.component.css']
})
export class ResultAnalysisComponent implements OnInit {
  var:any
  temp1: number
  temp2: number
  temp3: string
  // detailedResult: any
  tests: any;
  test:any;
  questions: any;
  showDetails: boolean
  noOfCrrctAns: number
  categoryResult: any
  categoryResults:any;
  detailedResult:any;
  constructor(private userService:UserService,private service:CreateExamService) {
   
   }

  ngOnInit() {
    let u=localStorage.token
    this.showDetails = false
    this.getResultForUser(u);
  }

  getResultForUser(u:string){
    this.userService.getResultForUser(u).subscribe(data => {
      console.log(data);
      this.tests = data;
    },err=>{
      console.log(err.error);
      // alert("Problem while fetching the data:" + "\n message: "
      // + err.error.message + "\n details: " + err.error.details)
    });

  }

  getTest(uti:number,testId:number,userResult:any){
    this.service.getTest(testId).subscribe(data=>{
      console.log(userResult);
      console.log(data);
      this.detailedResult=userResult;

      this.test=data;
      this.showDetails=true;
      this.getCategoryResult(uti);
    },err=>{
      console.log("gcr")

    });
  }
  getCategoryResult(uti:number){
    this.userService.getCategoryResultForTest(uti).subscribe(data=>{
      console.log(data);
      this.categoryResults=data
    },err=>{
      console.log("gcr")
    });

  }




  // showDetailedResults(obj: any) {
  //   // this.detailedResult = obj
  //   //  console.log(this.detailedResult.usertestAnswer);
  //   this.userService.getQuestions(obj.testId.test_Id).subscribe(data => {
  //     console.log(data);
  //     this.questions = data;
  //     console.log(this.questions);

  //   },err=>{
  //     console.log(err.error);
  //     alert("Problem while fetching the data:" + "\n message: "
  //     + err.error.message + "\n details: " + err.error.details)
  //   });
  //   setTimeout(() => {
  //     this.showDetails = true                          
  //   }, 1000);
  //   this.noOfCrrctAns = 0
  //   for (let i = 0; i < this.detailedResult.testCorrectAnswer.length; i++) {
  //     if (this.detailedResult.testCorrectAnswer[i] == true)
  //       this.noOfCrrctAns++
  //   }

  //   this.userService.getCategoryResultForTest(obj.user_Test_Id).subscribe(data => {
  //     this.categoryResult = data;
  //     console.log(this.categoryResult);
  //     this.temp1 = 0;
  //     this.temp2 = 0;
  //     this.temp3 = "";

  //     setTimeout(() => {
  //       for (let i = 0; i < this.categoryResult.length; i++) {
  //         this.temp1 = 0
  //         for (let j = 0; j < this.questions.length; j++) {
  //           if (this.questions[j].questionCategory.categoryId == this.categoryResult[i].category.categoryId) {
  //             this.temp1 += this.questions[j].questionMarks
  //           }
  //         }
  //         this.temp2 = (this.categoryResult[i].categoryResult / this.temp1) * 100
  
  //         console.log(this.temp2);
  //         console.log(this.categoryResult[i].category.name);
  //         this.temp3=this.temp2.toString()+"%";
  //         console.log(this.temp3);
  //         (<HTMLInputElement>document.getElementById("fill-"+this.categoryResult[i].category.name.toString())).style.width=this.temp2.toString()+"%";
  //         (<HTMLInputElement>document.getElementById("marks-"+this.categoryResult[i].category.name.toString())).value=this.temp2.toString()+"%";
  //       }
  //     }, 3000);
  //   },err=>{
  //     console.log(err.error);
  //     alert("Problem while fetching the data:" + "\n message: "
  //     + err.error.message + "\n details: " + err.error.details)
  //   });
  // }

  goBack(){
    console.log("hhhhhh")
    this.showDetails = false;
  }

}
