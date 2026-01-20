package add.agendaadd.controller;

import add.agendaadd.dto.ClienteDTO;
import add.agendaadd.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            Model model
    ) {
        model.addAttribute("clientes", clienteService.buscarPorNome(nome));
        model.addAttribute("nome", nome);
        return "cliente/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "cliente/form";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new ClienteDTO(null, "", "", ""));
        return "cliente/form";
    }


    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("cliente") ClienteDTO clienteDTO,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "cliente/form";
        }

        clienteService.salvar(clienteDTO);
        return "redirect:/clientes";
    }


}
