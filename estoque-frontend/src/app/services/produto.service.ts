import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';

export interface ProdutoRequest {
  nome: string;
  descricao: string;
  quantidadeEstoque: number;
  valorCompra: number;
  valorVenda: number;
  categoriaId: number;
}

export interface Produto {
  id: number;
  nome: string;
  descricao: string;
  quantidadeEstoque: number;
  valorCompra: number;
  valorVenda: number;
  categoria: {
    id: number;
    nome: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private apiUrl = 'http://localhost:8080/api/produtos';
  private produtosSubject = new BehaviorSubject<Produto[]>([]);

  constructor(private http: HttpClient) {
    this.buscarProdutosIniciais();
  }

  get produtos$(): Observable<Produto[]> {
    return this.produtosSubject.asObservable();
  }

  private buscarProdutosIniciais(): void {
    this.http.get<Produto[]>(this.apiUrl).subscribe(produtos => {
      this.produtosSubject.next(produtos);
    });
  }

  criarProduto(produto: ProdutoRequest): Observable<Produto> {
    return this.http.post<Produto>(this.apiUrl, produto).pipe(
      tap(() => this.buscarProdutosIniciais())
    );
  }

  atualizarProduto(id: number, produto: ProdutoRequest): Observable<Produto> {
    return this.http.put<Produto>(`${this.apiUrl}/${id}`, produto).pipe(
      tap(() => this.buscarProdutosIniciais())
    );
  }

  // NOVO MÉTODO PARA DELETAR UM PRODUTO
  deletarProduto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => this.buscarProdutosIniciais())
    );
  }

  getProdutosPorCategoria(categoriaId: number): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/categoria/${categoriaId}`);
  }

  buscarProdutos(filtros: any): Observable<Produto[]> {
    let params = new HttpParams();
    if (filtros.nome) params = params.append('nome', filtros.nome);
    if (filtros.categoriaId) params = params.append('categoriaId', filtros.categoriaId);
    if (filtros.quantidade != null) params = params.append('quantidade', filtros.quantidade);

    return this.http.get<Produto[]>(`${this.apiUrl}/buscar`, { params }).pipe(
      tap(produtos => this.produtosSubject.next(produtos))
    );
  }
}
