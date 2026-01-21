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

    @Column(name = "problema_relatado", length = 500, nullable = false)
    private String problemaRelatado;

    @Column(name = "descricao_solucao", length = 1000)
    private String descricaoSolucao;

    @Enumerated(EnumType.STRING)
    private Status  status;

    @Enumerated(EnumType.STRING)
    private Tecnico tecnico;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataFinalizacao;




}
