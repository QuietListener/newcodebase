package andy.com.socket.protocal;

import andy.com.socket.protocal.compress.Compressor;
import andy.com.socket.protocal.compress.GzipCompressor;
import andy.com.socket.protocal.compress.SnappyCompressor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * 消息头:
 * 1个byte做为type
 * 1个byte做为version
 * 1个byte作为压缩算法
 * 1个byte作为保留字
 * 2个byte做为length
 */
public class Frame {

    public static final int MIN_COMPRESS_SIZE = 512;
    public static final int HEAD_LENGTH = 6;
    public static final byte COMPRESS_GZIP = 0x1;
    public static final byte COMPRESS_SNAPPY = 0x2;
    public static final byte COMPRESS_NONE = 0x0;

    byte[] head = new byte[6];
    byte[] data = null;
    /**
     * 数据原始大小 包括头
     */
    private int originFrameLength = -1;

    /**
     * 压缩后的大小 包括头
     */
    private int comprredFrameLength = -1;

    private Compressor cgzip = new GzipCompressor();
    private Compressor snappy = new SnappyCompressor();

    private Frame() {
    }

    private Frame(String s, byte compressType) throws IOException {
        this(s.getBytes(), compressType);
    }

    private Frame(byte[] bs, byte compressType) throws IOException {

        head[0] = 1; //type1 data;
        head[1] = 1; //version 1
        head[2] = compressType; //压缩算法
        head[3] = 0; //保留

        data = bs;
        this.originFrameLength = data.length + head.length;
        System.out.println("data.length = " + data.length);

        if (head[2] != COMPRESS_NONE) {
            if (head[2] == COMPRESS_GZIP) {
                data = cgzip.compress(data);
                System.out.println("cdata.length = " + data.length);
            } else {
                data = snappy.compress(data);
            }
        }

        int length = data.length;

        this.comprredFrameLength = this.head.length + data.length;

        byte[] lbytes = PUtils.intToByteArray(length);

        //长度2个字节
        head[4] = lbytes[2];
        head[5] = lbytes[3];

    }


    public byte[] getBytes() {
        return ArrayUtils.addAll(head, data);
    }

    public byte[] getHead() {
        return head;
    }


    public byte[] getData() {
        return data;
    }

    public int getOriginFrameLength() {
        return originFrameLength;
    }

    public int getComprredFrameLength() {
        return comprredFrameLength;
    }

    public void setComprredFrameLength(int comprredFrameLength) {
        this.comprredFrameLength = comprredFrameLength;
    }

    public static Frame encode(String s) throws IOException {
        byte c = COMPRESS_NONE;
//        if(s.length() > MIN_COMPRESS_SIZE){
//            c = COMPRESS_GZIP;
//        }

        return new Frame(s, c);
    }

    public static Frame encode(byte[] bs) throws IOException {

        byte c = COMPRESS_NONE;
//        if(bs.length > MIN_COMPRESS_SIZE){
//            c = COMPRESS_GZIP;
//        }

        return new Frame(bs, c);
    }


    public static Frame decode(byte[] bs) throws IOException, FrameNeedMoreBytesException {
        Frame f = new Frame();
        f.head = Arrays.copyOfRange(bs, 0, 6);
        byte[] lengthBytes = new byte[]{0, 0, f.head[4], f.head[5]};
        int length = PUtils.byteArrayToInt(lengthBytes);

        if (length + HEAD_LENGTH > bs.length) {
            throw new FrameNeedMoreBytesException();
        }

        f.data = Arrays.copyOfRange(bs, HEAD_LENGTH, length + HEAD_LENGTH);
        f.comprredFrameLength = f.head.length + f.data.length;

        if (f.head[2] != COMPRESS_NONE) {
            if (f.head[2] == COMPRESS_GZIP) {
                f.data = f.cgzip.uncompress(f.data);
            } else if (f.head[2] == COMPRESS_SNAPPY) {
                f.data = f.snappy.uncompress(f.data);
            }

        }
        f.originFrameLength = f.head.length + f.data.length;

        return f;
    }

//    public static void main(String[] args) throws Exception {
//
//        List<String> strs = Arrays.asList("aaaaaaad3222asssssssssssssssssfdfdfasd22222222222222ddd", "bbb232asdfas3bdccc", "eeee11212345");
//
//        int total_length = 0;
//        ByteOutputStream bo = new ByteOutputStream();
//        for (int i = 0; i < strs.size(); i++) {
//            Frame ff = Frame.encode(strs.get(i));
//            bo.write(ff.head);
//            bo.write(ff.data);
//            total_length += ff.head.length;
//            total_length += ff.data.length;
//        }
//
//
//        byte[] bs = bo.getBytes();
//        ByteInputStream bi = new ByteInputStream(bs, total_length);
//
//
//        byte[] buf = new byte[20];
//        byte[] databuf = null;
//        int size = 0;
//        List<Frame> fs = new ArrayList<>();
//        while ((size = bi.read(buf)) > 0) {
//            if (databuf == null) {
//                databuf = Arrays.copyOfRange(buf, 0, size);
//            } else {
//                databuf = ArrayUtils.addAll(databuf, buf);
//            }
//
//            try {
//                Frame f1 = Frame.decode(databuf);
//                int length = f1.getComprredFrameLength();
//                fs.add(f1);
//                databuf = Arrays.copyOfRange(databuf, length, databuf.length);
//            } catch (FrameNeedMoreBytesException e) {
//                System.out.println("need more");
//            }
//        }
//
//
//        for (int i = 0; i < fs.size(); i++) {
//            Frame f2 = fs.get(i);
//            String s = new String(f2.getData());
//            System.out.println(s + ":" + s.equals(strs.get(i)));
//        }
//
//
//    }
}
