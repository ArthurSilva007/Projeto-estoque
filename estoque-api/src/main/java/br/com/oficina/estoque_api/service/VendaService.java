package br.com.oficina.estoque_api.service;


import br.com.oficina.estoque_api.domain.*;
import br.com.oficina.estoque_api.dto.produto.Produto;
import br.com.oficina.estoque_api.dto.venda.VendaRequestDTO;
import br.com.oficina.estoque_api.dto.venda.VendaResponseDTO;
import br.com.oficina.estoque_api.exception.EstoqueInsuficienteException;
import br.com.oficina.estoque_api.exception.ResourceNotFoundException;
import br.com.oficina.estoque_api.repository.ClienteRepository;
import br.com.oficina.estoque_api.repository.ProdutoRepository;
import br.com.oficina.estoque_api.repository.VendaRepository;
import lombok.RequiredArgsConstructor; // <-- ADICIONE ESTE IMPORT
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // <-- ADICIONE ESTA ANOTAÇÃO
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final PdfService pdfService;

    @Transactional(readOnly = true)
    public byte[] gerarPdfVenda(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com o ID: " + id));
        return pdfService.gerarNotaVenda(venda);
    }


    @Transactional
    public VendaResponseDTO registrarVenda(VendaRequestDTO requestDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(requestDTO.nomeCliente());
        clienteRepository.save(cliente);

        Venda novaVenda = new Venda();
        novaVenda.setCliente(cliente);
        novaVenda.setDataVenda(LocalDateTime.now());
        novaVenda.setValorInstalacao(requestDTO.valorInstalacao());

        List<ItemVenda> itensVenda = new ArrayList<>();
        BigDecimal valorTotalProdutos = BigDecimal.ZERO;

        for (var itemDTO : requestDTO.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + itemDTO.produtoId()));

            if (produto.getQuantidadeEstoque() < itemDTO.quantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getDescricao());
            }

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemDTO.quantidade());
            produtoRepository.save(produto);

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDTO.quantidade());
            itemVenda.setValorUnitario(produto.getValorVenda());
            itemVenda.setVenda(novaVenda);
            itensVenda.add(itemVenda);

            valorTotalProdutos = valorTotalProdutos.add(
                    produto.getValorVenda().multiply(BigDecimal.valueOf(itemDTO.quantidade()))
            );
        }

        novaVenda.setItens(itensVenda);
        novaVenda.setValorTotal(valorTotalProdutos.add(requestDTO.valorInstalacao()));
        Venda vendaSalva = vendaRepository.save(novaVenda);

        return new VendaResponseDTO(vendaSalva);
    }
}

