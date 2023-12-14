package edu.cibertec.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cibertec.ecommerce.model.DetallePedido;
import edu.cibertec.ecommerce.repository.IDetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements IDetallePedidoService{

	@Autowired
	private IDetallePedidoRepository detallePedidoRepository;
	
	
	@Override
	public DetallePedido save(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}
	

}
