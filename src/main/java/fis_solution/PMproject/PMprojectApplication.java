package fis_solution.PMproject;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;

@SpringBootApplication
public class PMprojectApplication {
	@Bean
	JPAQueryFactory JpaQueryFactory(EntityManager em){
		System.out.println("em ============================================== " + em);
		return new JPAQueryFactory(em);
	}


	public static void main(String[] args) {
		SpringApplication.run(PMprojectApplication.class, args);
	}


	// 현승구 cors 매핑 설정완료
	@Configuration
	public class WebConfig implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry){
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000")
					.allowCredentials(true)
					.allowedMethods("*");
		}
	}

}
