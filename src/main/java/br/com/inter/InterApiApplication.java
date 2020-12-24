package br.com.inter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.inter.service.MainService;

@EnableScheduling
@SpringBootApplication
public class InterApiApplication {
	
	private final MainService mainService;
	
	public InterApiApplication(MainService mainService) {
		this.mainService = mainService;
	}

	public static void main(String[] args) {
		SpringApplication.run(InterApiApplication.class, args);
	}
	
	@Scheduled(cron = "0/5 * * * * *")
	//@Scheduled(cron = "${job.cron}")
	private void execute() throws Exception {
		mainService.execute();
	}

}
