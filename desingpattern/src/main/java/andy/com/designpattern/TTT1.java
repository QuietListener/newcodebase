package andy.com.designpattern;

import org.junit.Test;


public class TTT {
    public static void main(String []args) {
        System.out.println("aa");
    }


    static class Node{
            public Node next = null;
            public int value = 0;
    }


    public Node mergeSort(Node head){

        int count = 0;
        Node left = head;
        Node right = head;

        while(head.next != null){
            count+=1;
        }

        Node pre = null;
        for(int i = 0; i < count/2; i++){
            pre = right;
            right = right.next;
        }

        pre.next = null; //将链表切成两块。


        Node leftHead  = mergeSort(left); //递归
        Node rightHead = mergeSort(right);

        Node ret = new Node();

        while(leftHead.next != null && rightHead.next != null){
            if(leftHead.value > rightHead.value){
                ret.next = left;
                leftHead = leftHead.next;
            }else{
                ret.next = right;
                rightHead = rightHead.next;
            }
        }

        if(leftHead != null){
            ret.next = leftHead;
        }

        if(rightHead != null){
            ret.next = rightHead;
        }

        return ret.next;
    }


    @Test
    public void test1(){
        System.out.println("aa");
    }
}
