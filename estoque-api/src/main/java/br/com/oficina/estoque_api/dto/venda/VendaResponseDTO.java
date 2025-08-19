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
        String telefoneCliente,
        String cnpjCliente,
        LocalDateTime dataVenda,
        String atendente,
        String carro,
        String descricaoDefeito,
        BigDecimal valorInstalacao,
        BigDecimal valorTotal,
        List<ItemVendaResponseDTO> itens
) {

    public VendaResponseDTO(Venda venda) {
        this(
                venda.getId(),
                venda.getCliente().getNome(),
                venda.getCliente().getTelefone(),
                venda.getCliente().getCnpj(),
                venda.getDataVenda(),
                venda.getAtendente(),
                venda.getCarro(),
                venda.getDescricaoDefeito(),
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