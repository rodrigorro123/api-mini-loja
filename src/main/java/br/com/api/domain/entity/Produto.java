package br.com.api.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de mapeamento do banco para o produto
 * @author rodrigo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name= "Produto")
@Table(name = Produto.TABLE_NAME)
public class Produto implements Serializable  {
 
	private static final long serialVersionUID = -2348089903917261471L;

	public static final String TABLE_NAME = "tbl_produto";
	
	@Id
	@Column(name = "sku")
	 private String sku;

	@Column(name = "name")
	 private String name;
	
	@Column(name = "stock")
	 private Long stock;
		
	@Column(name = "is_in_stock")
	 private Boolean isInStock;
	
	@Column(name = "price")
	 private Double price;

}
