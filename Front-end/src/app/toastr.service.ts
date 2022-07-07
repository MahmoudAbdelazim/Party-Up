import { Injectable } from '@angular/core';
declare let toastr:any

@Injectable({
  providedIn: 'root'
})
export class ToastrService {

  constructor() { }

  success(){
    toastr.success("Request has been sent successfully");
  }
  info(){
    toastr.info();
  }
  warning(){
    toastr.warning();
  }
  error(){
    toastr.error();
  }
}
