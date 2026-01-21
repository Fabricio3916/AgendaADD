package add.agendaadd.dto;

import add.agendaadd.entity.Status;
import add.agendaadd.entity.Tecnico;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AtendimentoDTO(

        Long id,

        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        String clienteNome,

        @NotNull(message = "Técnico é obrigatório")
        Tecnico tecnico,

        @NotNull(message = "Status é obrigatório")
        Status status,

        @NotNull(message = "Problema de abertura de chamado é obrigatório")
        String problemaRelatado,

        String descricaoSolucao,

        LocalDateTime dataCriacao,

        LocalDateTime dataFinalizacao
) {}
