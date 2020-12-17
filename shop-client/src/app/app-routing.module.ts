import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from "./keycloak.guard";
import {HelloComponent} from "./hello/hello.component";

const routes: Routes = [
  {
    path: 'shop',
    component: HelloComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
