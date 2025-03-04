package org.iesvdm.controlador;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}

	@GetMapping("/clientes/crear")
	public String crear(@ModelAttribute("cliente") Cliente cliente) {
		return "crear-cliente";
	}

	@PostMapping("/clientes/crear")
	public String guardar(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.crear(cliente);

		return "redirect:/clientes";
	}
	
	@GetMapping("/clientes/detalles/{id}")
	public String detalles(@PathVariable("id") int id, Model model) {
		Optional<ClienteDTO> clienteDTO = clienteService.detalles(id);
	}
	
	

}
