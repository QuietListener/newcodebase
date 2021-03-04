package andy.com.algorithm1.leetcode;


import org.junit.Test;



public class Q141 {

    public boolean hasCycle(ListNode head) {

        ListNode fast = head;
        ListNode slow = head;
        boolean flag = false;
        while(slow != null && head != null){

            //快指针追上了慢指针 有环
            if(slow == fast && flag == true){
                return true;
            }

            slow = slow.next;
            if(fast!=null && fast.next != null){
                fast = fast.next.next;
            }else{
                return false;
            }
            flag = true;
        }

        return false;
    }
}


class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}
