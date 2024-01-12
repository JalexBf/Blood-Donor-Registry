package gr.hua.dit.ds.BloodRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "gr.hua.dit.ds.BloodRegistry")
public class BloodRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloodRegistryApplication.class, args);
	}

}
