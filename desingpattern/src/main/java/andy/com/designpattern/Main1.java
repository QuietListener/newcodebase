package andy.com.designpattern;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main1 {

    public static void main(String []args) {
        BufferedReader stdin =
                new BufferedReader(
                        new InputStreamReader(System.in));

        try{
            String line = stdin.readLine();
            while(line != null && !line.trim().equals("")){
                String [] numsS = line.split(" ");
                float total = 0;
                for(int i = 0; i < numsS.length; i++){
                    numsS[i] = "";
                }

            }

        }catch (Exception e){

        }

    }

    public void test(){
        int m;
        double sum,n;
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            n=sc.nextInt();
            m=sc.nextInt();
            sum=0;
            for(int i=0;i<m;i++){
                sum=sum+n;
                n=Math.sqrt(n);
            }
            System.out.printf("%.2f",sum);
            System.out.println();
        }
    }


}
