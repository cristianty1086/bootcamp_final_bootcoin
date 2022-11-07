package com.nttdata.bootcamp.pfinal;

import com.nttdata.bootcamp.pfinal.domain.Parameter;
import com.nttdata.bootcamp.pfinal.repository.ParameterRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "App BootCoin", version = "1.0", description = "Documentation APIs v1.0"))
public class BootcoinApplication implements CommandLineRunner {

	@Autowired
	private ParameterRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BootcoinApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();
		Flux.just(Parameter.builder().parameter("PaymentType").value("Yanki").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("PaymentType").value("Transfer").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("DocumentType").value("DNI").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("DocumentType").value("CEX").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("DocumentType").value("Pasaporte").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("BootcoinTransactionType").value("entry").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("BootcoinTransactionType").value("withdrawal").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("CompraBootcoin").value("20.50").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
		Flux.just(Parameter.builder().parameter("VentaBootcoin").value("19.70").build())
				.flatMap(p -> repository.save(p)).subscribe(p -> log.info("Insert: " + p.toString()));
	}
	@Bean
	public ReactiveRedisTemplate<String, Parameter> reactiveJsonPostRedisTemplate(
			ReactiveRedisConnectionFactory connectionFactory) {

		RedisSerializationContext<String, Parameter> serializationContext = RedisSerializationContext
				.<String, Parameter> newSerializationContext(new StringRedisSerializer())
				.hashKey(new StringRedisSerializer())
				.hashValue(new Jackson2JsonRedisSerializer<>(Parameter.class))
				.build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}

}
