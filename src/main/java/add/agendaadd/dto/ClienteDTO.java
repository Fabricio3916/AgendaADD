package add.agendaadd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteDTO(

        Long id,

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
        String nome,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "O nome do atendente é obrigatório")
        String atendente
) {}
