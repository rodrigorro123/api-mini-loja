package br.com.api.application.service;

import java.util.List;

import br.com.api.application.DTO.CarrinhoCreateDto;
import br.com.api.application.exception.ApiException;


public interface CarrinhoService {
	/**
	 * Limpa itens do carrinho
	 * @param idCarinho
	 * @return
	 * @throws ApiException
	 */
	CarrinhoCreateDto LimparCarrinho (Long idCarinho) throws  ApiException;
	
	/**
	 * retorna dados do carrinho solicitado
	 * @param idCarinho
	 * @return
	 * @throws ApiException
	 */
	CarrinhoCreateDto obterCarrinho (Long idCarinho) throws  ApiException;
	
	/**
	 * cria carrinho
	 * @param carrinho
	 * @return
	 * @throws ApiException
	 */
	CarrinhoCreateDto createCarrinho (CarrinhoCreateDto carrinho) throws  ApiException;
	
	/**
	 * finaliza o carrinho informado
	 * @param idCarinho
	 * @return
	 * @throws ApiException
	 */
	CarrinhoCreateDto finalizarCarrinho (Long idCarinho) throws  ApiException;
	
	/**
	 * retorna todos carrinhos com status finalizado
	 * @return
	 * @throws ApiException
	 */
	List<CarrinhoCreateDto> getCarrinhoFinalizados() throws ApiException; 
	
}
