package fraktikant.tflcstefan.hybrit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HybridItJobsMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(HybridItJobsMarketApplication.class, args);
	}

}
