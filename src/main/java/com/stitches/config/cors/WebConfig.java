package com.stitches.config.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${allowed_credential}")
    private boolean allowedCredential;

    @Value("${allowed_origin}")
    private String allowedOrigin;

    @Value("${allowed_header}")
    private String allowedHeader;

    @Value("${allowed_method}")
    private String allowedMethod;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(allowedCredential);
        config.addAllowedOrigin(allowedOrigin); // You can specify specific origins here
        config.addAllowedHeader(allowedHeader);
        config.addAllowedMethod(allowedMethod);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}