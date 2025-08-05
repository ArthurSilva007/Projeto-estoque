package br.com.oficina.estoque_api.repository;

import br.com.oficina.estoque_api.dto.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
