package andy.com.algorithm1.beautify_program.part1;

/**
 * 象棋问题
 */
public class Q1_2 {

    public static void main(String [] args){
        for(int i = 1; i<=9; i ++){
            for(int j = 1; j <=9; j++){
                if(i%3 != j%3){
                    System.out.println("A = "+i+" B= "+j);
                }
            }
        }
    }
}
