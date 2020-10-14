package andy.com.designpattern.type_interface.facade;

/**
 * 外观(门面模式)模式
 */
public class FacadeTest {

    static class SystemA{
        public void ops(){
            System.out.println("SystemA Operation");
        }
    }

    static class SystemB{
        public void ops(){
            System.out.println("SystemB Operation");
        }
    }

    static class SystemC{
        public void ops(){
            System.out.println("SystemC Operation");
        }
    }


    /**
     * 外观类
     */
    static public class Facade{

        /**
         * 提供一个统一接口
         */
        public void wrapOperation(){
            SystemA a = new SystemA();
            SystemB b = new SystemB();
            SystemC c = new SystemC();

            a.ops();
            b.ops();
            c.ops();
        }
    }

    public static void main(String[] args) {
        Facade f = new Facade();
        f.wrapOperation();
    }
}
