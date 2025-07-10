package org.zerock.mallapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링에서 설정 파일의 역할을 하는 @Configuration 이용하여 ModelMapper 설정한다.
@Configuration
public class RootConfig {
    
    // setFieldAccessLevel에서 AccessLevel.Private로 설정해주면 ModelMapper에서 private 필드에도 접근이 가능하도록 설정할 수 있다.
    // setFieldMatchingEnabled를 true로 설정하게 되면 필드 이름이 동일한 경우에만 매핑을 수행한다. 
    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
