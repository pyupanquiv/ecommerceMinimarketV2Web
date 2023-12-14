package edu.cibertec.ecommerce.service;

import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.repository.IUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    Usuario usuario;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Test User");
        usuario.setUsername("test");
        usuario.setEmail("test@test.com");
        usuario.setDireccion("Test Address");
        usuario.setTelefono("123456789");
        usuario.setTipo("Test Type");
        usuario.setPassword("Test Password");
    }

    @Test
    void findByid() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.findByid(1);

        verify(usuarioRepository, times(1)).findById(1);
        assertEquals(usuario, result.orElse(null));
    }

    @Test
    void save() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.save(usuario);

        verify(usuarioRepository, times(1)).save(usuario);
        assertEquals(usuario, result);
    }

    @Test
    void findByEmail() {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.findByEmail("test@test.com");

        verify(usuarioRepository, times(1)).findByEmail("test@test.com");
        assertEquals(usuario, result.orElse(null));
    }

    @Test
    void findAll() {
        List<Usuario> usuarios = Arrays.asList(usuario, usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.findAll();

        verify(usuarioRepository, times(1)).findAll();
        assertEquals(usuarios, result);
    }
}