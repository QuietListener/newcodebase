package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Q415 {

    public String addStrings(String num1, String num2) {
        int len1 = num1.length()-1;
        int len2 = num2.length()-1;

        LinkedList<Integer> list = new LinkedList<>();
        list.add(0);
        while(len1 >=0 && len2 >= 0){
            int int1 = Integer.valueOf(num1.charAt(len1)+"");
            int int2 = Integer.valueOf(num2.charAt(len2)+"");
            int sum = int1+int2+list.get(0);
            int tmp1 = sum%10;
            int tmp2 = sum/10;

            list.set(0,tmp1);
            list.add(0,tmp2);
            len1--;
            len2--;
        }


        while(len1 >=0){
            int int1 = Integer.valueOf(num1.charAt(len1)+"");

            int sum = int1+list.get(0);
            int tmp1 = sum%10;
            int tmp2 = sum/10;
            list.set(0,tmp1);
            list.add(0,tmp2);
            len1--;
        }

        while(len2 >= 0){

            int int2 = Integer.valueOf(num2.charAt(len1)+"");
            int sum = int2+list.get(0);
            int tmp1 = sum%10;
            int tmp2 = sum/10;

            list.set(0,tmp1);
            list.add(0,tmp2);
            len2--;
        }

         list.remove(0);
        return list.stream().map(t->t.toString()).collect(Collectors.joining(""));
    }


    @Test
    public void test1(){
      String ret = new Q415().addStrings("2234","88");
      System.out.println(ret);
      System.out.println();

      Integer a = 1;
      Integer b = 1;
      System.out.println(a == b);

      Integer a1 = 1111;
      Integer b1 = 1111;
      System.out.println(a1 == b1);

      String a2 = "a";
      String b2 = "a";
        System.out.println(a2 == b2);
    }
}
