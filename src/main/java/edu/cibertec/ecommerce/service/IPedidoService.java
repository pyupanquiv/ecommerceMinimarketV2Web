package edu.cibertec.ecommerce.service;

import java.util.List;
import java.util.Optional;

import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Usuario;

public interface IPedidoService {
	
	List<Pedido> findAll();
	Optional<Pedido> findById(Integer id);
	Pedido save (Pedido pedido);
	String generarNumeroPedido();
	List<Pedido> findByUsuario(Usuario usuario);
	
}
