package br.com.api.application.DTO;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CarrinhoCreateDto{

	 private Long idCarrinho;
	 
	 @NotNull
	 private Long idCliente;
	 
	 private String status;

	 @JsonFormat(timezone = "GMT-3", pattern = "yyyy-MM-dd HH:mm:ss")
	 private Date dtCriacao;
	 
	 private Double totalGeral; 
	 private List<ItemProdutoDto> itens;
		
}
