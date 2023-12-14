package edu.cibertec.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.cibertec.ecommerce.model.Pedido;
import edu.cibertec.ecommerce.model.Usuario;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Integer>{
	List<Pedido> findByUsuario(Usuario usuario);
}
