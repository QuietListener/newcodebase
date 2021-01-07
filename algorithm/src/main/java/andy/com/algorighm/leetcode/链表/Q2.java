package andy.com.algorighm.leetcode.链表;

import andy.com.algorighm.leetcode.hash.Q387;

public class Q2 {
    public static void main(String [] args){

        int result = new Q387().firstUniqChar("loveleetcode");
        System.out.println(String.format("%s",result));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode pre = null;
        ListNode ret = null;
        int nextRoundAdd = 0;
        while(true){
            int num1 = l1 == null?0:l1.val;
            int num2 = l2 == null?0:l2.val;


            int sum = num1+num2+nextRoundAdd;
            int newNum = sum%10;
            nextRoundAdd = sum == newNum ? 0 : 1;

            ListNode curNode = new ListNode(newNum,null);
            if(pre == null) {
                pre = curNode;
                ret = curNode;
            }else {
                pre.next = curNode;
                pre = curNode;
            }

            if(l1 != null)
                l1 = l1.next;
            if(l2 != null)
                l2 = l2.next;

            if(l1 == null && l2 == null && nextRoundAdd == 0){
                break;
            }

        }
        return ret;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
