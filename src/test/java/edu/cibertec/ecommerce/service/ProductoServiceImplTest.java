package edu.cibertec.ecommerce.service;

import edu.cibertec.ecommerce.model.Producto;
import edu.cibertec.ecommerce.repository.IProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @InjectMocks
    ProductoServiceImpl productoService;

    @Mock
    IProductoRepository productoRepository;

    Producto producto;

    @BeforeEach
    void setUp() throws Exception {
        producto = new Producto();
        producto.setId(1);
        producto.setNombre("Test Product");
        producto.setPrecio(100.0);
        producto.setDescripcion("Test Description");
        producto.setImagen("Test Image");
        producto.setCantidad(10);
    }

    @Test
    void save() {
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto result = productoService.save(producto);
        assertEquals(producto, result);
    }

    @Test
    void get() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        Optional<Producto> result = productoService.get(1);
        assertEquals(Optional.of(producto), result);
    }

    @Test
    void update() {
        when(productoRepository.save(producto)).thenReturn(producto);
        productoService.update(producto);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void delete() {
        productoService.delete(1);
        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    void findAll() {
        List<Producto> productos = Arrays.asList(producto, producto);
        when(productoRepository.findAll()).thenReturn(productos);
        List<Producto> result = productoService.findAll();
        assertEquals(productos, result);
    }
}