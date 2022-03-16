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

const routes: Routes = [
  {path: "", redirectTo: "home", pathMatch: "full"},
  {path: "home", component: HomeComponent},
  {path: "login", component:LoginComponent},
  {path: "logout", component:LogoutComponent},
  {path: "register", component: RegisterComponent},
  {path: "findpeers", component: FindpeersComponent},
  {path: "profile", component:ProfileComponent},
  {path: "personalityTest", component:PersonalityTestComponent},
  {path: "**", component:NotFound404Component}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
