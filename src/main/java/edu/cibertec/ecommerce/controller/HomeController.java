package edu.cibertec.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cibertec.ecommerce.model.DetallePedido;
import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Producto;
import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.service.IDetallePedidoService;
import edu.cibertec.ecommerce.service.IPedidoService;
import edu.cibertec.ecommerce.service.IUsuarioService;
import edu.cibertec.ecommerce.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger log= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	@Autowired
	private IDetallePedidoService detallePedidoService;
	
	//para almacenar los detalles de la orden
	List<DetallePedido> detalles= new ArrayList<DetallePedido>();
	
	//Detalle del pedido
	Pedido pedido = new Pedido();
	
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		
		log.info("Sesion del Usuario: {}", session.getAttribute("idusuario"));
	
		model.addAttribute("productos",productoService.findAll());
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "usuario/home";
	}
	
	@GetMapping("/productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model, HttpSession session) {
		log.info("id PRODUCTO enviado como parametro {}", id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));	
		
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model, HttpSession session) {
		
		DetallePedido detallePedido = new DetallePedido();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Optional<Producto> optionalProducto = productoService.get(id);
		log.info("Producto añadido: {}", optionalProducto.get());
		log.info("Cantidad {}", cantidad);
		producto = optionalProducto.get();
		
		detallePedido.setCantidad(cantidad);
		detallePedido.setPrecio(producto.getPrecio());
		detallePedido.setNombre(producto.getNombre());
		detallePedido.setTotal(producto.getPrecio()*cantidad);
		detallePedido.setProducto(producto);
		
		
		//validar para que producto no se agregue 2 veces
		
		Integer idProducto=producto.getId();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		if (!ingresado) {
			detalles.add(detallePedido);
		}
		
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		pedido.setTotal(sumaTotal);
		model.addAttribute("cart",detalles);
		model.addAttribute("pedido",pedido);
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));		
		
		return "usuario/carrito";
	}
	
	@GetMapping("delete/cart/{id}")	
	public String deleteProductoCarrito(@PathVariable Integer id, Model model) {
		
		//Lista nueva de productos
		List<DetallePedido> pedidoNueva = new ArrayList<DetallePedido>();
		
		//lista menos las que eliminamos con el ID
		for (DetallePedido detallePedido: detalles) {
			if(detallePedido.getProducto().getId()!=id) {
				pedidoNueva.add(detallePedido);
			}
		}
		
		//agregar la nueva lista con los productos restantes.
		detalles= pedidoNueva;
		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		pedido.setTotal(sumaTotal);
		model.addAttribute("cart",detalles);
		model.addAttribute("pedido",pedido);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model model, HttpSession session) {
		
		model.addAttribute("cart",detalles);
		model.addAttribute("pedido",pedido);
		
		//sesion
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "/usuario/carrito";
	}
	
	@GetMapping("/pedidos")
	public String pedidos(Model model,HttpSession session) {
		
		Usuario usuario = usuarioService.findByid(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		
		model.addAttribute("cart",detalles);
		model.addAttribute("pedido",pedido);
		model.addAttribute("usuario", usuario);

		//sesion
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		return "usuario/resumenpedido";
	}
	
	// Guardar Pedido (BOTOIN GENERAR)
	
	@GetMapping("/savePedido")
	public String savePedido(HttpSession session) {
		Date fechaActual = new Date();
		pedido.setFechaCreacion(fechaActual);
		pedido.setNumero(pedidoService.generarNumeroPedido());
		log.info("PEDIDO {}", pedido);
		//Usuario que gnera la orden
		Usuario usuario = usuarioService.findByid(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		pedido.setUsuario(usuario);

		pedidoService.save(pedido);
		
		// Guardar los detalles
		
		for(DetallePedido dp:detalles ) {
			dp.setPedido(pedido);
			detallePedidoService.save(dp);
		}
		
		//limpiar lista y pedidos
		
		pedido = new Pedido();
		detalles.clear();
		
		return "redirect:/";
	}
	
	//busqueda de productos
	@PostMapping("/buscar")
	public String buscarProducto(@RequestParam String nombre, Model model) {
		log.info("Nombre del Producto: {}", nombre);
		List<Producto> productos = productoService.findAll().stream().filter( p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		model.addAttribute("productos",productos);
		return "usuario/home";
	}
	
}
