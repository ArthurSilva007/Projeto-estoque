package br.com.oficina.estoque_api.repository.specification;;

import br.com.oficina.estoque_api.dto.produto.Produto;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProdutoSpecification {
    public static Specification<Produto> buscarComFiltros(String nome, Long categoriaId, Integer quantidade) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null && !nome.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            if (categoriaId != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoria").get("id"), categoriaId));
            }
            if (quantidade != null) {
                predicates.add(criteriaBuilder.equal(root.get("quantidadeEstoque"), quantidade));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}