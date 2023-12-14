package edu.cibertec.ecommerce.service;

import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.repository.IPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @InjectMocks
    PedidoServiceImpl pedidoService;

    @Mock
    IPedidoRepository pedidoRepository;

    Pedido pedido1;
    Pedido pedido2;

    @BeforeEach
    void setUp() {
        pedido1 = new Pedido();
        pedido1.setNumero("0000000001");

        pedido2 = new Pedido();
        pedido2.setNumero("0000000002");
    }

    @Test
    void save() {
        when(pedidoRepository.save(any())).thenReturn(pedido1);
        Pedido result = pedidoService.save(pedido1);
        assertEquals(pedido1, result);
    }

    @Test
    void findAll() {
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido1, pedido2));
        assertEquals(2, pedidoService.findAll().size());
    }

    @Test
    void generarNumeroPedido() {
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido1, pedido2));
        assertEquals("0000000003", pedidoService.generarNumeroPedido());
    }

    @Test
    void findByUsuario() {
        Usuario usuario = new Usuario();
        when(pedidoRepository.findByUsuario(any())).thenReturn(Arrays.asList(pedido1, pedido2));
        assertEquals(2, pedidoService.findByUsuario(usuario).size());
    }

    @Test
    void findById() {
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.of(pedido1));
        assertEquals(pedido1, pedidoService.findById(1).get());
    }
}