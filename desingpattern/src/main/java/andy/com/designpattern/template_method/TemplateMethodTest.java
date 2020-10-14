package andy.com.designpattern.template_method;

/**
 * #### 为什么需要
 * 模板方法模式是类的行为模式。准备一个**抽象类**，将**部分逻辑**以具体方法以及具体构造函数的形式实现，然后**声明一些抽象方法来迫使子类实现剩余的逻辑**。不同的子类可以以不同的方式实现这些抽象方法，从而对剩余的逻辑有不同的实现。这就是模板方法模式的用意。
 * #### 具体
 * 1. 定义了一个或多个抽象操作，以便让子类实现。这些抽象操作叫做基本操作，它们是一个顶级逻辑的组成步骤。
 * <p>
 * 2. 定义并实现了一个模板方法。这个模板方法一般是一个具体方法，它给出了一个顶级逻辑的骨架，而逻辑的组成步骤在相应的抽象操作中，推迟到子类实现。顶级逻辑也有可能调用一些具体方法。
 */
public class TemplateMethodTest {

    /**
     * 定义抽象类(以数据库为例)
     */
    static abstract class AbstractTemplate {


        //已经实现的业务代码
        private void logic1() {
            System.out.println("do logic1");
        }


        // 已经实现的业务代码
        public void logic2() {
            System.out.println("do logic2 ");
        }


        // 需要由子类实现的方法
        protected abstract void userLogic();


        // 模板方法
        public void logic() {
            System.out.println("开始整个逻辑");

            this.logic1();
            this.userLogic();
            this.logic2();

            System.out.println("整个逻辑执行结束");
        }
    }

    /**
     * 强制子类实现 userLogic 逻辑
     */
    static class ConcreteLogic1 extends AbstractTemplate{

        @Override
        protected void userLogic() {
            System.out.println("do a nice logic ");
        }
    }


    /**
     * 强制子类实现 userLogic 逻辑
     */
    static class ConcreteLogic2 extends AbstractTemplate{

        @Override
        protected void userLogic() {
            System.out.println("do a wonderful logic ");
        }
    }

    public static void main(String[] args) {

        AbstractTemplate l1 = new ConcreteLogic1();
        l1.logic();
        AbstractTemplate l2 = new ConcreteLogic2();
        l2.logic();

    }

}
