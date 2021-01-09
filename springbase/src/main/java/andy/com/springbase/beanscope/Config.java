package andy.com.springbase.beanscope;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Configuration
@ComponentScan("andy.com.springbase.beanscope")
public class Config{

    @Bean
    public CustomScopeConfigurer customScopeConfigurer(){

        //注册新的 scope
        CustomScopeConfigurer configure = new CustomScopeConfigurer();
        Map<String,Object> scopes = new HashMap<>();
        scopes.put(ThreadScope.name,new ThreadScope());
        configure.setScopes(scopes);
        return configure;
    }
}