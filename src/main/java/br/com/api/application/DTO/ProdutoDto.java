package br.com.api.application.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ProdutoDto{
		
	 private String sku;
	
	 private String name;
	 
	 private Long stock;
	 
	 private Boolean isInStock;
	 
	 private Double price;
	
}
