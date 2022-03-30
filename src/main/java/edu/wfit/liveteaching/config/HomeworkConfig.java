package edu.wfit.liveteaching.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 课后作业配置类
 */
@Component
@ConfigurationProperties(prefix = "homework")
public class HomeworkConfig {
    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
