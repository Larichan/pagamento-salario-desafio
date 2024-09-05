package net.larichan.pagamento_salario.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.larichan.pagamento_salario.models.Cargo;
import net.larichan.pagamento_salario.models.CargoVencimento;

public interface CargoVencimentoRepository extends JpaRepository<CargoVencimento, Integer> {
    List<CargoVencimento> findByCargo(Cargo cargo);
}
