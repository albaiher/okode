package com.okode.prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.*;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class PruebaApplication {

	private static final String BASE_URL = "https://api.themoviedb.org";
	private static final String KEY = "8c4c40fa5a83e95ea047d237ee28b1f7";

	private WebClient client = WebClient.builder()
					.baseUrl(BASE_URL)
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
					.build();

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}

	@GetMapping("/searchMovie")
	public String searchMovie(@RequestParam(value = "movie", defaultValue = "movie") String movie){
		return searchMovieTMDB(movie);
	}

	private String searchMovieTMDB(String movie){
		return client.get()
				.uri(String.join("","3/search/movie?","api_key=",KEY,"&query=",movie))
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError,
										error -> Mono.error(new RuntimeException("Api not found")))
				.onStatus(HttpStatus::is5xxServerError,
										error -> Mono.error(new RuntimeException("Server is not responding")))
				.bodyToMono(String.class)
				.block();
	}
}
