package br.com.oficina.estoque_api.dto.produto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank String nome, // ALTERADO de 'descricao' para 'nome'
        String descricao,      // NOVO CAMPO
        @NotNull @PositiveOrZero Integer quantidadeEstoque,
        @NotNull @Positive BigDecimal valorCompra,
        @NotNull @Positive BigDecimal valorVenda,
        @NotNull Long categoriaId
) {}