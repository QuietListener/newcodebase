package andy.com.springbase.beanlifetime;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class TestObject implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @PostConstruct
    public void initMethod(){
        System.out.println("init");
    }

    @PreDestroy
    public void destroyMehtod(){
        System.out.println("destroy");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext");
        this.applicationContext = applicationContext;
    }

    public void hello(){
        System.out.println("hello world");
    }
}
