package wiki.jixing.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wiki.jixing.myblog.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/static/**", "/**/login", "/**/register", "/**/logout", "/**/forget", "*.css", "*.js", "*.jpg", "*.png", "*.jpeg");
    }
}
