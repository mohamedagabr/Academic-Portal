package com.academic.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class AcademicPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicPortalApplication.class, args);
	}

}
