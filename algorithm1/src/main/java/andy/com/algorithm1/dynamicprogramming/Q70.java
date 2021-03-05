package andy.com.algorithm1.dynamicprogramming;

import org.junit.Test;

public class Q70 {
    public static int  solution(int n){

        int []dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 2;

        for(int i = 3; i<=n; i++){
            int count1 = dp[i-1]
            int count2 = dp[i-2];
            dp[i] = count1+count2;
        }

        return dp[n];
    }

    @Test
    public void test(){

       System.out.println(solution(6));
    }
}
