package andy.com;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestSerialization {

    static class Test implements Serializable{
         public  void  test(){
             Runnable r= () -> System.out.println("hello");
             new Thread(r).start();

        }
    }


    @org.junit.Test
    public void testSerialization(){

        try (//创建一个ObjectOutputStream输出流
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.txt"))) {
            //将对象序列化到文件s
            Test person = new Test();
            oos.writeObject(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
