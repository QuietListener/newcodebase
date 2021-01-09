package andy.com.springbase.ThreeTypeInject;

import andy.com.springbase.IFXNewsListener;
import andy.com.springbase.IFXNewsPersister;

public class ConstructorInject {

    /**
     * 构造方法注入方式
     */
    static class FXNewsProvider{
        IFXNewsListener listener;
        IFXNewsPersister persister;

        public FXNewsProvider(IFXNewsListener l, IFXNewsPersister p){
                this.listener = l;
                this.persister = p;
        }


    }
}

