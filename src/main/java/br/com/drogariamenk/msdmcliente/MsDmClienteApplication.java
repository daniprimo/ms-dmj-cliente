package br.com.drogariamenk.msdmcliente;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
public class MsDmClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDmClienteApplication.class, args);
	}

}
