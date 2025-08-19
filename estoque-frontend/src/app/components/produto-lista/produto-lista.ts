import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../navbar/navbar';
import { Produto, ProdutoRequest, ProdutoService } from '../../services/produto.service';
import { Categoria, CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-produto-lista',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavbarComponent],
  templateUrl: './produto-lista.html',
  styleUrls: ['./produto-lista.scss']
})
export class ProdutoListaComponent implements OnInit {
  listaProdutosFiltrados: Produto[] = [];
  idProdutoExpandido: number | null = null;
  exibirFormulario = false;
  categorias: Categoria[] = [];

  // Objeto para os filtros da busca avançada
  filtros = {
    nome: '',
    categoriaId: null as number | null,
    quantidade: null as number | null
  };

  novoProduto: ProdutoRequest = {
    nome: '',
    descricao: '',
    quantidadeEstoque: 0,
    valorCompra: 0,
    valorVenda: 0,
    categoriaId: 0
  };

  constructor(
    private produtoService: ProdutoService,
    private categoriaService: CategoriaService
  ) { }

  ngOnInit(): void {
    // Se inscreve na lista reativa de produtos
    this.produtoService.produtos$.subscribe((produtos: Produto[]) => {
      this.listaProdutosFiltrados = produtos;
    });
    this.carregarCategorias();
  }

  carregarCategorias(): void {
    this.categoriaService.getCategorias().subscribe((data: Categoria[]) => {
      this.categorias = data;
      if (data.length > 0 && this.novoProduto.categoriaId === 0) {
        this.novoProduto.categoriaId = data[0].id;
      }
    });
  }

  // Função da busca avançada
  aplicarFiltros(): void {
    this.produtoService.buscarProdutos(this.filtros).subscribe(); // Apenas dispara a busca
  }

  toggleDetalhes(produtoId: number): void {
    this.idProdutoExpandido = this.idProdutoExpandido === produtoId ? null : produtoId;
  }

  abrirFormulario(): void {
    this.exibirFormulario = true;
  }

  fecharFormulario(): void {
    this.exibirFormulario = false;
    this.novoProduto = {
      nome: '',
      descricao: '',
      quantidadeEstoque: 0,
      valorCompra: 0,
      valorVenda: 0,
      categoriaId: this.categorias.length > 0 ? this.categorias[0].id : 0
    };
  }

  salvarProduto(): void {
    this.produtoService.criarProduto(this.novoProduto).subscribe({
      next: () => {
        alert('Produto adicionado com sucesso!');
        this.fecharFormulario();
      },
      error: (err) => alert('Erro ao adicionar produto: ' + err.message)
    });
  }
}
