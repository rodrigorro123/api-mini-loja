package br.com.api.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.exception.DataException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.api.application.DTO.CarrinhoCreateDto;
import br.com.api.application.exception.ApiException;
import br.com.api.application.service.CarrinhoService;
import br.com.api.domain.entity.Carrinho;
import br.com.api.domain.entity.ItemProduto;
import br.com.api.domain.repository.CarrinhoRepository;
import br.com.api.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 *  Classe para operações do carrinho
 * @author rodrigo
 *
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class CarrinhoServiceImpl implements CarrinhoService {
	
	private final CarrinhoRepository carrinhoRepository;
	private final ProdutoRepository produtoRepository;
	private final ModelMapper mapper;

	/**
	 * realiza a soma total dos itens 
	 * @param car
	 * @return
	 */
	private Double sumSubTotalCar(CarrinhoCreateDto car ) {
		try {
			return car.getItens().stream()
					  .map(x -> x.getTotal())
					  .collect(Collectors.summingDouble(Double::doubleValue) );
		} catch (Exception e) {
			log.error("Erro ao calcular total Carrinho - " + e);
			return 0D;
		}
	}
	
	@Override
	public CarrinhoCreateDto obterCarrinho(Long idCarinho) throws ApiException {
		try {
			
			Carrinho carrinho = validaExistCarrinho(idCarinho);
			CarrinhoCreateDto carRet = mapper.map(carrinho, CarrinhoCreateDto.class);
			carRet.setTotalGeral(sumSubTotalCar(carRet));
			
			 return carRet;
		} catch (ApiException ae) {
            throw ApiException.builder()
            .statusCode(ae.getStatusCode())
            .code(ae.getCode())
            .message(ae.getMessage())
            .build();

		} catch (Exception e) {
			log.error("Erro ao buscar Carrinho - " + e);
            throw ApiException.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao buscar Carrinho")
            .build();
		}
	}

	@Override
	public CarrinhoCreateDto createCarrinho(CarrinhoCreateDto carrinhoIn) throws ApiException {
		try {
			
			Carrinho carSave = Carrinho.builder()
									   .idCarrinho(carrinhoIn.getIdCarrinho())
									   .idCliente(carrinhoIn.getIdCliente())
									   .status("EM ABERTO")
									   .itens( carrinhoIn.getItens().stream().map( itemCar -> {//transforma os itens do carrinho
																						ItemProduto ip = new ItemProduto();
																						ip.setQuantidade(itemCar.getQuantidade());
																						ip.setProduto( produtoRepository.findBySku(itemCar.getSku()).orElse(null) );						
																						ip.setTotal( itemCar.getQuantidade() * ip.getProduto().getPrice() );
																						return ip;
																					}
																				).filter(itemCar -> Objects.nonNull(itemCar))
																        		 .collect(Collectors.toList()) )
									   .build();
									    		
			Carrinho carResult = carrinhoRepository.save(carSave);//salva o carrinho
			
			//converte para o objeto de retorno
			CarrinhoCreateDto carReturn = mapper.map(carResult, CarrinhoCreateDto.class);
			
			//realiza a soma total dos itens
			carReturn.setTotalGeral(sumSubTotalCar(carReturn));
			
			return carReturn;
		} catch (Exception e) {
			log.error("Erro ao criar Carrinho - " + e);
            throw ApiException.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao criar Carrinho" )
            .build();
		}
	}

	@Override
	public CarrinhoCreateDto LimparCarrinho(Long idCarinho) throws ApiException {
		try {
			Carrinho carrinho = validaExistCarrinho(idCarinho);
			carrinho.setItens(new ArrayList<ItemProduto>());
			
			carrinho = carrinhoRepository.saveAndFlush(carrinho);
			
			return mapper.map(carrinho, CarrinhoCreateDto.class);
			
		} catch (ApiException ae) {
            throw ApiException.builder()
            .statusCode(ae.getStatusCode())
            .code(ae.getCode())
            .message(ae.getMessage())
            .build();
		} catch (Exception e) {
			log.error("Erro ao buscar Carrinho - " + e);
            throw ApiException.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao buscar Carrinho")
            .build();
		}
	}
	
	@Override
	public CarrinhoCreateDto finalizarCarrinho(Long idCarinho) throws ApiException {
		try {
			Carrinho carrinho = validaExistCarrinho(idCarinho);
			carrinho.setStatus("FINALIZADO");
			
			carrinho = carrinhoRepository.saveAndFlush(carrinho);
			
			CarrinhoCreateDto carRet = mapper.map(carrinho, CarrinhoCreateDto.class);
			
			carRet.setTotalGeral(sumSubTotalCar(carRet));
			
			return carRet;
		} catch (ApiException ae) {
            throw ApiException.builder()
            .statusCode(ae.getStatusCode())
            .code(ae.getCode())
            .message(ae.getMessage())
            .build();
		} catch (Exception e) {
			log.error("Erro ao buscar Carrinho - " + e);
            throw ApiException.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao buscar Carrinho")
            .build();
		}
	}
	
	@Override
	public List<CarrinhoCreateDto> getCarrinhoFinalizados() throws ApiException {
		try {
			List<Carrinho> carrinhos = carrinhoRepository.findByStatus("FINALIZADO");
			
			 return carrinhos.stream()
			 						.map(car -> {
			 							CarrinhoCreateDto ret = mapper.map(car, CarrinhoCreateDto.class);
			 							ret.setTotalGeral( sumSubTotalCar(ret) );
			 							return ret;
			 						}
							).filter(ret -> Objects.nonNull(ret))
				 		 .collect(Collectors.toList()) ;
			
		} catch (Exception e) {
			log.error("Erro ao buscar Carrinho - " + e);
            throw ApiException.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao buscar Carrinho")
            .build();
		}
	}
	/**
	 * verifica se existe carrinho na base
	 * @param idCarinho
	 * @return
	 * @throws ApiException
	 */
	private Carrinho validaExistCarrinho(Long idCarinho) throws ApiException {
		try {
			Carrinho carrinho = carrinhoRepository.findById(idCarinho).orElse(null);
			if(carrinho == null) {
				log.error("Carrinho nao encrontado - " + idCarinho);
	            throw ApiException.builder()
	            .statusCode(HttpStatus.NOT_FOUND.value())
	            .code(ApiException.NOTFOUND_ERROR)
	            .message("id de Carrinho nao localizado")
	            .build();	
			}
			
			return carrinho;
		} catch (DataException e) {
	           throw ApiException.builder()
	            .statusCode(HttpStatus.BAD_REQUEST.value())
	            .code(ApiException.GENERAL_ERROR)
	            .message("Erro ao localizar Carrinho")
	            .build();
		}

	}
	
}
