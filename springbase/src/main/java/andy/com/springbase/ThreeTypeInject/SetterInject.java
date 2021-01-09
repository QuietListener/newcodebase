package andy.com.springbase.ThreeTypeInject;

import andy.com.springbase.IFXNewsListener;
import andy.com.springbase.IFXNewsPersister;

public class SetterInject {

    /**
     * setter来注入依赖
     */
    static class FXNewsProvider{
        IFXNewsListener listener;
        IFXNewsPersister persister;

        public FXNewsProvider(){}

        public IFXNewsListener getListener() {
            return listener;
        }

        public void setListener(IFXNewsListener listener) {
            this.listener = listener;
        }

        public IFXNewsPersister getPersister() {
            return persister;
        }

        public void setPersister(IFXNewsPersister persister) {
            this.persister = persister;
        }
    }
}
