import { Injectable } from '@angular/core';
import { HttpBackend, HttpClient, HttpHeaders } from '@angular/common/http';
import { parseJwt } from '../models/customFunctions';

@Injectable({
  providedIn: 'root'
})
export class AttemptExamService {
  

  baseUrl = "http://localhost:8082/Start/";


  constructor(private http:HttpClient){}

  getAllTests(){
    return this.http.get(this.baseUrl+"getAllTest" )
  }

  getTestsAssignedToUser(userName){
   return this.http.get(this.baseUrl+"getAllTestByUserId?userName="+parseJwt(localStorage.getItem('token')))
  }

  getActiveTests(userName,testId){
    return this.http.get(this.baseUrl+"getTestByUserId?userName="+parseJwt(localStorage.getItem('token'))+"&testId="+testId)
  }

  getAllQuestion(userName, testId) {
    return this.http.get(this.baseUrl+"getAllQuestion?userName="+parseJwt(localStorage.getItem('token'))+"&testId="+testId);
   }

  setTestAnswer(getAnswer : any) {
    return this.http.post(this.baseUrl+"setTestAnswer",getAnswer,)
  }


}
