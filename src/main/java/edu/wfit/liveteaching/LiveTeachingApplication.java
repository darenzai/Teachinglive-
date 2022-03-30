package edu.wfit.liveteaching;

import edu.wfit.liveteaching.config.HomeworkConfig;
import edu.wfit.liveteaching.config.LiveConfig;
import edu.wfit.liveteaching.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        LiveConfig.class,
        SecurityConfig.class,
        HomeworkConfig.class
})
@SpringBootApplication
public class LiveTeachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveTeachingApplication.class, args);
    }

}
