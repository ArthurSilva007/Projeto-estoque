import { Routes } from '@angular/router';
import { EstoqueProdutosComponent } from './components/estoque-produtos/estoque-produtos';
import { NovaVendaComponent } from './components/nova-venda/nova-venda';
import { ProdutoListaComponent } from './components/produto-lista/produto-lista';
import { HistoriaVendasComponent } from './components/historia-vendas/historia-vendas';
import { DetalhesVendaComponent } from './components/detalhes-venda/detalhes-venda'; // <-- Importe



export const routes: Routes = [
  { path: 'estoque', component: EstoqueProdutosComponent },
  { path: 'nova-venda', component: NovaVendaComponent },
  { path: 'produtos', component: ProdutoListaComponent }, // <-- Adicione esta rota
  { path: '', redirectTo: '/estoque', pathMatch: 'full' },
  { path: 'historia-vendas', component: HistoriaVendasComponent },
  { path: 'detalhes-venda', component: DetalhesVendaComponent }// <-- Adicione

];
