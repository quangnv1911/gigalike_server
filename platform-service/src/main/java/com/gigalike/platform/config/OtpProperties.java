package com.gigalike.platform.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "otp")
public class OtpProperties {
    Integer expireTime;
    Integer length;
}
