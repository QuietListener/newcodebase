package andy.com.algorithm1.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main5 {

    public static int N = 62;
    public static String [] NS = new String[]{"0","1","2","3","4","5","6"};

    public int map2Int(String n){
        return -1;
    }

    public String map2String(int num){
        return null;
    }

    public String add62(String n1,String n2){

        List<String> ret = new ArrayList<>();

        String [] n1Split = n1.split("");
        String [] n2Split = n2.split("");

        int i = n1Split.length-1;
        int j = n2Split.length-1;
        int adder = 0; //进位
        while(i>=0 && j>=0){
            String curN1 = n1Split[i];
            String curN2 = n2Split[j];

            int n1Cur = map2Int(curN1);
            int n2Cur = map2Int(curN2);

            int num = n1Cur+n2Cur+adder;
            int cur = num%N;
            adder = cur/N;
            String curNum = map2String(cur);
            ret.add(0,curNum);
            i--; j--;
        }


        while(i>=0){
            String curN1 = n1Split[i];
            int n1Cur = map2Int(curN1);
            int num = n1Cur+adder;
            int cur = num%N;
            adder = cur/N;
            String curNum = map2String(cur);
            ret.add(0,curNum);
            i--;
        }

        while(j>=0){
            String curN2 = n2Split[j];
            int n2Cur = map2Int(curN2);
            int num = n2Cur+adder;
            int cur = num%N;
            adder = cur/N;
            String curNum = map2String(cur);
            ret.add(0,curNum);
            j--;
        }

        return ret.stream().collect(Collectors.joining(""));
    }


    public static void main(String [] args){

    }

}
