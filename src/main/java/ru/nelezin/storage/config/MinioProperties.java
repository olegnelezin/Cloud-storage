package ru.nelezin.storage.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(value = "minio")
@Configuration
public class MinioProperties {

    private String endpoint;

    private String user;

    private String password;
}
