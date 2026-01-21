package add.agendaadd.controller;

import add.agendaadd.dto.AtendimentoDTO;
import add.agendaadd.entity.Status;
import add.agendaadd.entity.Tecnico;
import add.agendaadd.exceptions.RegraNegocioException;
import add.agendaadd.service.AtendimentoService;
import add.agendaadd.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private final AtendimentoService atendimentoService;
    private final ClienteService clienteService;

    public AtendimentoController(AtendimentoService atendimentoService,
                                 ClienteService clienteService) {
        this.atendimentoService = atendimentoService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("atendimentos", atendimentoService.listar());
        return "atendimento/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("atendimento",
                new AtendimentoDTO(null, null, null, null, Status.ABERTO, "", null, null, null)
        );

        carregarCombos(model);
        return "atendimento/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("atendimento", atendimentoService.buscarPorId(id));
        carregarCombos(model);
        return "atendimento/form";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        model.addAttribute("atendimento", atendimentoService.buscarPorId(id));
        return "atendimento/detalhes";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("atendimento") AtendimentoDTO dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            carregarCombos(model);
            return "atendimento/form";
        }

        try {
            atendimentoService.salvar(dto);
        } catch (RegraNegocioException e) {

            // ERRO DE NEGÓCIO VIRA ERRO DE FORMULÁRIO
            result.rejectValue(
                    "descricaoSolucao",
                    "erro.regra",
                    e.getMessage()
            );

            carregarCombos(model);
            return "atendimento/form";
        }

        return "redirect:/atendimentos";
    }

    private void carregarCombos(Model model) {
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("tecnicos", Tecnico.values());
        model.addAttribute("statusList", Status.values());
    }
}
