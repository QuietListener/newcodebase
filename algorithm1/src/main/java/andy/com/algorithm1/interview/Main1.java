package andy.com.algorithm1.interview;

import org.junit.Test;

public class Main1 {


    public int[] findK(int[] a1, int a2[] , int k ){

        if(a1 == null || a2 == null || k <=0){
            return new int[]{-1,-1};
        }

        int i = 0;
        int j = 0;
        int count = 0;
        while(i < a1.length && j < a2.length){
            if(a1[i] < a2[j]){
                i++;
                count+=1;
                if(count == k){
                    return new int[]{0,i-1};
                }
            }else{
                j++;
                count+=1;
                if(count == k){
                    return new int[]{1,j-1};
                }
            }

        }

        while(i < a1.length ){
                i++;
                count += 1;
                if (count == k) {
                    return new int[]{0, i-1};
                }
            }

        while(j<a2.length){
            j++;
            count+=1;
            if(count == k){
                return new int[]{1,j-1};
            }
        }

        return new int[]{-1,-1};
    }


    @Test
    public void test(){
        int [] a1 = new int []{1,3,5};
        int [] a2 = new int []{2,4,6};
        int [] ret = new Main1().findK(a1,a2,3);
        System.out.println(ret[0]+","+ret[1]);


        a1 = new int []{1,3,5};
        a2 = new int []{1,2,4,6};
        ret = new Main1().findK(a1,a2,3);
        System.out.println(ret[0]+","+ret[1]);
    }

}
