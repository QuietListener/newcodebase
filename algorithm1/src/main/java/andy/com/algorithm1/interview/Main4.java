package andy.com.algorithm1.interview;

import java.util.Arrays;
import java.util.List;

public class Main4 {

    private static class Node{
        public String value;
        public Node next;
    }

    public static boolean solution(Node root){
        Node slow = root;
        Node fast = root;
        boolean isOdd = false; //判断长度是不是奇数
        while(fast!=null && fast.next != null){
            slow = slow.next;
            fast = fast.next;
            if(fast != null){
                fast = fast.next;
            }else{
                isOdd = true;
            }
        }

        Node middleNode = null;
        Node tmpMiddleNode = null;
        if(isOdd){
            middleNode = slow.next;
            tmpMiddleNode = slow;
        }else{
            middleNode = slow;
            tmpMiddleNode = slow;
        }


        while(root != tmpMiddleNode ){ //逐个比较元素
            if(!root.value.equals(middleNode.value)){
                return false;
            }

            root = root.next;
            middleNode = middleNode.next;
        }
        return true;
    }


    public static Node createLinkedList(List<String> strs){
        Node root = null;
        Node cur = null;
        Node pre = cur;
        for(int i = 0; i < strs.size();i++){
            cur = new Node();
            if(root == null){
                root = cur;
            }

            cur.value = strs.get(i);
            cur.next = null;

            if(pre != null){
                pre.next = cur;
            }else{
                pre = cur;
            }


            pre = cur;
        }

        return  root;
    }


    public static void main(String [] args){

        List<String> test1 = Arrays.asList("a","b","a");
        Node root1 = createLinkedList(test1);
        while(root1 != null){
            System.out.println(root1.value);
            root1 = root1.next;
        }

        boolean ret1 = solution(root1);
        System.out.println(ret1);

        List<String> test2 = Arrays.asList("a","b","c");
        Node root2 = createLinkedList(test2);
        Node root2Tmp = root2;
        while(root2 != null){
            System.out.println(root2.value);
            root2 = root2.next;
        }
        boolean ret2 = solution(root2Tmp);
        System.out.println(ret2);
    }

}
