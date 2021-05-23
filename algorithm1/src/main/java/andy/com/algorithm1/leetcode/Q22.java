package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Q22 {

    /**
     *
     * @param list
     * @param left 作括号个数
     * @param right 有括号个数
     */
    public void dfs(List<String> list, String str, int left, int right){
        if(left == 0 && right == 0){
            list.add(str);
            return;
        }

        if(left >  right){
            return;
        }

        if(left <= right ){
            if(left>=1){
                dfs(list,str+"(",left-1,right);
            }
            if(right >= 1){
                dfs(list,str+")",left,right-1);
            }
        }
    }

    @Test
    public void test(){
      List<String> list =new ArrayList<>();
      dfs(list,"",3,3);
      System.out.println(list);
    }
}
