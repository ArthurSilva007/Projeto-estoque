import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../navbar/navbar';
import { VendaResponse, VendaService } from '../../services/venda.service';

@Component({
  selector: 'app-detalhes-venda',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './detalhes-venda.html',
  styleUrls: ['./detalhes-venda.scss']
})
export class DetalhesVendaComponent implements OnInit {
  vendasDetalhadas: VendaResponse[] = [];

  constructor(private vendaService: VendaService) {}

  ngOnInit(): void {
    // CORREÇÃO: Apenas se inscreve no serviço reativo
    this.vendaService.vendas$.subscribe((vendas: VendaResponse[]) => {
      this.vendasDetalhadas = vendas;
    });
  }

  // O método carregarVendas() foi removido

  baixarPdf(vendaId: number): void { /* ... */ }
}
