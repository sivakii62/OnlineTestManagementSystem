import { HttpBackend, HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { parseJwt } from '../models/customFunctions';
import { Test, User } from '../models/user.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl: string = "http://localhost:8082";

  private httpClient: HttpClient;

 
  constructor(private http:HttpClient,handler:HttpBackend) {
    this.httpClient = new HttpClient(handler);
   }


  valid(id:String,password:string)
  {
    // let customHeaders = new HttpHeaders();
    // customHeaders = customHeaders.append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    // customHeaders = customHeaders.append('Access-Control-Allow-Origin', 'http://localhost:4200')
    // customHeaders = customHeaders.append('Access-Control-Allow-Methods', 'GET,POST,OPTIONS,DELETE,PUT')

    return this.httpClient.post(this.baseUrl+"/authenticate",{
      "username":id,
	    "password":password
    },)
  }


  getAllUserTest()
  {
    return this.http.get(this.baseUrl+"/Admin/getAllUserTests")
  }

  Register(users:User )
  {
return this.httpClient.post(this.baseUrl+"/signup",users);
  }
  getUserid(id:string)
  {
    return this.http.get(this.baseUrl+"/getUserId"+parseJwt(localStorage.getItem('token')));
  }
  getAllTestAssignedToAUser(userId:string)
  {
    return this.http.get<Test[]>(this.baseUrl+"/getAllTestByUserId"+"/"+parseJwt(localStorage.getItem('token')));
  }
  getAllTest(){
    return this.http.get<any[]>(this.baseUrl + "/getAllTest");
 }
 forget(users:User)
 {
   return this.http.put(this.baseUrl+"/forgot",users);
 }
 getResultForUser(userId:string)
 {

   return this.http.get<any[]>(this.baseUrl+"/getResult"+"/"+parseJwt(localStorage.getItem('token')));
 }
 getRegisterInTest (userId:string, testId:any){
  return this.http.get<any>(this.baseUrl + "/getResultModule/assignTest?testId=" + testId + "&userId=" + parseJwt(localStorage.getItem('token')));
}
getCategoryResultForTest(userTestId : number){
  return this.http.get<any>(this.baseUrl+"/getCategoryResult/"+userTestId);
}
CalculateResult(uti:number)
{
  return this.http.get(this.baseUrl+"/Admin/getScore/"+uti);
  
  
}
getQuestions(testId : number){
  return this.http.get<any>(this.baseUrl+"/getQuestions/"+testId);
}






private loader = new BehaviorSubject<boolean>(true);
private state = this.loader.asObservable();

show() {
  this.loader.next(<boolean>true);
}
hide() {
  this.loader.next(<boolean>false);
}

getState() {
  return this.state;
}
}