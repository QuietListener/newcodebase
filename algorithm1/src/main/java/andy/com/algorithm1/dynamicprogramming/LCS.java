package andy.com.algorithm1.dynamicprogramming;

import org.junit.Test;

import java.util.LinkedHashMap;

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
}
