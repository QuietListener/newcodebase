package andy.com.springbase;

public class IFXNewsProvider {

    IFXNewsListener listener;
    IFXNewsPersister persister;


    public IFXNewsProvider(){
    }

    public IFXNewsProvider(IFXNewsListener l, IFXNewsPersister p){
        this.listener = l;
        this.persister = p;
    }

    public void getAndPersist(){
        System.out.println("get and persist");
    }

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
