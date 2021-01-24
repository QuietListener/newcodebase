package andy.com.algorighm.leetcode.dp;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class LCS {


    public static String lcs(String s1, String s2){

        s1 = "-"+s1;
        s2 = "-"+s2;
        int s1l = s1.length();
        int s2l = s2.length();

        int [][]c = new int[s1l][s2l];

        for(int i = 0; i < s1l; i++)
            c[i][0] = 0;
        for(int j = 0; j < s2l; j++)
            c[0][j] = 0;

        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer, String>();
        for(int i = 1; i < s1l; i++){
            for(int j = 1; j < s2l; j++){

                String a = s1.substring(i,i+1);
                String b = s2.substring(j,j+1);
                if(a.equals(b)){
                    c[i][j] = c[i-1][j-1]+1;
                    map.put(i,a);
                }else {
                     if( c[i][j-1]  > c[i-1][j]){
                         c[i][j] =  c[i][j-1]  ;
                     }else{
                         c[i][j] = c[i-1][j];
                     }
                }
            }
        }


        return map.values().stream().collect(Collectors.joining(""));
    }



    public static int  lcs1(String s1, String s2){

        s1 = "-"+s1;
        s2 = "-"+s2;
        int s1l = s1.length();
        int s2l = s2.length();

        int [][]c = new int[s1l][s2l];

        for(int i = 0; i < s1l; i++)
            c[i][0] = 0;
        for(int j = 0; j < s2l; j++)
            c[0][j] = 0;

        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer, String>();
        LinkedHashMap<Integer,String> mapResult = new LinkedHashMap<Integer, String>();
        int max= 0;
        String result = "";
        for(int i = 1; i < s1l; i++){
            for(int j = 1; j < s2l; j++){

                String a = s1.substring(i,i+1);
                String b = s2.substring(j,j+1);
                if(a.equals(b)){
                    c[i][j] = c[i-1][j-1]+1;
                    if (c[i][j] > max)
                    {
                        max = c[i][j];
                       String curStr = s1.substring(i-max+1,i+1);
                       if(curStr.length() > result.length()){
                           result = curStr;
                       }
                    }
                }else {
                    c[i][j] =  0;
                }
            }
        }


        System.out.println(result);
        return  max;
    }




    public  static  void main(String [] args){
        String s1 = "1aaj123458i";
        String s2 = "1aak123458d";

        System.out.println(LCS.lcs(s1,s2));

        System.out.println(LCS.lcs1(s1,s2));
    }

}
