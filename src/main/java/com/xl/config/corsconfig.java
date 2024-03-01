package com.xl.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class corsconfig {
private  static  final long MAX_AGE=24*60*60;


@Bean
    public CorsFilter corsFilter(){
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("http://localhost:8080");//访问源
    corsConfiguration.addAllowedHeader("*");//访问源头信息
    corsConfiguration.addAllowedMethod("*");//访问源方法
    corsConfiguration.setMaxAge(MAX_AGE);
    source.registerCorsConfiguration("/**",corsConfiguration);
    return new CorsFilter(source);


}

}
