import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  navBarDisplay(){
    // if (this.logIn.navBarFlag[0] && this.logIn.navBarFlag[1])
    //   return true;
    // return false;
  }
}
