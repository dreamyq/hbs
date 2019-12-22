package cn.codemodel.recruitment;

import cn.codemodel.interceptor.JwtInterceptor;
import cn.codemodel.util.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.codemodel.recruitment.dao")
public class RecruitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class);
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
