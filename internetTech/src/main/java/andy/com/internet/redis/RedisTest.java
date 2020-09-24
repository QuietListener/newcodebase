package andy.com.internet.redis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedisTest {

    List<String> chars = Stream.of("abcdefghujklmdds()()8788nopqrstuvwxyz12365428181818181818asf8999990000000adsfasdfasdalsjfal;sdkjfa;sdjfa;sdfja;sjf".split("")).collect(Collectors.toList());

    @Test
    public void test(){
        System.out.println(chars);
    }

    @Test
    public void write(){
        String ip = "172.16.10.12";
        int port = 6379;
        JedisManager jedisManager = new JedisManager();
        jedisManager.init(ip,port);

        for(int i = 1; i < 300000;i++){
            Collections.shuffle(chars);
            String value = chars.stream().collect(Collectors.joining("_"));
            try {
                jedisManager.set("key" + i, value, 10000000);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(i%100==0) {
                System.out.println(String.format("i = %s ",i));
            }
        }

        jedisManager.destroy();
    }



    @Test
    public void writeSentinel(){

        JedisSentinelManager jedisManager = new JedisSentinelManager();
        jedisManager.init();

        for(int i = 1; i < 300000;i++){
            Collections.shuffle(chars);
            String value = chars.stream().collect(Collectors.joining("_"));
            try {
                jedisManager.set("key" + i, value, 1000000);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(i%100==0) {
                System.out.println(String.format("i = %s ",i));
            }
        }

        jedisManager.destroy();
    }

}
