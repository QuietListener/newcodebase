package andy.com.springbase.beanscope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.Map;

public class ThreadScope implements Scope {

    public static final String name = "thread";

    private final ThreadLocal threadLocal = new ThreadLocal(){
        protected Object initialValue(){
            return new HashMap();
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map m = (HashMap)threadLocal.get();
        Object obj = m.get(name);
        if(obj == null){
            obj = objectFactory.getObject();
            m.put(name,obj);
        }
        return obj;
    }

    @Override
    public Object remove(String name) {
        Map m = (HashMap)threadLocal.get();
        return m.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}