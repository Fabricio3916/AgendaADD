package add.agendaadd.service;

import add.agendaadd.dto.ClienteDTO;
import add.agendaadd.entity.Cliente;
import add.agendaadd.mapper.ClienteMapper;
import add.agendaadd.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        Cliente novoCliente = clienteMapper.toEntity(clienteDTO);
        clienteRepository.save(novoCliente);
        return clienteMapper.toDTO(novoCliente);
    }

    public List<ClienteDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDTO)
                .toList();
    }

    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return clienteMapper.toDTO(cliente);
    }

    public List<ClienteDTO> buscarPorNome(String nome) {

        List<Cliente> clientes;

        if (nome == null || nome.isBlank()) {
            clientes = clienteRepository.findAll();
        } else {
            clientes = clienteRepository.findByNomeContainingIgnoreCase(nome);
        }

        return clientes.stream()
                .map(clienteMapper::toDTO)
                .toList();
    }



}
