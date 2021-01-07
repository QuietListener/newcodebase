package andy.com.algorighm.leetcode.链表;

import java.util.*;

public class Q138{
    public static void main(String [] args){


    }
    public Node copyRandomList(Node head) {
        Node n = head;

        Map<Integer,Integer> map =  new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        Node newHead = null;
        Node pre = null;

        int count = 0;
        while(n != null){
            Node curNode = new Node(n.val);
            curNode.next = null;
            curNode.random = null;
            if(pre != null){
                pre.next = curNode;
            }
            pre = curNode;

            if(newHead == null){
                newHead = curNode;
            }

            map.put(n.hashCode(),count);
            nodes.add(newHead);
            n = n.next;
            count++;
        }


        Node nn = head;
        Node curNode = newHead;
        while(nn != null){
            Node r = nn.random;
            if(r != null){
               int index =  map.get(r.hashCode());
               Node  rand = nodes.get(index);
               curNode.next = rand;
            }

            nn = nn.next;
            curNode = curNode.next;
        }


        return newHead;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
