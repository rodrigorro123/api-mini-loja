package br.com.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.domain.entity.Carrinho;

/**
 * interface de acesso aos dados do carrinho
 * @author rodrigo
 *
 */
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

	List<Carrinho> findByStatus(String status);
	
}
