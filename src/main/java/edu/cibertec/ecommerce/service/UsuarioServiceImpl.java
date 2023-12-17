package edu.cibertec.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cibertec.ecommerce.model.Usuario;
import edu.cibertec.ecommerce.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> findByid(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {

		return usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public void deleteById(Integer id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	
}

