import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AtalhoTecladoService } from './services/atalho-teclado.service'; // <-- Importe

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class AppComponent {
  title = 'estoque-frontend';

  constructor(private atalhoService: AtalhoTecladoService) {} // <-- Adicione
}
