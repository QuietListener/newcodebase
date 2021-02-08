package andy.com.algorithm1.beautify_program.part1;

import andy.com.algorithm1.utils.AlgorithmUtils;

/**
 * 象棋问题
 */
public class Q1_3 {

    public static void main(String [] args){
            int [] pies = new int[]{1,2,3,4};
             AlgorithmUtils.print(flip(pies,0,pies.length-2));
    }

    public static int [] flip(int [] pies, int start, int end){
         int tmp = (end -start)/2;
         for(int i=0; i<= tmp ; i++){
                int t = pies[start+i];
                pies[start+i] = pies[end -i];
                pies[end-i] = t;
         }

         return pies;
    }

}
