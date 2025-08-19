import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../navbar/navbar';
import { VendaResponse, VendaService, ItemVendaResponse } from '../../services/venda.service';

@Component({
  selector: 'app-historia-vendas',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './historia-vendas.html',
  styleUrls: ['./historia-vendas.scss']
})
export class HistoriaVendasComponent implements OnInit {
  historicoDeVendas: VendaResponse[] = [];

  constructor(private vendaService: VendaService) {}

  ngOnInit(): void {
    this.vendaService.vendas$.subscribe((vendas: VendaResponse[]) => {
      this.historicoDeVendas = vendas;
    });
  }

  baixarRelatorio(vendaId: number): void {
    this.vendaService.gerarPdf(vendaId).subscribe(pdfBlob => {
      const pdfUrl = URL.createObjectURL(pdfBlob);
      window.open(pdfUrl, '_blank');
    });
  }

  // CORREÇÃO: Implementando a lógica da função
  formatarDescricaoItens(itens: ItemVendaResponse[]): string {
    if (!itens || itens.length === 0) {
      return 'Nenhum item na venda';
    }
    return itens.map(item => item.descricaoProduto).join(' / ');
  }
}
