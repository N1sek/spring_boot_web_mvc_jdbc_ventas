package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.modelo.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	private ClienteDAO clienteDAO;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteService(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	public List<Cliente> listAll() {
		
		return clienteDAO.getAll();
		
	}

	public void crear(Cliente cliente) {

		clienteDAO.create(cliente);
	}


	public Optional<Cliente> find(int id) {
		return clienteDAO.find(id);
	}

	public void update(Cliente cliente) {
		clienteDAO.update(cliente);
	}

	public void delete(int id) {
		clienteDAO.delete(id);
	}
}
