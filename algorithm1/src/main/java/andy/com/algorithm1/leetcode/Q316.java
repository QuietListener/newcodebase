package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Q316 {

   public static String  solution(String s){
       String [] ss = s.split("");

       //统计每个字母个数
       Map<String ,Boolean> mapVisted = new HashMap<>();
       Map<String ,Integer> map = new HashMap<>();
       for(String s1: ss){
            Integer count = map.get(s1);
            if(count == null){
                count = 0;
            }

            count++;
            map.put(s1,count);
            mapVisted.put(s1,false);
       }



       Stack<String> stack = new Stack();
       for(String s2: ss){
           if(stack.empty()){
               stack.push(s2);
               continue;
           }

           if(stack.contains(s2)){

               Integer count = map.get(s2);
               count--;
               map.put(s2,count);
               continue;
           }

           String topOne = stack.peek();
           char topChar = topOne.charAt(0);
           char curChar = s2.charAt(0);
           Integer count = map.get(topOne);
           while(curChar<topChar){

               if(map.get(topOne) > 1){
                   stack.pop();
                   count--;
                   map.put(topOne,count);
               }
               else{
                   break;
               }

               if(stack.empty()){
                    break;
               }

               topOne = stack.peek();
               topChar = topOne.charAt(0);
               count = map.get(topOne);
           }

           stack.push(s2);

       }

       return stack.stream().collect(Collectors.joining(""));
   }

    @Test
    public void test(){

       System.out.println(solution("bbcaac"));
    }
}

