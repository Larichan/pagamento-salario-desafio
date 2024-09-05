package net.larichan.pagamento_salario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.larichan.pagamento_salario.models.Vencimento;

public interface VencimentoRepository extends JpaRepository<Vencimento, Integer> {

}
