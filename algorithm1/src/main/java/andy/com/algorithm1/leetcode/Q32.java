package andy.com.algorithm1.leetcode;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Q32 {

    public static int solution(String s){
       String [] ss = s.split("");

       Stack<Integer> stack = new Stack<>();
       int  count = 0;
       int maxCount = 0;

       int tmpCount = 0;
       List<Integer> maxCounts = new ArrayList<>();
       for(int i = 0; i < ss.length; i++){
           String s1 = ss[i];
            if(stack.empty()){
                stack.push(i);
                continue;
            }


            String top = ss[stack.peek()];
            if(top.equals(")") && s1.equals("(")  || top.equals(")") && s1.equals(")") ){
                stack.clear();

                stack.push(i);


            }else if(top.equals("(")&& s1.equals("(") ){
                stack.push(i);
            }else  if(top.equals("(")&& s1.equals(")") ){
                int curCount = i-stack.peek()+1;
                if(maxCount < curCount){
                    maxCount = curCount;
                }
                stack.pop();
            }

       }

       return maxCount;
    }


    @Test
    public void test(){
        System.out.println(solution( ")()())"));
    }
}
