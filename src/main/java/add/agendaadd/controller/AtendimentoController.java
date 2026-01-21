package add.agendaadd.controller;

import add.agendaadd.dto.AtendimentoDTO;
import add.agendaadd.entity.Status;
import add.agendaadd.entity.Tecnico;
import add.agendaadd.exceptions.RegraNegocioException;
import add.agendaadd.service.AtendimentoService;
import add.agendaadd.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @GetMapping
    public String listarAtendimentos(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataFim,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Page<AtendimentoDTO> atendimentos =
                atendimentoService.buscarComFiltro(clienteId, dataInicio, dataFim, page);

        model.addAttribute("atendimentos", atendimentos); // ← Page, NÃO List

        model.addAttribute("clienteId", clienteId);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        model.addAttribute("clientes", clienteService.listar());

        return "atendimento/lista";
    }

    private void carregarCombos(Model model) {
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("tecnicos", Tecnico.values());
        model.addAttribute("statusList", Status.values());
    }
}
