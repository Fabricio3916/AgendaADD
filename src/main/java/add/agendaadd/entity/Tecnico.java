package add.agendaadd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tecnico {

    FABRICIO("Fabricio"),
    DAVI("Davi"),
    SYLVIO("Sylvio"),
    DANIEL("Daniel");

    private final String nome;

}
