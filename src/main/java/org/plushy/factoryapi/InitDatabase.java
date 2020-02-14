package org.plushy.factoryapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitDatabase {

	@Bean
	CommandLineRunner initDatabase(MachineRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Machine("Bilbo Baggins")));
			log.info("Preloading " + repository.save(new Machine("Frodo Baggins")));
		};
	}
}
