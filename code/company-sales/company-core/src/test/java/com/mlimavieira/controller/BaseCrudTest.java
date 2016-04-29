package com.mlimavieira.controller;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.mlimavieira.CompanyCoreApplicationTests;

@Profile("test")
@Transactional
@WebAppConfiguration
@IntegrationTest("server.port:0")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringApplicationConfiguration(classes = CompanyCoreApplicationTests.class)
public abstract class BaseCrudTest {

	@Value("${local.server.port}")
	private int port;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		RestAssuredMockMvc.mockMvc = mockMvc;
	}

	protected abstract String getBaseUrl();

	protected abstract Object getCreateSuccessDto();

	protected abstract Object getUpdateSuccessDto();

	protected abstract Object getPostObjectFieldSizeError();

	protected abstract Object getPostObjectRequiredField();

	protected abstract Long foundById();

	protected abstract Long notFoundById();

	@Test
	public void findAllTest() {
		given().when().get(getBaseUrl()).then().statusCode(HttpServletResponse.SC_OK).contentType("application/json")
				.statusCode(200);
	}

	@Test
	public void findSuccessTest() {
		given().when().get(getBaseUrl() + "/" + foundById()).then().statusCode(HttpServletResponse.SC_OK)
				.contentType("application/json");
	}

	@Test
	public void findSaleOrderNotFoundTest() {
		given().when().get(getBaseUrl() + "/" + notFoundById()).then().statusCode(HttpServletResponse.SC_NOT_FOUND)
				.contentType("application/json");
	}

	@Test
	public void deleteSaleOrderNotFoundTest() {
		given().when().delete(getBaseUrl() + "/" + notFoundById()).then().statusCode(HttpServletResponse.SC_NOT_FOUND)
				.contentType("application/json");
	}

	@Test
	public void deleteSuccessTest() {
		given().when().delete(getBaseUrl() + "/" + foundById()).then().statusCode(HttpServletResponse.SC_OK)
				.contentType("application/json");
	}

	@Test
	public void errorRequiredFieldTest() {
		given().contentType(ContentType.JSON).body(getPostObjectRequiredField()).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_BAD_REQUEST).contentType("application/json");
	}

	@Test
	public void errorFieldMaxSizeTest() {
		given().contentType(ContentType.JSON).body(getPostObjectFieldSizeError()).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_BAD_REQUEST).contentType("application/json");
	}

	@Test
	public void createSuccessTest() {
		given().contentType(ContentType.JSON).body(getCreateSuccessDto()).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_CREATED).contentType("application/json");
	}

	@Test
	public void updateSuccessTest() {
		given().contentType(ContentType.JSON).body(getUpdateSuccessDto()).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_OK).contentType("application/json");
	}

}
