package br.com.oficina.estoque_api.repository;

import br.com.oficina.estoque_api.dto.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    List<Produto> findByCategoriaId(Long categoriaId);

}