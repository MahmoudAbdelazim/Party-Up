import { Injectable } from '@angular/core';
declare let toastr:any

@Injectable({
  providedIn: 'root'
})
export class ToastrService {

  constructor() { }

  success(message : string){
    toastr.success(message);
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
