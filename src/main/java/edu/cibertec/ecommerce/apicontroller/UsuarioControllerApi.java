package edu.cibertec.ecommerce.apicontroller;

import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioControllerApi {
    @Autowired
    private IUsuarioService usuarioService;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    //Listar Usuario
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> users = usuarioService.findAll();
        return ResponseEntity.ok(users);
    }

    //Guardar Usuario
    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
        usuario.setPassword(passEncode.encode(usuario.getPassword()));
        Usuario createdUser = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    //Actualizar Usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> existingUserOptional = usuarioService.findByid(id);
        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario existingUser = existingUserOptional.get();
        existingUser.setNombre(usuario.getNombre());
        existingUser.setUsername(usuario.getUsername());
        existingUser.setEmail(usuario.getEmail());
        existingUser.setDireccion(usuario.getDireccion());
        existingUser.setTelefono(usuario.getTelefono());
        existingUser.setTipo(usuario.getTipo());
        existingUser.setPassword(passEncode.encode(usuario.getPassword()));

        Usuario updatedUser = usuarioService.save(existingUser);

        return ResponseEntity.ok(updatedUser);
    }

    //Eliminar Usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") Integer id) {
        try {
            // Verificar si el usuario existe
            if (usuarioService.findByid(id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no existe");
            }

            // Eliminar el usuario
            usuarioService.deleteById(id);

            return ResponseEntity.ok("Usuario Eliminado");
        } catch (Exception e) {
            // Capturar cualquier excepción y devolver un código de estado 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuario no puede ser eliminado");
        }
    }

}
