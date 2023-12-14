package edu.cibertec.ecommerce.controller;

import edu.cibertec.ecommerce.model.Producto;
import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.service.IUsuarioService;
import edu.cibertec.ecommerce.service.ProductoService;
import edu.cibertec.ecommerce.service.UploadFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private IUsuarioService usuarioService;

    @Mock
    private UploadFileService uploadFileService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ProductoController productoController;

    @Test
    void show() {
        productoController.show(model);
        verify(productoService, times(1)).findAll();
    }

    @Test
    void create() {
        String view = productoController.create();
        assertEquals("productos/create", view);
    }

    @Test
    void save() throws IOException {
        Producto producto = new Producto();
        when(usuarioService.findByid(anyInt())).thenReturn(Optional.of(new Usuario()));
        when(uploadFileService.saveImage(file)).thenReturn("test.jpg");
        when(session.getAttribute("idusuario")).thenReturn("1"); // Set an attribute for the HttpSession object

        String view = productoController.save(producto, file, session);

        verify(productoService, times(1)).save(producto);
        assertEquals("redirect:/productos", view);
    }

    @Test
    void edit() {
        Producto producto = new Producto();
        when(productoService.get(anyInt())).thenReturn(Optional.of(producto));

        String view = productoController.edit(1, model);

        verify(productoService, times(1)).get(1);
        assertEquals("productos/edit", view);
    }

    @Test
    void update() throws IOException {
        Producto producto = new Producto();
        producto.setImagen("test.jpg");
        when(productoService.get(null)).thenReturn(Optional.of(producto));
        when(uploadFileService.saveImage(file)).thenReturn("test.jpg");

        String view = productoController.update(producto, file);

        verify(productoService, times(1)).update(producto);
        assertEquals("redirect:/productos", view);
    }

    @Test
    void delete() {
        Producto producto = new Producto();
        producto.setImagen("test.jpg");
        when(productoService.get(anyInt())).thenReturn(Optional.of(producto));

        String view = productoController.delete(1);

        verify(productoService, times(1)).delete(1);
        assertEquals("redirect:/productos", view);
    }
}