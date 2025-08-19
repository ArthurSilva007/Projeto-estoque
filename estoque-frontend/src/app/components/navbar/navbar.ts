import { Component } from '@angular/core';
import { RouterModule } from '@angular/router'; // Importe para routerLink funcionar

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule], // Adicione aqui
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.scss']
})
export class NavbarComponent {}
