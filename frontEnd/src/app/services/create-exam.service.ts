import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Question, Test, Category, User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class CreateExamService {

  constructor(private http: HttpClient) { }

  baseUrl: string = "http://localhost:8082/exam";

  getAllCategories(){
    return this.http.get<Category[]>(this.baseUrl+"/getAllCategory");
  }

  getAllUsers(){
    return this.http.get<User[]>(this.baseUrl+"/getAllUsers");
  }

  addCategory(categoryName:string){
    return this.http.post<boolean>(this.baseUrl+"/addCategory",{"name": categoryName});
  }

  addTest(test:Test){
    return this.http.post<boolean>(this.baseUrl+"/addTest",test);
  }

  addQuestion(selectedCategory:number,question:Question){
    return this.http.post<boolean>(this.baseUrl+"/addQuestion/"+selectedCategory,question);
  }

  getAllQuestions() {
    return this.http.get<Question[]>(this.baseUrl+"/getAllQuestions");
  }

  getAllTests() {
    return this.http.get<Test[]>(this.baseUrl+"/allTests");
  }

  deleteQuestion(questionId:number){
    return this.http.delete<boolean>(this.baseUrl+"/deleteQuestion/"+questionId);
  }

  getTest(testId:number) {
    return this.http.get<Test>(this.baseUrl+"/getTest/"+testId);
  }

  getRemainingQuestions(){
    return this.http.get<Question[]>(this.baseUrl+"/getRemainingQuestions");
  }

  getTestQuestions(testId:number){
    return this.http.get<Question[]>(this.baseUrl+"/getTestQuestions/"+testId);
  }

  getQuestion(questionId:number) {
    return this.http.get<Question>(this.baseUrl+"/getQuestion/"+questionId);
  }

  addQuestionToTest(questionId:number,testId:number){
    return this.http.put<any>(this.baseUrl+"/addQuestionToTest",{"questionId": questionId, "test_Id": testId});
  }

  deleteQuestionFromTest(questionId:number,testId:number){
    return this.http.put<boolean>(this.baseUrl+"/deleteQuestionFromTest",{"questionId": questionId, "test_Id": testId});
  }

  updateTest(testId:number,test:Test){
    return this.http.put<boolean>(this.baseUrl+"/updateTest/"+testId,test);
  }

  deleteTest(testId:number){
    return this.http.delete<boolean>(this.baseUrl+"/deleteTest/"+testId);
  }

  updateQuestion(question:Question){
    return this.http.put<boolean>(this.baseUrl+"/updateQuestion",question);
  }
}
