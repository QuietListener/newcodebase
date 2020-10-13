package andy.com.designpattern.type_interface.adapter;



/**
 * 类的适配器模式
 * 模式所涉及的角色有：
 * 1. 目标(Target)角色：这就是所期待得到的接口。注意：由于这里讨论的是类适配器模式，因此目标不可以是类。
 * 2. 源(Adapee)角色：现在需要适配的接口。
 * 3. 适配器(Adaper)角色：适配器类是本模式的核心。适配器把源接口转换成目标接口。显然，这一角色不可以是接口，而必须是具体类。
 */
public class AdaptorClassTest {


    /**
     * 现有的类
     */
    static class Adaptee {
        /**
         * 已经有的方法
         *
         * @return
         */
        public void existsMethod() {
            System.out.println("已有的方法");
        }
    }


    /**
     * 现在需要的接口
     */
    static interface Target {
        /**
         * 原adaptee有的方法
         */
        public void existsMethod();

        /**
         * 原adaptee没有的方法
         */
        public void newMethod();
    }

    static class Adapter extends Adaptee implements Target {

        @Override
        public void newMethod() {
            System.out.println("新的方法");
        }
    }


    public static void main(String[] args) {
        Target t = new Adapter();
        t.existsMethod();//调用老方法
        t.newMethod();//调用新方法
    }

}