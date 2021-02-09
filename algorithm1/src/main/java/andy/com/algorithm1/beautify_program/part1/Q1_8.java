package andy.com.algorithm1.beautify_program.part1;

import andy.com.algorithm1.utils.AlgorithmUtils;

/**
 * 电梯
 */
public class Q1_8 {

    public static void main(String [] args){
        solution1();
    }

    public static void solution1(){
        int maxIdx = 0;
        int maxEnergy = Integer.MAX_VALUE;
        int [] people = new int[]{2,3,4,1,5,6};
        for(int i = 0; i < people.length; i++){

            int total = 0;
            for(int j = 0; j < people.length;j++){
                total += Math.abs(j-i)*people[j];
            }

            if(total < maxEnergy){
                maxEnergy = total;
                maxIdx = i;
            }

            System.out.println(i+":"+total);
        }

        System.out.println(maxIdx);
    }

}
