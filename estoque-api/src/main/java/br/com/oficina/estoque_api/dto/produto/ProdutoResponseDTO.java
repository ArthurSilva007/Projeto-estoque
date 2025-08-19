package br.com.oficina.estoque_api.dto.produto;


import br.com.oficina.estoque_api.dto.categoria.CategoriaDTO;
import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome, // ALTERADO de 'descricao' para 'nome'
        String descricao, // NOVO CAMPO
        Integer quantidadeEstoque,
        BigDecimal valorCompra,
        BigDecimal valorVenda,
        CategoriaDTO categoria
) {}