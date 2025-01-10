package contest.blaybus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlaybusApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlaybusApplication.class, args);
	}

}
