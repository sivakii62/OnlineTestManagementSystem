import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { User, User_Test } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  

  constructor(private http:HttpClient,private router: Router) { }



getUserid(): string
  {
    return "kjh";
  }

topPerformers() :Observable<User_Test[]>
{
  return  this.http.get<User_Test[]>('http://localhost:8082/Admin/top_performers')}

topPerformersOfTest(testId:number)
{
  return  this.http.get<User[]>('http://localhost:8082/Admin/top_performers/'+testId )
}

caluculateScore(userTestID:number)
{
  return  this.http.get<number>('http://localhost:8082/Admin/getScore/'+userTestID )
}

assignTest(testID:number,userID:number)
{
  return  this.http.get<number>('http://localhost:8082/Admin/assignTest/'+testID+'/'+userID )
}

}