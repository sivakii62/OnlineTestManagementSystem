import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
signupForm:FormGroup;
validate:number;
submitted:boolean=false;
  constructor(private formBuilder:FormBuilder,private router:Router,private userService:UserService) { }

  ngOnInit() {
    this.signupForm = this.formBuilder.group({
  
  email:['',[Validators.required,Validators.email]],
  username:['',[Validators.required ,Validators.pattern("[A-Za-z]{4}[0-9]{4}")]],
  password: ['',[ Validators.required,Validators.pattern("[A-Za-z]{4}[0-9]{4}")]],
});

}
signUp()
{
  this.submitted=true;
  if(this.signupForm.invalid)
  {
    return;
  }
  console.log(this.signupForm.value);
  this.userService.Register(this.signupForm.value).subscribe(data=>{
   
    this.function1(data);
},err=>{

  console.log(err.error.details)
  
  if(err.error.details=="Email already used ,try with different Email Id"){
    alert("Email already used ,try with different Email Id");
  }
  if(err.error.details=="UserName already taken ,try different UserName"){
    alert("UserName already taken ,try different UserName")
  }
}); 
}
function1(data)
{
  this.validate=data;
 
    
  alert("account created successfully login with your credentials")
  this.router.navigate(['/login']);


}
home()
{
  this.router.navigate(['/home']);
}
invalidLogin:boolean = false;
}
