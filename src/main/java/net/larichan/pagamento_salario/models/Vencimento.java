package net.larichan.pagamento_salario.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.larichan.pagamento_salario.enums.TipoVencimento;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vencimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ven_id_seq")
    @SequenceGenerator(name = "ven_id_seq", sequenceName = "ven_id_seq", allocationSize = 1)
    private Integer id;

    private String descricao;

    private BigDecimal valor;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoVencimento tipoVencimento;
}
