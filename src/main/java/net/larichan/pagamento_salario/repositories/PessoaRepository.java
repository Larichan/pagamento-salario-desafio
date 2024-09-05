package net.larichan.pagamento_salario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.larichan.pagamento_salario.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
