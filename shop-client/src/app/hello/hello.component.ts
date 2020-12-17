import { Component, OnInit } from '@angular/core';
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrls: ['./hello.component.css']
})
export class HelloComponent {

  constructor(private securityService: KeycloakService) {
  }

  logout() {
     this.securityService.logout("http://localhost:4200")
  }

}
