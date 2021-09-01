package de.niklas.custemerservice;

import java.time.LocalDateTime;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import de.niklas.custemerservice.repository.AuftraegeRepository;
import de.niklas.custemerservice.repository.KundenRepository;

@SpringBootApplication
@EnableScheduling
public class CustomerServiceApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceApplication.class);
	private static final LocalDateTime startDateTime = LocalDateTime.parse("1970-01-08T19:28:55");
	private LocalDateTime lastDateTime = startDateTime;

	@Autowired
	private AuftraegeRepository auftraegeRepo;
	@Autowired
	private KundenRepository kundenRepo;

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
	
	@Scheduled(fixedDelay = 3000)
	public void run() {
		
		log.info("Current time is :: " + Calendar.getInstance().getTime());

		CustomerServiceWorker worker = new CustomerServiceWorker();
		worker.setAuftraegeRepo(auftraegeRepo);
		worker.setKundenRepo(kundenRepo);
		worker.setStartDateTime(lastDateTime);
		worker.work();
		
		
		lastDateTime = lastDateTime.plusMonths(1L);
	}

}
