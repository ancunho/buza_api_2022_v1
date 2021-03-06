package buza.group.api;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("buza.group.api.dao")
public class ApiApplication {

    public static void main(String[] args) {
        log.info("Buza AdpApplication starting .......");
        SpringApplication.run(ApiApplication.class, args);
        log.info("Buza AdpApplication started .......");
    }

}
