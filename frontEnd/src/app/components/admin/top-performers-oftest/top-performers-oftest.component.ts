import {  OnInit } from '@angular/core';
import { User, User_Test } from 'src/app/models/user.model';
import { AdminService } from 'src/app/services/admin.service';
import {Component} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-top-performers-oftest',
  templateUrl: './top-performers-oftest.component.html',
  styleUrls: ['./top-performers-oftest.component.css']
})
export class TopPerformersOftestComponent implements OnInit {

 

  
  users:User[]=[];
  test:number=1;
  constructor(private service:AdminService) {

   }

  ngOnInit(): void {
    this.topp();
  }




  topp(){
    this.service.topPerformersOfTest(this.test).subscribe(data => {
      this.users=data;
    
    },
      err => {
        console.log(err.stack);
      });
  
  }

}
