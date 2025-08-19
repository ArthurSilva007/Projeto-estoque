import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../navbar/navbar';
import { FormsModule } from '@angular/forms';
import { Categoria, CategoriaRequest, CategoriaService } from '../../services/categoria.service';
import { Produto, ProdutoRequest, ProdutoService } from '../../services/produto.service';

@Component({
  selector: 'app-estoque-produtos',
  standalone: true,
  imports: [CommonModule, NavbarComponent, FormsModule],
  templateUrl: './estoque-produtos.html',
  styleUrls: ['./estoque-produtos.scss']
})
export class EstoqueProdutosComponent implements OnInit {

  categorias: Categoria[] = [];

  // Propriedades para o formulário de adicionar categoria
  exibirFormularioAdd = false;
  nomeNovaCategoria = '';

  // Propriedades para edição de categoria
  editandoCategoriaId: number | null = null;
  nomeCategoriaEditada: string = '';

  // Propriedades para a janela de produtos
  categoriaSelecionada: Categoria | null = null;
  produtosDaCategoria: Produto[] = [];
  produtosFiltradosModal: Produto[] = [];
  termoBuscaModal: string = '';

  // Propriedades para edição de produtos
  editandoProdutoId: number | null = null;
  produtoEditado: any = null;

  // Propriedades para adicionar novo produto na janela
  adicionandoProduto = false;
  novoProduto: ProdutoRequest = this.resetarNovoProduto();

  constructor(
    private categoriaService: CategoriaService,
    private produtoService: ProdutoService
  ) { }

  ngOnInit(): void {
    this.carregarCategorias();
  }

  carregarCategorias(): void {
    this.categoriaService.getCategorias().subscribe(dados => {
      this.categorias = dados;
    });
  }

  // --- Funções da Categoria (Adicionar e Editar)---
  abrirFormularioAdd(): void {
    this.exibirFormularioAdd = true;
  }

  fecharFormularioAdd(): void {
    this.exibirFormularioAdd = false;
    this.nomeNovaCategoria = '';
  }

  salvarNovaCategoria(): void {
    if (!this.nomeNovaCategoria.trim()) return;
    const novaCategoria: CategoriaRequest = { nome: this.nomeNovaCategoria };
    this.categoriaService.criarCategoria(novaCategoria).subscribe(() => {
      this.fecharFormularioAdd();
      this.carregarCategorias();
    });
  }

  iniciarEdicao(categoria: Categoria): void {
    this.editandoCategoriaId = categoria.id;
    this.nomeCategoriaEditada = categoria.nome;
  }

  cancelarEdicao(): void {
    this.editandoCategoriaId = null;
    this.nomeCategoriaEditada = '';
  }

  salvarEdicao(categoria: Categoria): void {
    const categoriaAtualizada: CategoriaRequest = { nome: this.nomeCategoriaEditada };
    this.categoriaService.atualizarCategoria(categoria.id, categoriaAtualizada).subscribe(() => {
      this.cancelarEdicao();
      this.carregarCategorias();
    });
  }

  // --- Funções da Janela de Produtos ---
  mostrarProdutos(categoria: Categoria): void {
    if (this.editandoCategoriaId) return;
    this.categoriaSelecionada = categoria;
    this.produtoService.getProdutosPorCategoria(categoria.id).subscribe(produtos => {
      this.produtosDaCategoria = produtos;
      this.produtosFiltradosModal = produtos;
    });
  }

  fecharJanelaProdutos(): void {
    this.categoriaSelecionada = null;
    this.produtosDaCategoria = [];
    this.produtosFiltradosModal = [];
    this.cancelarEdicaoProduto();
    this.cancelarAdicaoProduto();
  }

  filtrarProdutosModal(): void {
    this.produtosFiltradosModal = this.produtosDaCategoria.filter(p =>
      p.nome.toLowerCase().includes(this.termoBuscaModal.toLowerCase())
    );
  }

  // --- Funções de Gestão de Produto na Janela ---
  iniciarEdicaoProduto(produto: Produto): void {
    this.editandoProdutoId = produto.id;
    this.produtoEditado = { ...produto };
  }

  cancelarEdicaoProduto(): void {
    this.editandoProdutoId = null;
    this.produtoEditado = null;
  }

  salvarEdicaoProduto(): void {
    if (!this.produtoEditado) return;
    const produtoRequest: ProdutoRequest = {
      nome: this.produtoEditado.nome,
      descricao: this.produtoEditado.descricao,
      quantidadeEstoque: this.produtoEditado.quantidadeEstoque,
      valorCompra: this.produtoEditado.valorCompra,
      valorVenda: this.produtoEditado.valorVenda,
      categoriaId: this.produtoEditado.categoria.id
    };
    this.produtoService.atualizarProduto(this.produtoEditado.id, produtoRequest).subscribe(() => {
      if (this.categoriaSelecionada) this.mostrarProdutos(this.categoriaSelecionada);
      this.cancelarEdicaoProduto();
    });
  }

  iniciarAdicaoProduto(): void {
    this.adicionandoProduto = true;
    this.novoProduto = this.resetarNovoProduto();
  }

  cancelarAdicaoProduto(): void {
    this.adicionandoProduto = false;
  }

  salvarNovoProduto(): void {
    if (!this.novoProduto.nome || !this.categoriaSelecionada) return;
    this.novoProduto.categoriaId = this.categoriaSelecionada.id;
    this.produtoService.criarProduto(this.novoProduto).subscribe(() => {
      this.mostrarProdutos(this.categoriaSelecionada!);
      this.cancelarAdicaoProduto();
    });
  }

  deletarProduto(produtoId: number): void {
    this.produtoService.deletarProduto(produtoId).subscribe(() => {
      if (this.categoriaSelecionada) {
        this.mostrarProdutos(this.categoriaSelecionada);
      }
    });
  }

  resetarNovoProduto(): ProdutoRequest {
    return { nome: '', descricao: '', quantidadeEstoque: 0, valorCompra: 0, valorVenda: 0, categoriaId: 0 };
  }
}
