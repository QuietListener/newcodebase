package andy.com.jvm.chapter2;

/**
 * 虚拟机栈溢出
 * -Xss128k 控制虚拟机栈的大小
 */
public class StackFlowOver {

    int deepth = 0;
    public void stackCall(){
            deepth += 1;
            stackCall();
    }

    public static void main(String[] args) throws Exception {
        StackFlowOver s = new StackFlowOver();
        try {
            s.stackCall();

        }catch (Throwable e){
            System.err.println("s.deepth = "+s.deepth);
            e.printStackTrace();
        }
    }
}
