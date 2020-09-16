import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  loginForm: FormGroup;
  validate:number;
  submitted: boolean= false;
  constructor(private formBuilder:FormBuilder,private router:Router,private userService:UserService) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username:['',Validators.required],
      email:['',Validators.required],
      password: ['',[ Validators.required,Validators.pattern("[A-Za-z]{4}[0-9]{4}")]],
    });
   
  }
Login()
{
  this.router.navigate(['/login']);
}
forgotPassword()
{

  this.submitted=true;
  if(this.loginForm.invalid)
  {
    return;
  }
  console.log(this.loginForm.value);
  this.userService.forget(this.loginForm.value).subscribe(data=>{
   
    this.function1(data);
},err=>{

  console.log(err.error)
  
 alert("incorrect username")
}); 
}
function1(data)
{
  this.validate=data;
 if(this.validate==1)
 {
    
  alert("new password created successfully, login with your credentials")
  this.router.navigate(['/login']);
 }
 else{
   alert('incorrect username');
 }


}

invalidLogin:boolean = false;
}
