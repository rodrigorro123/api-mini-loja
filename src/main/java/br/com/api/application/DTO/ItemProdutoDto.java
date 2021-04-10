package br.com.api.application.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ItemProdutoDto{
		
	 private String sku;
	 private Long quantidade;
	 private Double total;
	 private ProdutoDto produto;
}
