package cn.gzy.config;

import cn.gzy.intercept.TokenIntercept;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenIntercept())
                .addPathPatterns("/**")
                .excludePathPatterns("/*.html","/login","/LoginReg.html","/LoginReg","/index1.html,/index1,/category.html");
    }
}
