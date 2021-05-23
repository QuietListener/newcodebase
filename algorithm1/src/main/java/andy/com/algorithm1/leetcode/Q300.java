package andy.com.algorithm1.leetcode;


import org.junit.Test;

public class Q300 {



    @Test
    public void test(){
         int [] a = new int []{0,1,0,3,2,3};
         int [] dp = new int[a.length];
         dp[0]=1;
         for(int i = 1; i < a.length; i++){

             int max = dp[i-1];
             for(int j = i-1; j>=0;j-- ){
                 if(a[i]>a[j]){
                    int tmp = dp[j]+1;
                    if(tmp > max){
                        max = tmp;
                    }
                 }
             }

             dp[i] = max;
         }

         System.out.println(dp[a.length-1]);
    }
}
