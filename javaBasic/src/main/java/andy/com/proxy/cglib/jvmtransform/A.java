package andy.com.proxy.cglib.jvmtransform;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class A extends BaseProxiableClass implements Serializable {
    private String value = "";
    public String getValue(){
        return value;
    }
    public void setValue(String value){
        this.value = value;
    }
    private static final long serialVersionUID = 1L;
    /**
     * This method will be invoked whenever a serialization call happen on the
     * CGLIB wrapper. Returning "this" might seem weird. But see it in the context of
     * proxy. When the method is invoked on the proxy, it delegates the call to the
     * actual object, which in-turn returns the real object back. nice way to remove the
     * wrapper
     */
    public Object writeReplace() throws ObjectStreamException {
        return new DataHolder(this);
    }
}

