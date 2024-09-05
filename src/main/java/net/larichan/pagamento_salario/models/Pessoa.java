package net.larichan.pagamento_salario.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_id_seq")
    @SequenceGenerator(name = "pessoa_id_seq", sequenceName = "pessoa_id_seq", allocationSize = 1)
    private Integer id;

    private String nome;
    private String cidade;
    private String email;
    private String cep;
    private String endereco;
    private String pais;
    private String usuario;
    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @JoinColumn(name = "cargo_id")
    @ManyToOne
    private Cargo cargo;
}
