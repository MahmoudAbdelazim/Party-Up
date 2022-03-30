import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { FindpeersComponent } from './findpeers/findpeers.component';
import { NotFound404Component } from './not-found404/not-found404.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AllRightsReservedComponent } from './all-rights-reserved/all-rights-reserved.component';
import { LogoutComponent } from './logout/logout.component';
import { PersonalityTestComponent } from './personality-test/personality-test.component';
import { ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http'
import {PlayersDataService} from "./players-data.service";
@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    ProfileComponent,
    FindpeersComponent,
    NotFound404Component,
    NavbarComponent,
    AllRightsReservedComponent,
    LogoutComponent,
    PersonalityTestComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [PlayersDataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
