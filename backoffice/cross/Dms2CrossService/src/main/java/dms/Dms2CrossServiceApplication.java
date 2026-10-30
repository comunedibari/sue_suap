package dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
//@EnableScheduling
public class Dms2CrossServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dms2CrossServiceApplication.class, args);
	}

}
