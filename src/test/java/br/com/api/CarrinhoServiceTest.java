package br.com.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.api.application.DTO.CarrinhoCreateDto;
import br.com.api.application.DTO.ItemProdutoDto;
import br.com.api.application.service.CarrinhoService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class CarrinhoServiceTest {
	
	@Autowired
	private CarrinhoService service;

	@Test
	public void testACriarCarrinho() {
		try {

			ItemProdutoDto item = ItemProdutoDto.builder()
					.quantidade(5L)
					.sku("S2390002")
					.build();
			CarrinhoCreateDto carrinho = CarrinhoCreateDto.builder()
										  .idCliente(5555L)
										  .itens(Arrays.asList(item))
										  .build();
			
			CarrinhoCreateDto ret = this.service.createCarrinho(carrinho);

			assertThat(ret.getIdCliente()).isEqualTo(5555L) ;
			assertThat(ret.getDtCriacao() ).isNotNull();
			assertThat(ret.getIdCarrinho()).isNotNull();
			assertThat(ret.getTotalGeral()).isGreaterThan(0D);
			assertThat(ret.getItens()).isNotEmpty();

		} catch (Exception e) {
			log.error("falha ao realizar testes de criação do carrinho - {}", e.getMessage());
		}
	}	


	@Test
	public void testLimparCarrinho() {
		try {

			CarrinhoCreateDto ret = this.service.LimparCarrinho(1L);
			assertThat(ret.getItens()).isEmpty();

		} catch (Exception e) {
			log.error("falha ao realizar testes de criação do carrinho - {}", e.getMessage());
		}
	}	
	
	@Test
	public void testBuscarCarrinho() {
		try {

			CarrinhoCreateDto ret = this.service.obterCarrinho(1L);
			assertThat(ret.getIdCliente()).isEqualTo(5555L) ;
			assertThat(ret.getDtCriacao() ).isNotNull();
			assertThat(ret.getIdCarrinho()).isNotNull();

		} catch (Exception e) {
			log.error("falha ao realizar testes de criação do carrinho - {}", e.getMessage());
		}
	}	

	@Test
	public void testFinalizarCarrinho() {
		try {

			CarrinhoCreateDto ret = this.service.finalizarCarrinho(1L);
			assertThat(ret.getStatus()).isEqualTo("FINALIZADO") ;

		} catch (Exception e) {
			log.error("falha ao realizar testes de criação do carrinho - {}", e.getMessage());
		}
	}	
	
}
