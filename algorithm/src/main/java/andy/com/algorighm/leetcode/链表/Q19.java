package andy.com.algorighm.leetcode.链表;


import java.util.ArrayList;
import java.util.List;

public class Q19 {
    public static void main(String [] args){


    }
    public ListNode removeNthFromEnd(ListNode head, int n) {

        List<ListNode> refs = new ArrayList<>();
        ListNode ret = head;

        ListNode cur = head;
        while(cur != null){
            refs.add(cur);
            cur = cur.next;
        }

        int length = refs.size();
        int toDeleted = length - n;

        ListNode todeletedNode = refs.get(toDeleted);

        if(toDeleted == 0){
            ret = todeletedNode.next;
        }else{
            ListNode pre = refs.get(toDeleted - 1);
            ListNode next = toDeleted+1 < refs.size()? refs.get(toDeleted+1):null;
            pre.next = next;
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
