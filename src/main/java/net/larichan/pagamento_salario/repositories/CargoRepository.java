package net.larichan.pagamento_salario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.larichan.pagamento_salario.models.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Integer>{

}
