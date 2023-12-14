package edu.cibertec.ecommerce.controller;

import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Producto;
import edu.cibertec.ecommerce.service.IPedidoService;
import edu.cibertec.ecommerce.service.IUsuarioService;
import edu.cibertec.ecommerce.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdministradorControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private IUsuarioService usuarioService;

    @Mock
    private IPedidoService pedidoService;

    private AdministradorController administradorController;

    @BeforeEach
    void setUp() {
        administradorController = new AdministradorController(productoService, usuarioService, pedidoService);
    }

    @Test
    void home() {
        Producto producto = new Producto();
        when(productoService.findAll()).thenReturn(Collections.singletonList(producto));

        Model model = new BindingAwareModelMap();
        String view = administradorController.home(model);

        assertEquals("administrador/home", view);
        assertEquals(Collections.singletonList(producto), model.getAttribute("productos"));
    }

    @Test
    void usuarios() {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());

        Model model = new BindingAwareModelMap();
        String view = administradorController.usuarios(model);

        assertEquals("administrador/usuarios", view);
        assertEquals(Collections.emptyList(), model.getAttribute("usuarios"));
    }

    @Test
    void pedidos() {
        when(pedidoService.findAll()).thenReturn(Collections.emptyList());

        Model model = new BindingAwareModelMap();
        String view = administradorController.Pedidos(model);

        assertEquals("administrador/pedidos", view);
        assertEquals(Collections.emptyList(), model.getAttribute("pedidos"));
    }

    @Test
    void detalle() {
        Integer id = 1;
        Pedido pedido = new Pedido();
        when(pedidoService.findById(id)).thenReturn(java.util.Optional.of(pedido));

        Model model = new BindingAwareModelMap();
        String view = administradorController.detalle(model, id);

        assertEquals("administrador/detallepedido", view);
        assertEquals(pedido.getDetalle(), model.getAttribute("detalles"));
    }
}