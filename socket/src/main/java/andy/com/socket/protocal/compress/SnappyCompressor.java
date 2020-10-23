package andy.com.socket.protocal.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

public class SnappyCompressor implements Compressor {


    /**
     * 压缩指定的字符串
     *
     * @param bs
     * @return
     * @throws IOException
     */
    public byte[] compress(byte[] bs) throws IOException {
        long s = System.currentTimeMillis();
        byte [] r =  Snappy.compress(bs);
        System.out.println(String.format( "snappy_compress: %s bytes ,cost = %s ms",bs.length,System.currentTimeMillis()-s));
        return r;
    }

    /**
     * 解压缩字节数组
     *
     * @param bs
     * @return
     * @throws IOException
     */
    public byte[] uncompress(byte[] bs) throws IOException {
        long s = System.currentTimeMillis();
        byte [] r = Snappy.uncompress(bs);
        System.out.println(String.format( "snappy_uncompress: %s bytes ,cost = %s ms",bs.length,System.currentTimeMillis()-s));
        return r;
    }


}
