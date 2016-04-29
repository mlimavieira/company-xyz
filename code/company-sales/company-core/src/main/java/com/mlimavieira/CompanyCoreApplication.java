package com.mlimavieira;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class CompanyCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyCoreApplication.class, args);
	}

	@Bean
	public Mapper dozerBeanMapper() {

		final List<String> mappingFiles = Arrays.asList("dozer-mapping.xml");

		final DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(mappingFiles);
		return mapper;
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

}
