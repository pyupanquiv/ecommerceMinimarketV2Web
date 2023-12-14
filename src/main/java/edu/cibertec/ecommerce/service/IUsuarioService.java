package edu.cibertec.ecommerce.service;

import java.util.List;
import java.util.Optional;

import edu.cibertec.ecommerce.model.Usuario;

public interface IUsuarioService {
	
	List<Usuario> findAll();
	Optional<Usuario> findByid(Integer id);
	Usuario save (Usuario usuario);
	Optional<Usuario> findByEmail(String email);
}
