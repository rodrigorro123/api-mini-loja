package br.com.api.application.controller;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.application.DTO.CarrinhoCreateDto;
import br.com.api.application.DTO.Error;
import br.com.api.application.exception.ApiException;
import br.com.api.application.service.CarrinhoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Classe de controller da api de mini-loja
 * @author rodrigo
 *
 */
@RestController
@Api(value = "Java dev backend")
@CrossOrigin
@Slf4j
@RequestMapping("/mini-loja")
@RequiredArgsConstructor
@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Registro processado com sucesso"),
		@ApiResponse(code = 201, message = "Registro criado com sucesso"),
		@ApiResponse(code = 202, message = "Registro alterado com sucesso"),
		@ApiResponse(code = 400, message = "Requisiçao invalida"),
		@ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado"),
		@ApiResponse(code = 428, message = "Pre-Requisito necessario"),
		@ApiResponse(code = 500, message = "Erro interno")
	     })
public class ApiController {

	private final CarrinhoService carrinhoService;


	/**
	 * metodo para retornar carrinho com seu respectivo total
	 * @param carrinho
	 * @return
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "metodo para retornar carrinho com seu respectivo total")
	@Produces(value=MediaType.APPLICATION_JSON)
	public ResponseEntity getCarrinho (@RequestParam Long idCarinho) {
			try 
			{
					return ResponseEntity
							.status(HttpStatus.OK)
							.body(carrinhoService.obterCarrinho(idCarinho));
				
		    } catch (ApiException ex) {
		    	log.error(ex.getMessage());
	            return ResponseEntity.status(ex.getStatusCode()).body(Error.builder()
                        .code(ex.getStatusCode().toString())
                        .message(ex.getCode())
                        .description(ex.getMessage())
                        .build());
		    }
	}

	/**
	 * metodo para criar carrinho
	 * @param carrinho
	 * @return
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "metodo para criar carrinho")
	@Produces(value=MediaType.APPLICATION_JSON)
	public ResponseEntity createCarrinho (@Valid @RequestBody CarrinhoCreateDto carrinho) {
			try 
			{
					return ResponseEntity
							.status(HttpStatus.CREATED)
							.body(carrinhoService.createCarrinho(carrinho));

				
		    } catch (ApiException ex) {
		    	log.error(ex.getMessage());
	            return ResponseEntity.status(ex.getStatusCode()).body(Error.builder()
                        .code(ex.getStatusCode().toString())
                        .message(ex.getCode())
                        .description(ex.getMessage())
                        .build());
		    }
	}
	
	/**
	 * metodo para atualizar carrinho
	 * @param carrinho
	 * @return
	 */
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "metodo para atualizar carrinho")
	@Produces(value=MediaType.APPLICATION_JSON)
	public ResponseEntity atualizarCarrinho (@Valid @RequestBody CarrinhoCreateDto carrinho) {
			try 
			{
				if(carrinho.getIdCarrinho() == null) {
		            throw ApiException.builder()
		            .statusCode(HttpStatus.PRECONDITION_REQUIRED.value())
		            .code(ApiException.VALIDATION_ERROR)
		            .message("Deve-se informar o id do carrinho para atualizar")
		            .build();
				}
				
					return ResponseEntity
							.status(HttpStatus.ACCEPTED)
							.body(carrinhoService.createCarrinho(carrinho));

				
		    } catch (ApiException ex) {
		    	log.error(ex.getMessage());
	            return ResponseEntity.status(ex.getStatusCode()).body(Error.builder()
                        .code(ex.getStatusCode().toString())
                        .message(ex.getCode())
                        .description(ex.getMessage())
                        .build());
		    }
	}

	/**
	 * metodo para apagar items do carrinho
	 * @param idCarinho
	 * @return
	 */
	@DeleteMapping(value = "/{idCarinho}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "metodo para apagar items do carrinho")
	@Produces(value=MediaType.APPLICATION_JSON)
	public ResponseEntity apagarCarrinho (@PathVariable Long idCarinho) {
			try 
			{
				return ResponseEntity
						.status(HttpStatus.ACCEPTED)
						.body(carrinhoService.LimparCarrinho(idCarinho) );

				
		    } catch (ApiException ex) {
		    	log.error(ex.getMessage());
	            return ResponseEntity.status(ex.getStatusCode()).body(Error.builder()
                        .code(ex.getStatusCode().toString())
                        .message(ex.getCode())
                        .description(ex.getMessage())
                        .build());
		    }
	}

	/**
	 * metodo para finalizar carrinho
	 * @param idCarinho
	 * @return
	 */
	@PutMapping(value = "/finalizado/{idCarinho}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "metodo para finalizar carrinho")
	@Produces(value=MediaType.APPLICATION_JSON)
	public ResponseEntity finalizarCarrinho (@PathVariable Long idCarinho) {
			try 
			{
				return ResponseEntity
						.status(HttpStatus.ACCEPTED)
						.body(carrinhoService.finalizarCarrinho(idCarinho));
				
		    } catch (ApiException ex) {
		    	log.error(ex.getMessage());
	            return ResponseEntity.status(ex.getStatusCode()).body(Error.builder()
                        .code(ex.getStatusCode().toString())
                        .message(ex.getCode())
                        .description(ex.getMessage())
                        .build());
		    }
	}
	
	/**
	 * metodo para retornar carrinhos finalizados
	 * @return
	 */
	@GetMapping(value = "/finalizado")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "metodo para retornar carrinhos finalizados")
	@Produces(value=MediaType.APPLICATION_JSON)
	public ResponseEntity getCarrinhoFinalizado () {
			try 
			{
					return ResponseEntity
							.status(HttpStatus.OK)
							.body(carrinhoService.getCarrinhoFinalizados());

				
		    } catch (ApiException ex) {
		    	log.error(ex.getMessage());
	            return ResponseEntity.status(ex.getStatusCode()).body(Error.builder()
                        .code(ex.getStatusCode().toString())
                        .message(ex.getCode())
                        .description(ex.getMessage())
                        .build());
		    }
	}
}
