import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  todaysDate = new Date();
  constructor(private router: Router) {
    setInterval(() => {
      this.todaysDate = new Date();
    }, 1000);
  }
 
   ngOnInit() {
     if(localStorage.token!=null){

     }
     else{
       this.router.navigate(['/login']);
     }
   }
 
   logOut(){
     localStorage.removeItem("token");
     this.router.navigate(['/login']);
   }
  }
 