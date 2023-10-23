package com.stitches;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Library APIS", version = "1.0", description = "Library Management APis."))
public class StitchesApplication {

    public static void main(String[] args) {
        SpringApplication.run(StitchesApplication.class, args);
    }

}
