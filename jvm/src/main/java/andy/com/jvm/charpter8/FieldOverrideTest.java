package andy.com.jvm.charpter8;

public class FieldOverrideTest {
    static class Father{
        public int money = 1;
        public Father(){
            money = 2;
            showMeTheMoney();
        }

        public void showMeTheMoney(){
            System.out.println("I am father ,money = "+this.money);
        }
    }

    static class Son extends Father{
        public int money = 3;
        public Son(){
            money = 4;
            showMeTheMoney();
        }

        public void showMeTheMoney(){
            System.out.println("I am son ,money = "+this.money);
        }
    }

    public static void main(String [] args){
        Father f = new Son();
        System. out. println(" This gay has $" + f. money);

    }
}
