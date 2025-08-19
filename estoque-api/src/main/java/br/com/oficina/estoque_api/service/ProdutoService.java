package br.com.oficina.estoque_api.service;

import br.com.oficina.estoque_api.domain.Categoria;
import br.com.oficina.estoque_api.dto.categoria.CategoriaDTO;
import br.com.oficina.estoque_api.dto.produto.Produto;
import br.com.oficina.estoque_api.dto.produto.ProdutoRequestDTO;
import br.com.oficina.estoque_api.dto.produto.ProdutoResponseDTO;
import br.com.oficina.estoque_api.exception.ResourceNotFoundException;
import br.com.oficina.estoque_api.repository.CategoriaRepository;
import br.com.oficina.estoque_api.repository.ProdutoRepository;
import br.com.oficina.estoque_api.repository.specification.ProdutoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscar(String nome, Long categoriaId, Integer quantidade) {
        Specification<Produto> spec = ProdutoSpecification.buscarComFiltros(nome, categoriaId, quantidade);
        return produtoRepository.findAll(spec).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarPorCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO requestDTO) {
        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + requestDTO.categoriaId()));

        Produto novoProduto = new Produto();
        mapearDtoParaEntidade(novoProduto, requestDTO, categoria);

        Produto produtoSalvo = produtoRepository.save(novoProduto);
        return toResponseDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO requestDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impossível atualizar. Produto não encontrado com o ID: " + id));

        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Impossível atualizar. Categoria não encontrada com o ID: " + requestDTO.categoriaId()));

        mapearDtoParaEntidade(produtoExistente, requestDTO, categoria);

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return toResponseDTO(produtoAtualizado);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
        return toResponseDTO(produto);
    }

    @Transactional
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossível deletar. Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    private void mapearDtoParaEntidade(Produto produto, ProdutoRequestDTO dto, Categoria categoria) {
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());
        produto.setValorCompra(dto.valorCompra());
        produto.setValorVenda(dto.valorVenda());
        produto.setCategoria(categoria);
    }

    // Converte uma entidade Produto para um DTO de resposta
    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        CategoriaDTO categoriaDTO = new CategoriaDTO(
                produto.getCategoria().getId(),
                produto.getCategoria().getNome()
        );
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getQuantidadeEstoque(),
                produto.getValorCompra(),
                produto.getValorVenda(),
                categoriaDTO
        );
    }
}