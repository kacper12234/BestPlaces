package kacper.bestplaces.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public BCryptPasswordEncoder pwdEncrypt() {
		BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
		return bcp;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement()
	{
		MultipartConfigFactory factory=new MultipartConfigFactory();
		return factory.createMultipartConfig();
	}
}
