package andy.com.algorithm1.dynamicprogramming;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class LCS {

    /**
     * 最长公共子序列
     * @param l1
     * @param l2
     */
    public static void lcs(char[] l1, char[] l2){
        int m = l1.length+1;
        int n = l2.length+1;
        int [][]lcsMatrix = new int [m][n];

        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++)
            {
                if(l1[i-1] == l2[j-1]){
                    lcsMatrix[i][j] = lcsMatrix[i-1][j-1]+1;
                    //System.out.println(l1[i-1]);
                }
                else{
                    lcsMatrix[i][j] = Math.max(lcsMatrix[i-1][j],lcsMatrix[i][j-1]);
                }
            }
        }

        System.out.println(lcsMatrix[l1.length][l2.length]);
    }


    /**
     * 最长公共子串
     * @param l1
     * @param l2
     */
    public static void lcs1(char[] l1, char[] l2){
        int m = l1.length+1;
        int n = l2.length+1;
        int [][]lcsMatrix = new int [m][n];

        int maxI = 0;
        int maxJ = 0;
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++)
            {
                if(l1[i-1] == l2[j-1]){
                    lcsMatrix[i][j] = lcsMatrix[i-1][j-1]+1;
                    //System.out.println(l1[i-1]);
                    if(lcsMatrix[i][j]>lcsMatrix[maxI][maxJ]){
                        maxI = i-1;
                        maxJ = j-1;
                    }
                }
                else{
                    lcsMatrix[i][j] = 0;
                }
            }
        }

        System.out.println(lcsMatrix[maxI][maxJ]);
        System.out.println(String.valueOf(l1).substring(maxI-lcsMatrix[maxI][maxJ],maxI));
    }

    @Test
    public void test(){
        String a = "ab1abcd2aa";
        String b = "abcd1ab1";

        lcs(a.toCharArray(),b.toCharArray());
        System.out.println("------------------------");
        lcs1(a.toCharArray(),b.toCharArray());

        String s1 = "babad";
        String s2 = new StringBuffer(s1).reverse().toString();

        System.out.println(s1);
        System.out.println(s2);
        lcs1(s1.toCharArray(),s2.toCharArray());


    }


    @Test
    public void test1(){
        //System.out.println(lcs_("aab","bbabb"));
        System.out.println(lcs_1("aabac","bbabbaccc"));
    }

    public static int lcs_(String s1, String s2){
        String[] ss1 = s1.split("");
        String[] ss2 = s2.split("");

        int [][] dp  = new int[2][ss2.length];

        int max = 0;
        ArrayList<Integer> set = new ArrayList<>();
        for(int i = 0; i < ss1.length; i++){
            for(int j = 0; j < ss2.length; j++){
                if(ss1[i].equals(ss2[j])){
                    int pre = i-1>=0 && j-1>=0 ? dp[(i-1)%2][(j-1)%2] : 0;
                    dp[i%2][j%2] = pre+1;
                    set.add(i);
                }else{
                    int pre1 = i-1>=0 ? dp[(i-1)%2][j%2] : 0;
                    int pre2 = j-1>=0 ? dp[i%2][(j-1)%2] : 0;

                    dp[i%2][j%2] = Math.max(pre1,pre2);
                }
            }
        }

        System.out.println(set.stream().map(t->t+"").collect(Collectors.joining(",")));
        return set.size();
    }

    public static int lcs_1(String s1, String s2){
        String[] ss1 = s1.split("");
        String[] ss2 = s2.split("");

        int [][] dp  = new int[ss1.length][ss2.length];

        int max = 0;
        List<Integer> set = new ArrayList<>();
        for(int i = 0; i < ss1.length; i++){
            for(int j = 0; j < ss2.length; j++){
                if(ss1[i].equals(ss2[j])){
                    int pre = i-1>=0 && j-1>=0 ? dp[(i-1)][(j-1)] : 0;
                    dp[i][j] = pre+1;
                    if(dp[i][j] > max){
                        max = dp[i][j];
                        set.add(j);
                    }

                }else{
                    int pre1 = i-1>=0 ? dp[(i-1)][j] : 0;
                    int pre2 = j-1>=0 ? dp[i][(j-1)] : 0;

                    dp[i][j] = Math.max(pre1,pre2);
                }
            }
        }
        System.out.println(set.stream().map(t->t+"").collect(Collectors.joining(",")));
        return   dp[ss1.length-1][ss2.length-1];
    }
}
