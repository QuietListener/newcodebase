package andy.com.proxy.cglib.jvmtransform;

import java.io.*;

public class DataHolder implements Serializable {

    private static final long serialVersionUID = 1L;
    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public DataHolder (Serializable serItem) {
        super();
        try {
            //create a byte array output stream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(serItem);
            setData(baos.toByteArray());
            baos.close();
            oos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object readResolve() throws ObjectStreamException {
        BaseProxiableClass  returnValue = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            returnValue = ( BaseProxiableClass ) ois.readObject();
            bais.close();
            ois.close();
            returnValue =  null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnValue;
    }
}