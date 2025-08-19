// Em: src/app/services/atalho-teclado.service.ts

import { Injectable, Inject, PLATFORM_ID } from '@angular/core'; // <-- 1. IMPORTE Inject e PLATFORM_ID
import { isPlatformBrowser } from '@angular/common';           // <-- 2. IMPORTE isPlatformBrowser
import { Router } from '@angular/router';
import { fromEvent } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AtalhoTecladoService {

  // 3. INJETE O PLATFORM_ID NO CONSTRUTOR
  constructor(private router: Router, @Inject(PLATFORM_ID) private platformId: Object) {

    // 4. VERIFIQUE SE ESTAMOS NO NAVEGADOR ANTES DE ACESSAR 'document'
    if (isPlatformBrowser(this.platformId)) {
      // Este código agora só será executado no lado do cliente (no navegador)
      fromEvent<KeyboardEvent>(document, 'keydown').subscribe(event => {
        this.handleKeyboardEvent(event);
      });
    }
  }

  private handleKeyboardEvent(event: KeyboardEvent): void {
    switch (event.key) {
      case 'F1':
        event.preventDefault();
        this.router.navigate(['/produtos']);
        break;
      case 'F2':
        event.preventDefault();
        this.router.navigate(['/nova-venda']);
        break;
      case 'F3':
        event.preventDefault();
        this.router.navigate(['/historia-vendas']);
        break;
      case 'F4':
        event.preventDefault();
        // this.router.navigate(['/detalhes-venda']);
        break;
      case 'F5':
        event.preventDefault();
        this.router.navigate(['/estoque']);
        break;
    }
  }
}
