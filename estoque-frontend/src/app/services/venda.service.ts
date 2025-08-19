// Cole este código completo em: src/app/services/venda.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';

// --- Interfaces (DTOs) ---
export interface ItemVendaRequest { produtoId: number; quantidade: number; }
export interface VendaRequest { nomeCliente: string; valorInstalacao: number; itens: ItemVendaRequest[]; }
export interface ItemVendaResponse { produtoId: number; descricaoProduto: string; quantidade: number; valorUnitario: number; }
export interface VendaResponse {
  id: number; nomeCliente: string; telefoneCliente: string; cnpjCliente: string; dataVenda: string;
  atendente: string; carro: string; descricaoDefeito: string; valorInstalacao: number;
  valorTotal: number; itens: ItemVendaResponse[];
}

@Injectable({
  providedIn: 'root'
})
export class VendaService {
  private apiUrl = 'http://localhost:8080/api/vendas';

  // Lógica Reativa que estava faltando
  private vendasSubject = new BehaviorSubject<VendaResponse[]>([]);

  constructor(private http: HttpClient) {
    this.buscarVendasIniciais();
  }

  // A propriedade 'vendas$' que seus componentes estão procurando
  get vendas$(): Observable<VendaResponse[]> {
    return this.vendasSubject.asObservable();
  }

  private buscarVendasIniciais(): void {
    this.http.get<VendaResponse[]>(this.apiUrl).subscribe(vendas => {
      this.vendasSubject.next(vendas);
    });
  }

  registrarVenda(venda: VendaRequest): Observable<VendaResponse> {
    return this.http.post<VendaResponse>(this.apiUrl, venda).pipe(
      tap(() => this.buscarVendasIniciais())
    );
  }

  gerarPdf(vendaId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${vendaId}/nota-pdf`, {
      responseType: 'blob'
    });
  }
}
