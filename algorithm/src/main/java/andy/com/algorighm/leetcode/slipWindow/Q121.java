package andy.com.algorighm.leetcode.slipWindow;

public class Q121 {

    public static void main (String [] args) {
        int [] data = {2,1,2,0,1};
        System.out.println(new Q121().maxProfit(data));
    }

    public int maxProfit(int[] prices) {
        int index1 = 0 ;
        for(int i = 0; i < prices.length-1; i++){
            index1 = i;
            if(prices[i] < prices[i+1]){
                break;
            }
        }

        int index2 = 0 ;
        for(int i = prices.length-1; i > 0; i--){
            index2 = i;
            if( prices[i-1] < prices[i]){
                break;
            }
        }

        if(index1>=index2){
            return 0;
        }

        int max = 0;
        for(int i = index1; i <=index2-1; i++ ){
            for(int j = i+1; j <= index2; j++){
                if(prices[j]-prices[i]>max){
                    max = prices[j]-prices[i];
                }
            }
        }

        return max;
    }

    /**
     * 更好的办法
     * @param prices
     * @return
     */
    public int maxProfit1(int[] prices) {
        int ret = 0;
        int minIndex = 0;
        for(int i = 0; i < prices.length  ; i++){
            if(prices[i] < prices[minIndex]){
                minIndex = i;
            }

            if(minIndex != i){
                int tmp = prices[i] - prices[minIndex];
                if(tmp > ret){
                    ret = tmp;
                }
            }
        }

        return ret;
    }

}
