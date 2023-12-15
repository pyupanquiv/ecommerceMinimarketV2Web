package edu.cibertec.ecommerce.controller;

import java.util.List;

import edu.cibertec.ecommerce.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Producto;
import edu.cibertec.ecommerce.service.IPedidoService;
import edu.cibertec.ecommerce.service.IUsuarioService;
import edu.cibertec.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	private Logger logg= LoggerFactory.getLogger(AdministradorController.class);

	AdministradorController(ProductoService productoService, IUsuarioService usuarioService, IPedidoService pedidoService) {
		this.productoService = productoService;
		this.usuarioService = usuarioService;
		this.pedidoService = pedidoService;
	}
	
	@GetMapping("")
	public String home(Model model) {
		
		List<Producto> productos = productoService.findAll();
		model.addAttribute("productos",productos);
		
		return "administrador/home";
	}
	
	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		
		model.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}
	
	@GetMapping("/pedidos")
	public String Pedidos(Model model) {

		model.addAttribute("pedidos", pedidoService.findAll());
		return "administrador/pedidos";
	}
	
	@GetMapping("/detalle/{id}")	
	public String detalle(Model model, @PathVariable Integer id) {
		logg.info("id del Pedido {}", id);
		Pedido pedido = pedidoService.findById(id).get();

		model.addAttribute("pedido", pedido);
		model.addAttribute("detalles", pedido.getDetalle());
		return "administrador/detallepedido";
	}
	
}
