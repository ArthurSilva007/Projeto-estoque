package br.com.oficina.estoque_api.dto.venda;

import br.com.oficina.estoque_api.domain.ItemVenda;
import br.com.oficina.estoque_api.domain.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record VendaResponseDTO(
        Long id,
        String nomeCliente,
        LocalDateTime dataVenda,
        BigDecimal valorInstalacao,
        BigDecimal valorTotal,
        List<ItemVendaResponseDTO> itens
) {
    public VendaResponseDTO(Venda venda) {
        this(
                venda.getId(),
                venda.getCliente().getNome(),
                venda.getDataVenda(),
                venda.getValorInstalacao(),
                venda.getValorTotal(),
                venda.getItens().stream()
                        .map(ItemVendaResponseDTO::new)
                        .collect(Collectors.toList())
        );
    }
}

record ItemVendaResponseDTO(
        Long produtoId,
        String descricaoProduto,
        int quantidade,
        BigDecimal valorUnitario
) {
    public ItemVendaResponseDTO(ItemVenda item) {
        this(
                item.getProduto().getId(),
                item.getProduto().getDescricao(),
                item.getQuantidade(),
                item.getValorUnitario()
        );
    }
}
