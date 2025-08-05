package br.com.oficina.estoque_api.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        Long id,

        @NotBlank(message = "O nome não pode ser nulo ou vazio")
        String nome
) {
}
