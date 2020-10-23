package andy.com.socket.protocal.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompressor implements Compressor {


    /**
     * 压缩指定的字符串
     *
     * @param str
     * @return
     * @throws IOException
     */
    public byte[] compress(byte[] bs) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(bs);
        gzip.close();
        return out.toByteArray();
    }

    /**
     * 解压缩字节数组
     *
     * @param b
     * @return
     * @throws IOException
     */
    public byte[] uncompress(byte[] b) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }


}
