package andy.com.jvm.charpter3;

/**
 * 测试内存分配
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *  堆大小为20M 10M分配给新生代，剩下10M是老年代 Eden:survivor1:survivor2=8:1:1
 *
 *  执行testAllocation()中分配allocation4对象的语句时会发生一次MinorGC，
 *  这次回收的结果是新生代6651KB变为148KB，而总内存占用量则几乎没有减少
 *  （因为allocation1、2、3三个对象都是存活的，虚拟机几乎没有找到可回收的对象）。
 *  产生这次垃圾收集的原因是为allocation4分配内存时，发现Eden已经被占用了6MB，
 *  剩余空间已不足以分配allocation4所需的4MB内存，因此发生MinorGC
 *  。垃圾收集期间虚拟机又发现已有的三个2MB大小的对象全部无法放入Survivor空间
 *  （Survivor空间只有1MB大小），所以只好通过分配担保机制提前转移到老年代去。
 *  这次收集结束后，4MB的allocation4对象顺利分配在Eden中。
 *  因此程序执行完的结果是Eden占用4MB（被allocation4占用），Survivor空闲，
 *  老年代被占用6MB（被allocation1、2、3占用）。通过GC日志可以证实这一点。
 *
 */
public class TestAllocation {
    public static final int _1MB = 1024*1024;
    public static void main(String [] args){
        byte[] allocation1,allocation2,allocation3,allocation4;

        allocation1=new byte[2*_1MB];
        allocation2=new byte[2*_1MB];
        allocation3=new byte[2*_1MB];
        allocation4=new byte[4*_1MB];//出现一次MinorGC

    }

}
