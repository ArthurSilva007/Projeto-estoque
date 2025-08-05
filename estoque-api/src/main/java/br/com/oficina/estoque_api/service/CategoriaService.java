package br.com.oficina.estoque_api.service;

import br.com.oficina.estoque_api.domain.Categoria;
import br.com.oficina.estoque_api.dto.categoria.CategoriaDTO;
import br.com.oficina.estoque_api.exception.ResourceNotFoundException;
import br.com.oficina.estoque_api.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public List<CategoriaDTO> listarTodas() {
        List<Categoria> categorias = repository.findAll();
        return categorias.stream()
                .map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNome()))
                .collect(Collectors.toList());
    }
    public CategoriaDTO criar(CategoriaDTO categoriaDTO) {

        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(categoriaDTO.nome());

        Categoria categoriaSalva = repository.save(novaCategoria);

        return new CategoriaDTO(categoriaSalva.getId(), categoriaSalva.getNome());
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));
        return toDTO(categoria);
    }

    public CategoriaDTO atualizar (Long id, CategoriaDTO categoriaDTO) {
        Categoria categoriaExistence = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impossivel atualizar. Categoria não encontrada com o ID: " + id));
        categoriaExistence.setNome(categoriaDTO.nome());
        Categoria categoriaAtualizado = repository.save(categoriaExistence);
        return toDTO(categoriaAtualizado);
    }
    public void deletar(Long id) {
        if (repository.existsById(id)) {
            throw new ResourceNotFoundException("Impossivel deletar. Categoria não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(categoria.getId(), categoria.getNome());
    }

    private Categoria toEntity(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.nome());
        return categoria;
    }

}
