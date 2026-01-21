package add.agendaadd.mapper;

import add.agendaadd.dto.AtendimentoDTO;
import add.agendaadd.entity.Atendimento;
import org.springframework.stereotype.Component;

@Component
public class AtendimentoMapper {

    public AtendimentoDTO toDTO(Atendimento atendimento) {

        return new AtendimentoDTO(
                atendimento.getId(),
                atendimento.getCliente().getId(),
                atendimento.getCliente().getNome(),
                atendimento.getTecnico(),
                atendimento.getStatus(),
                atendimento.getProblemaRelatado(),
                atendimento.getDescricaoSolucao(),
                atendimento.getDataCriacao(),
                atendimento.getDataFinalizacao()
        );

    }


}
