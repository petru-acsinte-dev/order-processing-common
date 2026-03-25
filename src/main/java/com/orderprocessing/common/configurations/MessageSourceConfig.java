package com.orderprocessing.common.configurations;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.orderprocessing.common.constants.Constants;

@Configuration
public class MessageSourceConfig {

	@Bean
    MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(Constants.MESSAGE_FILE_BASE_NAME);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
