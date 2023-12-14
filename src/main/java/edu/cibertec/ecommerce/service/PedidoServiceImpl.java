package edu.cibertec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.repository.IPedidoRepository;

@Service
public class PedidoServiceImpl implements IPedidoService{

	@Autowired
	private IPedidoRepository pedidoRepository;
	
	@Override
	public Pedido save(Pedido pedido) {
		// TODO Auto-generated method stub
		return pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> findAll() {
		// TODO Auto-generated method stub
		return pedidoRepository.findAll();
	}
	
	public String generarNumeroPedido() {
		
		int numero=0;
		String numeroConcatenado = "";
		
		List<Pedido> pedidos = findAll();
		
		List<Integer> numeros = new ArrayList<Integer>();
		
		pedidos.stream().forEach(o -> numeros.add( Integer.parseInt(o.getNumero())));
		
		if (pedidos.isEmpty()) {
			numero = 1;
		}else {
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}
		
		if (numero < 10) {
			numeroConcatenado="000000000" + String.valueOf(numero);
		}else if(numero < 100) {
			numeroConcatenado="00000000" + String.valueOf(numero);
		}else if(numero < 1000) {
			numeroConcatenado="0000000" + String.valueOf(numero);
		}
		return numeroConcatenado;
	}

	@Override
	public List<Pedido> findByUsuario(Usuario usuario) {
		
		return pedidoRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Pedido> findById(Integer id) {
		
		return pedidoRepository.findById(id);
	}

}
