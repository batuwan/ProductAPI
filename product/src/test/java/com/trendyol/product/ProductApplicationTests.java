package com.trendyol.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.product.Controller.ProductController;
import com.trendyol.product.Domain.Product;
import com.trendyol.product.Service.ProductService;
import com.trendyol.product.Service.RestService;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProductService productService;
	@MockBean
	private RestService restService;
	@InjectMocks
	private ProductController productController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void createProduct_whenProductIsNew_ShouldReturn201WithProperLocationHeader() throws Exception{
		//Given
		Product mockProduct = new Product();
		//When
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8081/v1/products/")
				.accept(MediaType.APPLICATION_JSON)
				.content(convertToJson(mockProduct))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_CREATED);
		assertThat(response.getHeader(HttpHeaders.LOCATION))
				.isEqualTo("http://localhost:8081/v1/products/" + mockProduct.getId());

	}

	@Test
	public void findProductById_whenProductIsNull_ShouldReturn404() throws Exception {
		//Given
		String productId = "mock_id";
		//When
		when(productService.findProductById(productId)).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/v1/products/"+ productId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_NOT_FOUND);
	}

	@Test
	public void findProductById_whenEverythingIsOK_ShouldReturn200() throws Exception {
		//Given
		String productId = "mock_id";
		Product product = new Product();
		//When
		when(productService.findProductById(productId)).thenReturn(java.util.Optional.of(product));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/v1/products/"+ productId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_OK);
	}

	@Test
	public void deleteProduct_whenProductIsNotExist_ShouldReturn404() throws Exception {
		//Given
		String productId = "mockProduct";
		//When
		when(productService.findProductById(productId)).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/v1/products/notExistProduct")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_NOT_FOUND);
	}

	@Test
	public void deleteProduct_whenEverythingIsOkay_ShouldReturn200() throws Exception {

		//Given
		String productId = "mockId";
		Product product = new Product();
		product.setId(productId);
		//When
		when(productService.findProductById(productId)).thenReturn(Optional.of(product));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/v1/products/"+productId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_OK);

	}



	public static String convertToJson(Object o) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(o);
	}

}
