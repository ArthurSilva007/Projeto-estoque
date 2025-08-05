package br.com.oficina.estoque_api.dto.venda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record VendaRequestDTO(
        @NotBlank String nomeCliente,
        @NotNull BigDecimal valorInstalacao,
        @NotEmpty List<ItemVendaRequestDTO> itens
) {
}
