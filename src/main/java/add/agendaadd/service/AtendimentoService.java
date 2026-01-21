package add.agendaadd.service;

import add.agendaadd.dto.AtendimentoDTO;
import add.agendaadd.entity.Atendimento;
import add.agendaadd.entity.Cliente;
import add.agendaadd.entity.Status;
import add.agendaadd.mapper.AtendimentoMapper;
import add.agendaadd.repository.AtendimentoRepository;
import add.agendaadd.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendimentoService {


    private final AtendimentoRepository atendimentoRepository;
    private final AtendimentoMapper atendimentoMapper;
    private final ClienteRepository clienteRepository;

    public AtendimentoService(AtendimentoRepository atendimentoRepository, AtendimentoMapper atendimentoMapper, ClienteRepository clienteRepository) {
        this.atendimentoRepository = atendimentoRepository;
        this.atendimentoMapper = atendimentoMapper;
        this.clienteRepository = clienteRepository;
    }

    public List<AtendimentoDTO> listar() {
        return atendimentoRepository.findAll()
                .stream()
                .map(atendimentoMapper::toDTO)
                .toList();
    }

    public AtendimentoDTO buscarPorId(Long id) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));
        return atendimentoMapper.toDTO(atendimento);
    }

    @Transactional
    public void salvar(AtendimentoDTO dto) {

        Atendimento atendimento;

        // NOVO ou EDIÇÃO
        if (dto.id() != null) {
            atendimento = atendimentoRepository.findById(dto.id())
                    .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));
        } else {
            atendimento = new Atendimento();
            atendimento.setStatus(Status.ABERTO);
        }

        // REGRA: só finaliza com solução
        if (dto.status() == Status.FINALIZADO &&
                (dto.descricaoSolucao() == null || dto.descricaoSolucao().isBlank())) {
            throw new RuntimeException(
                    "Para finalizar o atendimento, descreva a solução"
            );
        }

        // CLIENTE
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        atendimento.setCliente(cliente);

        // CAMPOS PRINCIPAIS
        atendimento.setTecnico(dto.tecnico());
        atendimento.setProblemaRelatado(dto.problemaRelatado());
        atendimento.setStatus(dto.status());

        // SOLUÇÃO (pode ser nula)
        atendimento.setDescricaoSolucao(dto.descricaoSolucao());

        // DATA DE FINALIZAÇÃO
        if (dto.status() == Status.FINALIZADO &&
                atendimento.getDataFinalizacao() == null) {
            atendimento.setDataFinalizacao(LocalDateTime.now());
        }

        atendimentoRepository.save(atendimento);
    }

}
