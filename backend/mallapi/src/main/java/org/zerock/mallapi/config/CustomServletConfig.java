package org.zerock.mallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.mallapi.controller.formatter.LocalDateFormatter;

// Ajax 이용 서비스 호출 시 CORS로 인해 정상적인 호출이 제한적이 된다. 또한 리액트에서 스프링 부트로 동작하는 서버를 호출해야 하므로 CORS 설정이 필요하다
@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter());
    }

    // CORS설정은 @Controller가 있는 클래스에 @CrossOrigin을 적용하거나 Spring Security를 이용하는 설정이 있다.
    // @CrossOrigin 설정은 모든 Controller에 개별적으로 적용해야 하므로 예제에선 WebMvcConfigurer의 설정으로 사용한다.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(300)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
    }

}
