package org.iesvdm.dao;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {
	
	public List<Pedido> findByComercialID(int id);

}
