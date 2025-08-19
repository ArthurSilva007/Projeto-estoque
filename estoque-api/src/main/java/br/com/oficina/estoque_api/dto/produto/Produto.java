package br.com.oficina.estoque_api.dto.produto;

import br.com.oficina.estoque_api.domain.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    // CORREÇÃO: "nullable = false" estava dentro das aspas do 'name'
    @Column(name = "valor_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorCompra;

    @Column(name = "valor_venda", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
