package com.example.santhuongmai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication
//public class SinhnhatApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(SinhnhatApplication.class, args);
//	}
//
//}
// import thêm để deploy vps
@SpringBootApplication
public class SanthuongmaiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SanthuongmaiApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SanthuongmaiApplication.class);
    }


}