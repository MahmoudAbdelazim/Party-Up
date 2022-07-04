import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FindpeersComponent } from './findpeers/findpeers.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { NotFound404Component } from './not-found404/not-found404.component';
import { PersonalityTestComponent } from './personality-test/personality-test.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import {FeedComponent} from "./feed/feed.component";
import { ProfileSettingsComponent } from './profile-settings/profile-settings.component';
import {AuthService} from "./auth.service";
import {GuardAuthenticationService} from "./guard-authentication.service";
import {SignupGuardPersonalityService} from "./signup-guard-personality.service";

const routes: Routes = [
  {path: "", redirectTo: "home", pathMatch: "full"},
  {path: "home", component: HomeComponent},
  {path: "login", component:LoginComponent},
  {path: "logout", component:LogoutComponent , canActivate:[GuardAuthenticationService]},
  {path: "register", component: RegisterComponent},
  {path: "findpeers", component: FindpeersComponent , canActivate:[GuardAuthenticationService]},
  {path: "profile/settings", component: ProfileSettingsComponent , canActivate:[GuardAuthenticationService]},
  {path: "profile", component:ProfileComponent , canActivate:[GuardAuthenticationService]},
  {path: "personalityTest/:username", component:PersonalityTestComponent , canActivate:[SignupGuardPersonalityService]},
  {path: "feed", component:FeedComponent , canActivate:[GuardAuthenticationService]},
  {path: "**", component:NotFound404Component},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
