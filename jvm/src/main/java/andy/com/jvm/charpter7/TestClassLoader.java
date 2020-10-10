package andy.com.jvm.charpter7;

import java.io.IOException;
import java.io.InputStream;

public class TestClassLoader {

    public static void main(String[] args) throws Exception {
        ClassLoader myloader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }

                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (
                        IOException e) {
                    throw new ClassNotFoundException(name);
                }

            }
        };

        Object obj1 = myloader.loadClass("andy.com.jvm.charpter7.TestClassLoader").newInstance();

        System.out.println(obj1.getClass());

        //打印false 因为不是一个classLoader加载的
        System.out.println(TestClassLoader.class.isInstance(obj1));

d
        TestClassLoader obj2 = new TestClassLoader();
        System.out.println(obj1.getClass().getClassLoader());
        System.out.println(obj2.getClass().getClassLoader());
    }
}
