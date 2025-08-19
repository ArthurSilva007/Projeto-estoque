import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../navbar/navbar';
import { Produto, ProdutoService } from '../../services/produto.service';
import { VendaRequest, VendaResponse, VendaService } from '../../services/venda.service';

interface ItemVendaSaida {
  produtoId: number; descricao: string; quantidade: number;
  valorUnitario: number; estoqueDisponivel: number;
}

@Component({
  selector: 'app-nova-venda',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './nova-venda.html',
  styleUrls: ['./nova-venda.scss']
})
export class NovaVendaComponent implements OnInit {
  listaCompletaProdutos: Produto[] = [];
  listaProdutosFiltrados: Produto[] = [];
  itensDaVenda: ItemVendaSaida[] = [];
  nomeCliente: string = '';
  valorInstalacao: number = 0;
  termoBusca: string = '';

  constructor(
    private produtoService: ProdutoService,
    private vendaService: VendaService
  ) {}

  ngOnInit(): void {
    this.produtoService.produtos$.subscribe((produtos: Produto[]) => {
      this.listaCompletaProdutos = produtos;
      this.listaProdutosFiltrados = produtos;
    });
  }

  filtrarProdutos(): void {
    if (!this.termoBusca) { this.listaProdutosFiltrados = this.listaCompletaProdutos; }
    else { this.listaProdutosFiltrados = this.listaCompletaProdutos.filter(p =>
      p.descricao.toLowerCase().includes(this.termoBusca.toLowerCase()));
    }
  }

  adicionarProduto(produto: Produto): void {
    const itemExistente = this.itensDaVenda.find(i => i.produtoId === produto.id);
    if (itemExistente) {
      if (itemExistente.quantidade < produto.quantidadeEstoque) { itemExistente.quantidade++; }
    } else {
      if (produto.quantidadeEstoque > 0) {
        this.itensDaVenda.push({
          produtoId: produto.id, descricao: produto.descricao, quantidade: 1,
          valorUnitario: produto.valorVenda, estoqueDisponivel: produto.quantidadeEstoque
        });
      }
    }
  }

  get valorTotalProdutos(): number {
    return this.itensDaVenda.reduce((total, item) => total + (item.valorUnitario * item.quantidade), 0);
  }

  get valorTotalVenda(): number {
    return this.valorTotalProdutos + this.valorInstalacao;
  }

  finalizarVenda(): void {
    if (!this.nomeCliente || this.itensDaVenda.length === 0) {
      alert("Preencha o nome do cliente e adicione produtos à venda.");
      return;
    }
    // CORREÇÃO: Preenchendo o objeto com os dados do componente
    const novaVenda: VendaRequest = {
      nomeCliente: this.nomeCliente,
      valorInstalacao: this.valorInstalacao,
      itens: this.itensDaVenda.map(item => ({
        produtoId: item.produtoId,
        quantidade: item.quantidade
      }))
    };
    this.vendaService.registrarVenda(novaVenda).subscribe({
      next: (resposta: VendaResponse) => {
        alert(`Venda Nº ${resposta.id} registrada!`);
        this.imprimirRecibo(resposta.id);
        this.resetarVenda();
      },
      error: (erro: any) => { alert('Erro ao registrar venda.'); }
    });
  }

  imprimirRecibo(vendaId: number): void {
    this.vendaService.gerarPdf(vendaId).subscribe((pdfBlob: Blob) => {
      const pdfUrl = URL.createObjectURL(pdfBlob);
      window.open(pdfUrl, '_blank');
      URL.revokeObjectURL(pdfUrl);
    });
  }

  resetarVenda(): void {
    this.itensDaVenda = []; this.nomeCliente = '';
    this.valorInstalacao = 0; this.termoBusca = '';
    this.filtrarProdutos();
  }
}
