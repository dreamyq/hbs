package cn.codemodel.company.interceptor;

import cn.codemodel.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("经过WebMvcConfigurationSupport");
        registry.addInterceptor(jwtInterceptor).
                addPathPatterns("/**"). excludePathPatterns("/**/login/**").excludePathPatterns("/**/coRegister/**");
    }
}
