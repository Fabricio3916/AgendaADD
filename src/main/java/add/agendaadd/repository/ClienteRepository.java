package add.agendaadd.repository;

import add.agendaadd.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    List<Cliente> findByNomeContainingIgnoreCase(String nome);

}
