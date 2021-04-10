package br.com.api;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.api.application.DTO.CarrinhoCreateDto;
import br.com.api.application.DTO.ItemProdutoDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarrinhoTest {

	@Autowired
	private MockMvc mockMvc;	

	@Test
	public void testACriarCarrinho() throws Exception {
		
		ItemProdutoDto item = ItemProdutoDto.builder()
											.quantidade(5L)
											.sku("S2390002")
											.build();
		CarrinhoCreateDto carrinho = CarrinhoCreateDto.builder()
													  .idCliente(5555L)
													  .itens(Arrays.asList(item))
													  .build();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(MockMvcRequestBuilders
		.post("/mini-loja")
		.contentType(MediaType.APPLICATION_JSON)
		.content( ow.writeValueAsString(carrinho ) ) )
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful() ) ;
	}

	@Test
	public void testAtualizarCarrinho() throws Exception {
		
		ItemProdutoDto item = ItemProdutoDto.builder()
											.quantidade(5L)
											.sku("S2390002")
											.build();
		CarrinhoCreateDto carrinho = CarrinhoCreateDto.builder()
													  .idCliente(5555L)
													  .idCarrinho(1L)
													  .itens(Arrays.asList(item))
													  .build();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(MockMvcRequestBuilders
		.put("/mini-loja")
		.contentType(MediaType.APPLICATION_JSON)
		.content( ow.writeValueAsString(carrinho ) ) )
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful() ) ;
	}
	
	@Test
	public void testAtualizarCarrinhoFailPreRequisito() throws Exception {
		
		ItemProdutoDto item = ItemProdutoDto.builder()
											.quantidade(5L)
											.sku("S2390002")
											.build();
		CarrinhoCreateDto carrinho = CarrinhoCreateDto.builder()
													  .idCliente(5555L)
													  .itens(Arrays.asList(item))
													  .build();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(MockMvcRequestBuilders
		.put("/mini-loja")
		.contentType(MediaType.APPLICATION_JSON)
		.content( ow.writeValueAsString(carrinho ) ) )
		.andExpect(MockMvcResultMatchers.status().isPreconditionRequired() ) ;
	}


	@Test
	public void testGetCarrinhos() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
		.get("/mini-loja?idCarinho=1")
		.contentType(MediaType.APPLICATION_JSON) )
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful() ) ;

	}

	@Test
	public void testFinalizarCarrinhos() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
		.put("/mini-loja/finalizado/1")
		.contentType(MediaType.APPLICATION_JSON) )
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful() ) ;
	}

	@Test
	public void testGetCarrinhosFinalizados() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
		.get("/mini-loja/finalizado")
		.contentType(MediaType.APPLICATION_JSON) )
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful() ) ;
	}

	@Test
	public void testVerificarCarrinhosFinalizados() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
		.get("/mini-loja/finalizado")
		.contentType(MediaType.APPLICATION_JSON) )
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful() ) ;
	}

	@Test
	public void testLimpaItemCarrinho() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
		.delete("/mini-loja/1")
		.contentType(MediaType.APPLICATION_JSON) )
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful() ) ;

	}
}
