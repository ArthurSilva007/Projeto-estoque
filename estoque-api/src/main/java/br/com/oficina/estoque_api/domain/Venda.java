package br.com.oficina.estoque_api.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendas")
@Data
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_venda", nullable = false)
    private LocalDateTime dataVenda;

    @Column(name = "valor_instalacao", precision = 10, scale = 2)
    private BigDecimal valorInstalacao;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    // --- Relacionamentos ---
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Uma venda tem uma lista de itens
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens;
    @Column(name = "atendente")
    private String atendente;

    @Column(name = "carro")
    private String carro;

    @Column(name = "descricao_defeito", columnDefinition = "TEXT")
    private String descricaoDefeito;

}
