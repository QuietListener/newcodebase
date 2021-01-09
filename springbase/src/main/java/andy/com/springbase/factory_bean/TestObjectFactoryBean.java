package andy.com.springbase.factory_bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("date")
public class TestObjectFactoryBean implements FactoryBean<Date> {
    @Override
    public Date getObject() throws Exception {
        return new Date();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
