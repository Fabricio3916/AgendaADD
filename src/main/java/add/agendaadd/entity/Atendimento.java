package add.agendaadd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_atendimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status  status;

    @Enumerated(EnumType.STRING)
    private Tecnico tecnico;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataFinalizacao;




}
