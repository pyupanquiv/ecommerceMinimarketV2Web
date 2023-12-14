package edu.cibertec.ecommerce.controller;

import edu.cibertec.ecommerce.model.Producto;
import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.service.IDetallePedidoService;
import edu.cibertec.ecommerce.service.IPedidoService;
import edu.cibertec.ecommerce.service.IUsuarioService;
import edu.cibertec.ecommerce.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Mock
    private ProductoService productoService;

    @Mock
    private IUsuarioService usuarioService;

    @Mock
    private IPedidoService pedidoService;

    @Mock
    private IDetallePedidoService detallePedidoService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Test
    void home() {
        when(session.getAttribute("idusuario")).thenReturn(1);
        when(productoService.findAll()).thenReturn(new ArrayList<>());

        String view = homeController.home(model, session);

        verify(session, times(2)).getAttribute("idusuario");
        verify(productoService, times(1)).findAll();
        assertEquals("usuario/home", view);
    }

    @Test
    void productoHome() {
        Integer id = 1;
        Producto producto = new Producto();
        producto.setId(id);
        when(productoService.get(id)).thenReturn(Optional.of(producto));
        when(session.getAttribute("idusuario")).thenReturn(1);

        String view = homeController.productoHome(id, model, session);

        verify(productoService, times(1)).get(id);
        assertEquals("usuario/productohome", view);
    }

    @Test
    void addCart() {
        Integer id = 1;
        Integer cantidad = 1;
        Producto producto = new Producto();
        producto.setId(id);
        when(productoService.get(id)).thenReturn(Optional.of(producto));
        when(session.getAttribute("idusuario")).thenReturn(1);

        String view = homeController.addCart(id, cantidad, model, session);

        verify(productoService, times(1)).get(id);
        assertEquals("usuario/carrito", view);
    }

    @Test
    void deleteProductoCarrito() {
        Integer id = 1;
        String view = homeController.deleteProductoCarrito(id, model);
        assertEquals("usuario/carrito", view);
    }

    @Test
    void getCart() {
        when(session.getAttribute("idusuario")).thenReturn(1);

        String view = homeController.getCart(model, session);

        verify(session, times(1)).getAttribute("idusuario");
        assertEquals("/usuario/carrito", view);
    }

    @Test
    void pedidos() {
        when(session.getAttribute("idusuario")).thenReturn(1);
        when(usuarioService.findByid(1)).thenReturn(Optional.of(new Usuario()));

        String view = homeController.pedidos(model, session);

        verify(session, times(2)).getAttribute("idusuario");
        verify(usuarioService, times(1)).findByid(1);
        assertEquals("usuario/resumenpedido", view);
    }

    @Test
    void savePedido() {
        when(session.getAttribute("idusuario")).thenReturn(1);
        when(usuarioService.findByid(1)).thenReturn(Optional.of(new Usuario()));

        String view = homeController.savePedido(session);

        verify(session, times(1)).getAttribute("idusuario");
        verify(usuarioService, times(1)).findByid(1);
        assertEquals("redirect:/", view);
    }

    @Test
    void buscarProducto() {
        String nombre = "test";
        List<Producto> productos = new ArrayList<>();
        Producto producto = new Producto();
        producto.setNombre(nombre);
        productos.add(producto);
        when(productoService.findAll()).thenReturn(productos);

        String view = homeController.buscarProducto(nombre, model);

        verify(productoService, times(1)).findAll();
        assertEquals("usuario/home", view);
    }
}