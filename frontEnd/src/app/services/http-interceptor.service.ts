import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { parseJwt } from '../models/customFunctions';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private router:Router){

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const changedReq = req.clone({headers: req.headers.set('Authorization', 'Bearer '+localStorage.getItem('token'))});
    return next.handle(changedReq).catch(err => {
      // onError
      console.log(err);
      if (err instanceof HttpErrorResponse) {
          console.log(err.status);
          console.log(err.statusText);
          if (err.status === 403) {
              alert("your Session is Expired")
              this.router.navigate(["/home"]);
              localStorage.removeItem('token')
          }
      }
      return Observable.throw(err);
  }) as any;
  }

}