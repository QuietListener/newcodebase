package andy.com.proxy;

import jdk.internal.org.objectweb.asm.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CglibFinalClass {

    public class MyClassLoader extends ClassLoader {

        public MyClassLoader() {
            //指定父加载器为null
            super(null);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            System.out.println("findClass: "+name);
            try {
                ClassReader reader = new ClassReader(name);
                ClassWriter writer = new ClassWriter(reader, 0);
                RemoveFinalFlagClassVisitor classVisitor = new RemoveFinalFlagClassVisitor(writer);
                reader.accept(classVisitor, ClassReader.SKIP_CODE);
                byte[] bytes = writer.toByteArray();
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    // 利用ClassVisitor去掉final的修饰
    class RemoveFinalFlagClassVisitor extends ClassVisitor {

        public RemoveFinalFlagClassVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            // we have the final flag
            if(  name.equals("andy/com/proxy/FinalClass") &&  ( access & Opcodes.ACC_FINAL) == Opcodes.ACC_FINAL) {
                //remove the final flag
                access = access ^ Opcodes.ACC_FINAL;
            }
//        在调用super.visit的时候，我们就已经把final关键字去掉了
            super.visit(version, access, name, signature, superName, interfaces);
        }


        @Override
        public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        /*可以在这里修改final修饰非方法
        这里可以利用debug查看修饰的特征值
        */
            if (i==17){
                return super.visitMethod(1, s, s1, s2, strings);
            }
            return super.visitMethod(i, s, s1, s2, strings);
        }
    }

    @Test
    public void test() throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = myClassLoader.loadClass("andy.com.proxy.FinalClass");

        System.out.println(Modifier.toString(aClass.getModifiers()));
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(Modifier.toString(method.getModifiers()));
        }


       Object object =  aClass.newInstance();
        aClass.getMethod("test").invoke(object);


        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(aClass);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {


                Object obj =  methodProxy.invokeSuper(o, objects);

                if(method.getName().equals("sayHello")){
                    System.out.println("MethodInterceptor invoke");
                }

                return obj;
            }
        });

        enhancer.create();;
    }
}
