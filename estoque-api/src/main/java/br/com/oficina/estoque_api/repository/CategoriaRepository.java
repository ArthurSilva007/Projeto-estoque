package br.com.oficina.estoque_api.repository;

import br.com.oficina.estoque_api.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
