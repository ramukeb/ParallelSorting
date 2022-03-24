package ass1;

import java.util.Random;

import org.junit.jupiter.api.Test;


public class TestLong {

    public static final Long[][] dataset={
            {new Long("999101101"),new Long("999101201"),new Long("999101301"),new Long("999101401"),new Long("999101501")},
            {new Long("999191101"),new Long("999181201"),new Long("999171301"),new Long("999161401"),new Long("999151501")},
            {new Long("-999101101"),new Long("-999101201"),new Long("999101301"),new Long("-999101401"),new Long("999101501")},
            {new Long("0"),new Long("-999101201"),new Long("1"),new Long("-999101401"),new Long("999101501"), new Long("-1")},
            {},
            manyOrdered(10000),
            manyReverse(10000),
            manyRandom(10000)
    };
    static private Long[] manyRandom(int size) {
        Random r=new Random(0);
        Long[] result=new Long[size];
        for(int i=0;i<size;i++){result[i]=r.nextLong();}
        return result;
    }
    static private Long[] manyReverse(int size) {
        Long[] result=new Long[size];
        for(int i=0;i<size;i++){result[i]=(size-i)+420000000000L;}
        return result;
    }
    static private Long[] manyOrdered(int size) {
        Long[] result=new Long[size];
        for(int i=0;i<size;i++){result[i]=i+360000000000000L;}
        return result;
    }

    @Test
    public void testISequentialSorter() {
        Sorter s=new ISequentialSorter();
        for(Long[]l:dataset){TestHelper.testData(l,s);}
    }

    @Test
    public void testMSequentialSorter() {
        Sorter s=new MSequentialSorter();
        for(Long[]l:dataset){TestHelper.testData(l,s);}
    }
    @Test
    public void testMParallelSorter1() {
        Sorter s=new MParallelSorter1();
        for(Long[]l:dataset){TestHelper.testData(l,s);}
    }
    @Test
    public void testMParallelSorter2() {
        Sorter s=new MParallelSorter2();
        for(Long[]l:dataset){TestHelper.testData(l,s);}
    }
    @Test
    public void testMParallelSorter3() {
        Sorter s=new MParallelSorter3();
        for(Long[]l:dataset){TestHelper.testData(l,s);}
    }
}
