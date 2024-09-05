package net.larichan.pagamento_salario.models;

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
@Table(name = "cargo_vencimento")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargoVencimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_ven_id_seq")
    @SequenceGenerator(name = "cargo_ven_id_seq", sequenceName = "cargo_ven_id_seq", allocationSize = 1)
    private Integer id;

    @JoinColumn(name = "cargo_id")
    @ManyToOne
    private Cargo cargo;
    
    @JoinColumn(name = "vencimento_id")
    @ManyToOne
    private Vencimento vencimento;
}
