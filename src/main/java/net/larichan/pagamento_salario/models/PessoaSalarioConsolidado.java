package net.larichan.pagamento_salario.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pessoa_salario_consolidado")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaSalarioConsolidado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salario_id_seq")
    @SequenceGenerator(name = "salario_id_seq", sequenceName = "salario_id_seq", allocationSize = 1)
    private Integer id;

    @JoinColumn(name = "pessoa_id")
    @ManyToOne
    private Pessoa pessoa;

    @Column(name = "nome_pessoa")
    private String nomePessoa;

    @Column(name = "nome_cargo")
    private String nomeCargo;

    private BigDecimal salario;
}
