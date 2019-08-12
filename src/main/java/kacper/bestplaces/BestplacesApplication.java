package kacper.bestplaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class BestplacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestplacesApplication.class, args);
	}
}
