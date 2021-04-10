package br.com.api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.domain.entity.Produto;

/**
 * interface de acesso aos dados do produto
 * @author rodrigo
 *
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Optional<Produto> findBySku(String sku);
	
}
