package andy.com.springbase.beanfactoryTest;

import andy.com.springbase.IFXNewsProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class BeanFactoryXML {
    public static void main(String [] args){

        DefaultListableBeanFactory reg  = new DefaultListableBeanFactory();
        BeanFactory container = (BeanFactory)bindViaCode(reg);
        IFXNewsProvider ifxNewsProvider = (IFXNewsProvider)container.getBean("ifxProvider");
        ifxNewsProvider.getAndPersist();
    }

    /**
     * 外部配置的方式来注入
     * @param reg
     * @return
     */
    public static BeanFactory bindViaCode(BeanDefinitionRegistry reg){

        /**
         * XmlBeanDefinitionReader负责读取Spring指定格式的XML配置文件并解析，之后将解析后的文件内
         * 容映射到相应的BeanDefinition，并加载到相应的BeanDefinitionRegistry中（在这里是DefaultListableBeanFactory）。
         */
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(reg);
        reader.loadBeanDefinitions("classpath:springxml1.xml");
        return (BeanFactory) reg;
    }

}

