package add.agendaadd.mapper;

import add.agendaadd.dto.ClienteDTO;
import add.agendaadd.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getAtendente()
        );

    }

    public Cliente toEntity(ClienteDTO clienteDTO) {

        return new Cliente(
                clienteDTO.id(),
                clienteDTO.nome(),
                clienteDTO.telefone(),
                clienteDTO.atendente()
        );

    }


}
