package org.iesvdm.controlador;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class ClienteController {
	
	private ClienteService clienteService;

	@GetMapping("/clientes")
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);

		return "clientes";
	}

	@GetMapping("/clientes/crear")
	public String crear(@ModelAttribute("cliente") Cliente cliente) {
		return "crearCliente";
	}

	@PostMapping("/clientes/crear")
	public String guardar(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.crear(cliente);

		return "redirect:/clientes";
	}

	@GetMapping("/clientes/editar/{id}")
	public String editar(@PathVariable("id") int id, Model model) {
		Optional<Cliente> cliente = clienteService.find(id);
		if (cliente.isEmpty()) {
			return "redirect:/clientes";
		}
		model.addAttribute("cliente", cliente.get());
		return "editarCliente";
	}

	@PostMapping("/clientes/editar")
	public String actualizar(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.update(cliente);
		return "redirect:/clientes";
	}

	@PostMapping("/clientes/borrar/{id}")
	public String borrar(@PathVariable("id") int id) {
		clienteService.delete(id);
		return "redirect:/clientes";
	}

	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id ) {

		Optional<Cliente> cliente = clienteService.find(id);
		if (cliente.isEmpty()) {
			return "redirect:/clientes";
		}
		model.addAttribute("cliente", cliente.get());

		return "detallesCliente";

	}
}
