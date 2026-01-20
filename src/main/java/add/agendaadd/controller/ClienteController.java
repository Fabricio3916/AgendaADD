package add.agendaadd.controller;

import add.agendaadd.dto.ClienteDTO;
import add.agendaadd.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // =========================
    // LISTAGEM
    // =========================
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listar());
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
    public String salvar(@ModelAttribute ClienteDTO clienteDTO) {
        clienteService.salvar(clienteDTO);
        return "redirect:/clientes";
    }


}
