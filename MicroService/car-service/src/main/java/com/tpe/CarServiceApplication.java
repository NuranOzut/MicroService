package com.tpe;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.NamingConvention;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration() // !!! konfigurayonu getiriliyor
				.setFieldMatchingEnabled(true)// !!! filedlar arasi eslestirmeyi otomatik yap
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) // !!! private fieldlar arasi eslestirme yap
				.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR); // !!! eslesme yapilirken getter ve setter metodlarini kullan
		return modelMapper ;
	}

	// !!! LogService APP icin request olusturulmasini saglayan sinif
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
