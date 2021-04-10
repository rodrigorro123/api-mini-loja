package br.com.api.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import lombok.Data;

/**
 * Classe de mapeamento no banco do item do produto
 * @author rodrigo
 *
 */
@Transactional
@Data
@Entity(name= "ItemProduto")
@Table(name = ItemProduto.TABLE_NAME)

public class ItemProduto implements Serializable {
 
	private static final long serialVersionUID = 1884395550354356546L;
	public static final String TABLE_NAME = "tbl_item_produto";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	 private Long id;
	
	@Column(name = "quantidade")
	 private Long quantidade;
	
	@Column(name = "total")
	 private Double total;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sku")
    private Produto produto;
	
}
