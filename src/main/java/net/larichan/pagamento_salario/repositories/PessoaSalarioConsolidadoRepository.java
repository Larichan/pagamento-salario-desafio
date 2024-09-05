package net.larichan.pagamento_salario.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.larichan.pagamento_salario.models.PessoaSalarioConsolidado;

public interface PessoaSalarioConsolidadoRepository extends JpaRepository<PessoaSalarioConsolidado, Integer> {

    @Query(value = "SELECT distinct on(pessoa_id) * from pessoa_salario_consolidado order by pessoa_id, id DESC", nativeQuery = true)
    List<PessoaSalarioConsolidado> buscaUltimoSalarioConsolidadoDeCadaPessoa();
}
