package andy.com.algorithm1.beautify_program.part1;

import andy.com.algorithm1.utils.AlgorithmUtils;

/**
 * 象棋问题
 */
public class Q1_3 {

    public static void main(String [] args){

            int [] pies = new int[]{1,2,3,4,7,9};
            int count = 0;
            for(int i = pies.length - 1; i>=0 ;i--){
                int maxIdx = maxIdx(pies,0,i);

                if(ok(pies,0,i)){
                    break;
                }
                flip(pies,0,maxIdx);
                flip(pies,0,i);
                count+=2;
            }

            AlgorithmUtils.print(pies);
            System.out.println("count = "+count);
    }

    public static int maxIdx(int [] pies,int start, int end){
        int max = start;
        for(int i = start; i <= end; i++){
            if(pies[i]>pies[max]){
                max = i;
            }
        }

        return max;
    }

    public static boolean ok(int [] pies,int start, int end ){
        for(int i = start; i <= end-1; i++){
            if(pies[i] > pies[i+1]){
                return false;
            }
        }

        return true;
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
