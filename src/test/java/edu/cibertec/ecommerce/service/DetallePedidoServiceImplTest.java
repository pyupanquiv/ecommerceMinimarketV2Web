package edu.cibertec.ecommerce.service;

import edu.cibertec.ecommerce.model.DetallePedido;
import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Producto;
import edu.cibertec.ecommerce.repository.IDetallePedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetallePedidoServiceImplTest {

    @Mock
    private IDetallePedidoRepository detallePedidoRepository;

    @InjectMocks
    private DetallePedidoServiceImpl detallePedidoServiceImpl;

    @BeforeEach
    void setUp() {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setNombre("Test Product");
        detallePedido.setCantidad(2);
        detallePedido.setPrecio(100);
        detallePedido.setTotal(200);

        Pedido pedido = new Pedido();
        pedido.setNumero("123456");
        pedido.setFechaCreacion(new Date());
        pedido.setFechaRecibida(new Date());
        pedido.setTotal(200);

        Producto producto = new Producto();
        producto.setNombre("Test Product");
        producto.setDescripcion("Test Description");
        producto.setImagen("Test Image");
        producto.setPrecio(100);
        producto.setCantidad(2);

        detallePedido.setPedido(pedido);
        detallePedido.setProducto(producto);
    }

    @Test
    void save() {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setNombre("Test Product");
        detallePedido.setCantidad(2);
        detallePedido.setPrecio(100);
        detallePedido.setTotal(200);
        Pedido pedido = new Pedido();
        Producto producto = new Producto();
        detallePedido.setPedido(pedido);
        detallePedido.setProducto(producto);

        when(detallePedidoRepository.save(detallePedido)).thenReturn(detallePedido);

        DetallePedido savedDetallePedido = detallePedidoServiceImpl.save(detallePedido);

        assertEquals(detallePedido, savedDetallePedido);
    }
}