import org.junit.*;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

public class Tester {
    int len = 16;
    private StringsSort strSort = new StringsSort();

    @Test
    public void test1() {

        String [] array = init(new String[100000]);
        getTime(array);
    }

    @Test
    public void test2() {
    	String[] array = init(new String[200000]);
        getTime(array);
    }

    @Test
    public void test3() {
    	String[] array = init(new String[300000]);
        getTime(array);
    }

    @Test
    public void test4() {
    	String[] array = init(new String[400000]);
        getTime(array);
    }

    @Test
    public void test5() {
    	String[] array = init(new String[500000]);
        getTime(array);
    }

    @Test
    public void test6() {
        String [] array = init(new String[600000]);
        getTime(array);
    }
    @Test
    public void test7() {
        String [] array = init(new String[700000]);
        getTime(array);
    }
    @Test
    public void test8() {
        String [] array = init(new String[800000]);
        getTime(array);
    }
    @Test
    public void test9() {
        String [] array = init(new String[900000]);
        getTime(array);
    }
    @Test
    public void test10() {
        String [] array = init(new String[1000000]);
        getTime(array);
    }
    @Test
    public void test11() {
        String [] array = init(new String[2000000]);
        getTime(array);
    }
    @Test
    public void test12() {
        String [] array = init(new String[4000000]);
        getTime(array);
    }
    @Test
    public void test13() {
        String [] array = init(new String[8000000]);
        getTime(array);
    }

    public String[] init(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = new StringRandom().stringRandom(len);
           // array[i] = new Random().nextBytes(array.length);

        }
        return strings;
    }

    public void getTime(String[] array) {

        long start = System.nanoTime();

        strSort.heapSort(array);

        long end = System.nanoTime();
        print(array, end - start);
    }

    private void print(String[] array, long time) {
        System.out.println("Array der Länge: " + array.length);
        System.out.println("nanosecunden: " + time);
        System.out.println("millisekunden: " + time / 1000000 + "\n");
    }

}
