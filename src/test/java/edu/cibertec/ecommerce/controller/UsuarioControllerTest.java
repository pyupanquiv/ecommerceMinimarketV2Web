package edu.cibertec.ecommerce.controller;

import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.service.IPedidoService;
import edu.cibertec.ecommerce.service.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @InjectMocks
    UsuarioController usuarioController;

    @Mock
    IUsuarioService usuarioService;

    @Mock
    IPedidoService pedidoService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(get("/usuario/registro"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuario/registro"));
    }

    @Test
    void save() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setTipo("USER");
        usuario.setPassword("password");
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuario/save")
                        .param("id", "1")
                        .param("tipo", "USER")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(usuarioService, times(1)).save(any(Usuario.class));
    }

    @Test
    void acceder() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setTipo("USER");
        when(usuarioService.findByid(anyInt())).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuario/acceder").sessionAttr("idusuario", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(usuarioService, times(1)).findByid(anyInt());
    }

    @Test
    void obtenerCompras() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioService.findByid(anyInt())).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuario/compras").sessionAttr("idusuario", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuario/compras"));
    }

    @Test
    void detalleCompra() throws Exception {
        Pedido pedido = new Pedido();
        when(pedidoService.findById(anyInt())).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/usuario/detalle/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuario/detallecompra"));

        verify(pedidoService, times(1)).findById(anyInt());
    }

    @Test
    void cerrarSesion() throws Exception {
        mockMvc.perform(get("/usuario/cerrar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}