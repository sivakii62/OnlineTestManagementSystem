import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddQuestionComponent } from './components/admin/add-question/add-question.component';
import { AddTestComponent } from './components/admin/add-test/add-test.component';
import { QuestionListComponent } from './components/admin/question-list/question-list.component';
import { TestListComponent } from './components/admin/test-list/test-list.component';
import { UpdateQuestionComponent } from './components/admin/update-question/update-question.component';
import { UpdateTestComponent } from './components/admin/update-test/update-test.component';
import { TestQuestionsComponent } from './components/admin/test-questions/test-questions.component';
import { AdminComponent } from './components/admin/admin.component';
import { AdminHomeComponent } from './components/admin/admin-home/admin-home.component';
import { HomeComponent } from './components/home/home.component';
// import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { RegisterTestComponent } from './components/user/register-test/register-test.component';
// import { ResultAnalysisComponent } from './components/user/result-analysis/result-analysis.component';
import { StatisticsComponent } from './components/user/statistics/statistics.component';
import { UserComponent } from './components/user/user.component';
import { TestTabComponent } from './components/test-tab/test-tab.component';
import { AttemptTestComponent } from './components/user/attemptTest/attempt-test/attempt-test.component';
import { AssignTestComponent } from './components/admin/assign-test/assign-test.component';
import { CaluculateScoreComponent } from './components/admin/caluculate-score/caluculate-score.component';
import { TopPerformersComponent } from './components/admin/top-performers/top-performers.component';
import { TopPerformersOftestComponent } from './components/admin/top-performers-oftest/top-performers-oftest.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { ResultAnalysisComponent } from './components/user/result-analysis/result-analysis.component';

import { UserLoginComponent } from './components/user-login/user-login.component';


const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'home',component:HomeComponent},
  {path:'login',component:UserLoginComponent},
  {path:'forgot',component:ForgotPasswordComponent},
  {path:'signUp',component:SignupComponent},
  {path:'userDashboard',component:UserComponent,
  children:[
              {path:'',component:StatisticsComponent},
              {path:'satistics',component:StatisticsComponent},
              {path:'result',component:ResultAnalysisComponent},
              {path:'register',component:RegisterTestComponent},
              {path:'attemptTest',component:AttemptTestComponent}
            ]},
  { path: 'Test/:userName/:testId', component: TestTabComponent},
  {path:'admin',component:AdminComponent,
  children: [ 
              {path: '',component: AdminHomeComponent},
              {path: 'home',component: AdminHomeComponent},
              {path: 'addQuestion',component: AddQuestionComponent},
              {path: 'addTest',component: AddTestComponent},
              {path:'questionList',component:QuestionListComponent},
              {path: 'testList',component: TestListComponent},
              {path: 'updateQuestion/:questionId',component: UpdateQuestionComponent},
              {path: 'updateTest/:testId',component: UpdateTestComponent},
              {path: 'testQuestions/:testId',component: TestQuestionsComponent},
              {path: 'topPerformers',component: TopPerformersComponent},
              {path: 'topPerformersOfTest',component: TopPerformersOftestComponent},
              {path: 'assignTest',component: AssignTestComponent},
              {path: 'declareResult',component: CaluculateScoreComponent},
            ]},
  {path:'**',component:AdminHomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
