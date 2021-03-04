package andy.com.algorithm1.leetcode;


import org.junit.Test;

public class Q5 {

    public static void find(String s){

        boolean [][] dp = new boolean [s.length()][s.length()];

        /**
         * 对角线初始化为True
         */
        for(int i = 0; i < s.length(); i++){
            dp[i][i] = true;
        }

        String result = "";
        for(int length = 1; length < s.length()-1;length++){
            for(int i = 0; i < s.length()-1;i++){
                int start = i;
                int end = i+length+1;
                if(end >= s.length()){
                    continue;
                }

                if(s.substring(start,start+1).equals(s.substring(end,end+1)) && start+1<=end-1  && dp[start+1][end-1]){
                    dp[start][end] = true;
                    if(end-start+1 > result.length()){
                        result = s.substring(start,end+1);
                    }
                }
                else{
                    dp[start][end] = false;
                }
            }
        }

        System.out.println("result:"+result);
    }

    @Test
    public void test(){
        String s = "11aba32";
        find(s);
    }
}
