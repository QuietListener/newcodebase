package andy.com.springbase.beanfactoryTest;

import andy.com.springbase.DowJonesNewsListener;
import andy.com.springbase.DowJonesNewsPersister;
import andy.com.springbase.IFXNewsProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class BeanFactoryHardCode {
    public static void main(String [] args){

        DefaultListableBeanFactory reg  = new DefaultListableBeanFactory();
        BeanFactory container = (BeanFactory)bindViaCode(reg);
        IFXNewsProvider ifxNewsProvider = (IFXNewsProvider)container.getBean("ifxProvider");
        ifxNewsProvider.getAndPersist();
    }

    /**
     * 代码的方式来注入
     * @param reg
     * @return
     */
    public static BeanFactory bindViaCode(BeanDefinitionRegistry reg){

        AbstractBeanDefinition  newprovider = new RootBeanDefinition(IFXNewsProvider.class,true);
        AbstractBeanDefinition  listener = new RootBeanDefinition(DowJonesNewsListener.class,true);
        AbstractBeanDefinition  persister = new RootBeanDefinition(DowJonesNewsPersister.class,true);

        //bean 的定义注册到容器中
        reg.registerBeanDefinition("ifxProvider",newprovider);
        reg.registerBeanDefinition("listener",listener);
        reg.registerBeanDefinition("persister",persister);

        //构造方法注入
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0,listener);
        argumentValues.addIndexedArgumentValue(1,persister);
        newprovider.setConstructorArgumentValues(argumentValues);

        //setter方法注入
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("listener",listener));
        propertyValues.addPropertyValue(new PropertyValue("persister",persister));
        newprovider.setPropertyValues(propertyValues);

        return (BeanFactory) reg;
    }

}

