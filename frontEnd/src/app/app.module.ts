import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './components/signup/signup.component';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { AddTestComponent } from './components/admin/add-test/add-test.component';
import { UpdateTestComponent } from './components/admin/update-test/update-test.component';
import { AddQuestionComponent } from './components/admin/add-question/add-question.component';
import { UpdateQuestionComponent } from './components/admin/update-question/update-question.component';
import { TestListComponent } from './components/admin/test-list/test-list.component';
import { QuestionListComponent } from './components/admin/question-list/question-list.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { TestQuestionsComponent } from './components/admin/test-questions/test-questions.component';
import { AdminHomeComponent } from './components/admin/admin-home/admin-home.component';
import { HomeComponent } from './components/home/home.component';
import { RegisterTestComponent } from './components/user/register-test/register-test.component';
import { TestTabComponent } from './components/test-tab/test-tab.component';
import { AttemptTestComponent } from './components/user/attemptTest/attempt-test/attempt-test.component';
import { StatisticsComponent } from './components/user/statistics/statistics.component';
import { FilterTestPipePipe } from './Pipes/filter-test-pipe.pipe';
import { TestFilterPipe } from './Pipes/test-filter.pipe';
import { DatePipe } from '@angular/common';
import { TopPerformersComponent } from './components/admin/top-performers/top-performers.component';
import { TopPerformersOftestComponent } from './components/admin/top-performers-oftest/top-performers-oftest.component';
import { CaluculateScoreComponent } from './components/admin/caluculate-score/caluculate-score.component';
import { AssignTestComponent } from './components/admin/assign-test/assign-test.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { ForgotPassComponent } from './components/forgot-pass/forgot-pass.component';
// import { LoginComponent } from './components/login/login.component';
import { ResultAnalysisComponent } from './components/user/result-analysis/result-analysis.component';
import { LoggingService } from './models/loggingService';
import { HttpInterceptorService } from './services/http-interceptor.service';
import { CountdownModule } from 'ngx-countdown';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    HomeComponent,
    RegisterTestComponent,
    AdminComponent,
    StatisticsComponent,
    RegisterTestComponent,
    AttemptTestComponent,
    TestTabComponent,
    UserComponent,
    AddTestComponent,
    UpdateTestComponent,
    AddQuestionComponent,
    FilterTestPipePipe,
    UpdateQuestionComponent,
    TestListComponent,
    QuestionListComponent,
    TestQuestionsComponent,
    AdminHomeComponent,
    TestFilterPipe,
    TopPerformersComponent,
    TopPerformersOftestComponent,
    CaluculateScoreComponent,
    AssignTestComponent,
    ForgotPasswordComponent,
    UserLoginComponent,
    ForgotPassComponent,
    UserLoginComponent,
    ResultAnalysisComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule, 
    HttpClientModule,
    FormsModule,
    CountdownModule    
  ],
  providers: [
    LoggingService,
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
