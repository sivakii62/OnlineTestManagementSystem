import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MyJwt } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {
  loginForm: FormGroup;
  validate:MyJwt;
  submitted: boolean= false;
  constructor(private formBuilder:FormBuilder,private router:Router,private userService:UserService) { 

  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username:['',Validators.required],
      password: ['', Validators.required]
    });
    
      localStorage.removeItem("token");
      this.router.navigate(['/login']);
    
  }
  // login() function
  login(){
    this.submitted = true;
    if(this.loginForm.invalid){
      return;
    }

    let username = this.loginForm.controls.username.value;
    let password = this.loginForm.controls.password.value;
this.userService.valid(username,password).subscribe(data=>{
  console.log(data)
  this.function2(data);
},
err=>{
  console.log(err.error);
  alert("Invalid login Credentials");
})   
  }
  // end of Login() function
  function2(data)
  {
    this.validate=data;
    
      // let username = this.loginForm.controls.username.value;
      // if(this.validate==1){
      //   localStorage.username = username;
      //   this.router.navigate(['userDashboard']);
      // }
      // if(this.validate==2)
      // {
      //   localStorage.username = username;
      //   this.router.navigate(['admin']);
      // }
      // if(this.validate==0){
      //   alert("Invalid login credentials")
      // }

      if(!this.validate.role){
          localStorage.token = this.validate.jwt;
          console.log(this.validate.jwt)
          this.router.navigate(['userDashboard']);
        }

        if(this.validate.role)
        {
          localStorage.token = this.validate.jwt;
          this.router.navigate(['admin']);
        }

        if(!this.validate){
          return;
        }

    }
  forgot()
  {
    this.router.navigate(['/forgot']);
  }
  home()
  {
    this.router.navigate(['/home']);
  }
      
  

}