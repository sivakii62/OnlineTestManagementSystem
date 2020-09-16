import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { parseJwt } from 'src/app/models/customFunctions';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  todaysDate = new Date();
  userName:string =  parseJwt(localStorage.getItem('token'))
  constructor(private router : Router) { 
    setInterval(() => {
      this.todaysDate = new Date();
    }, 1000);
  }

  ngOnInit() {
  
      
    
  }
  logOut()
  {
    localStorage.removeItem('token')
    this.router.navigate(['login']);
  
}
}
