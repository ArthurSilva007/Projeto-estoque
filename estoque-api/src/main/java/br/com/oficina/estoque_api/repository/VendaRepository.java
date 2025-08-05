package br.com.oficina.estoque_api.repository;

import br.com.oficina.estoque_api.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {


}
