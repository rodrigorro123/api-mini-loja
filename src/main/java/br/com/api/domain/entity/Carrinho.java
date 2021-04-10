package br.com.api.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de mapeamento do banco para o carrinho
 * @author rodrigo
 *
 */
@Transactional
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity(name= "Carrinho")
@Table(name = Carrinho.TABLE_NAME)

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")

public class Carrinho  implements Serializable {
 
	private static final long serialVersionUID = 1884395550354356546L;
	public static final String TABLE_NAME = "tbl_carrinho";
	
	@Id
	@Column(name = "id_carrinho")
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	 private Long idCarrinho;
	
	@Column(name = "id_cliente", nullable = false)
	 private Long idCliente;
	
	@Column(name = "status")
	 private String status;
	 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	 private Date dtCriacao;
 
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER )
    @JoinColumn(name = "id_carrinho")
    List<ItemProduto> itens;
 	
}
