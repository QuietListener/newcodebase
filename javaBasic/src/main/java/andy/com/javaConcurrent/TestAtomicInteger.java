package andy.com.javaConcurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class TestAtomicInteger {
    AtomicInteger a = null;
    AtomicStampedReference ar = null;

    Semaphore s;
}
