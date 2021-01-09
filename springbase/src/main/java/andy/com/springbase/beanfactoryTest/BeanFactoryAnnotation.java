package andy.com.springbase.beanfactoryTest;

import andy.com.springbase.IFXNewsProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用annotation来注入
 */
public class BeanFactoryAnnotation {
    public static void main(String [] args){
        ApplicationContext container = new ClassPathXmlApplicationContext("classpath:spring_annotation.xml");
        IFXNewsProvider ifxNewsProvider = container.getBean(IFXNewsProvider.class);
        ifxNewsProvider.getAndPersist();
    }
}

