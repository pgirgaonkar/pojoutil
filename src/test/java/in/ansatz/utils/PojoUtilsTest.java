package in.ansatz.utils;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PojoUtilsTest {

    TestPojo1 source = new TestPojo1();

    @Before
    public void setup(){
        source = new TestPojo1();
        source.a = 15;
        source.b =-9;
        source.s = "sample string";
        source.d1 = 9.99;
    }

    @SneakyThrows
    @Test
    public void copyProperties() {
        TestPojo2 target = PojoUtils.copyProperties(source,TestPojo2.class);
        assertEquals(target.a, source.a);
        assertEquals(target.b, source.b);
        assertEquals(target.s, source.s);
        assertEquals(target.d1,(source.d1), 0.1);
    }

    @SneakyThrows
    @Test
    public void testCopyProperties() {
        List<TestPojo1> in = new LinkedList<>();
        in.add(source);
        in.add(source);
        List<TestPojo2> out = PojoUtils.copyProperties(in,TestPojo2.class);
        assertEquals(out.size(), in.size());
        assertEquals(out.get(0).b, in.get(0).b);
        assertEquals(out.get(0).s, in.get(0).s);
        assertEquals(out.get(0).d1,(in.get(0).d1), 0.1);

        assertEquals(out.get(1).a, in.get(1).a);
        assertEquals(out.get(1).b, in.get(1).b);
        assertEquals(out.get(1).s, in.get(1).s);
        assertEquals(out.get(1).d1,(in.get(1).d1), 0.1);
    }

    @SneakyThrows
    @Test
    public void copyNonNullProperties() {
        List<TestPojo1> in = new LinkedList<>();
        source.a = null;
        in.add(source);
        in.add(source);
        List<TestPojo2> out = PojoUtils.copyNonNullProperties(in,TestPojo2.class);
        assertEquals(out.size(), in.size());
        assertEquals(out.get(0).b, in.get(0).b);
        assertEquals(out.get(0).s, in.get(0).s);
        assertEquals(out.get(0).d1,(in.get(0).d1), 0.1);

        assertEquals(out.get(1).a, in.get(1).a);
        assertEquals(out.get(1).b, in.get(1).b);
        assertEquals(out.get(1).s, in.get(1).s);
        assertEquals(out.get(1).d1,(in.get(1).d1), 0.1);
    }

    @SneakyThrows
    @Test
    public void testCopyNonNullProperties() {
        source.a = null;
        source.s = null;
        TestPojo2 target = PojoUtils.copyNonNullProperties(source,TestPojo2.class);
        assertEquals(target.a, source.a);
        assertEquals(target.b, source.b);
        assertEquals(target.s, source.s);
        assertEquals(target.d1,(source.d1), 0.1);
    }

    @SneakyThrows
    @Test
    public void testCopyNonNullProperties1() {
        source.a = null;
        source.s = null;
        TestPojo2 target1 = new TestPojo2();
        TestPojo2 target = PojoUtils.copyNonNullProperties(source,target1);
        assertEquals(target.a, source.a);
        assertEquals(target.b, source.b);
        assertEquals(target.s, source.s);
        assertEquals(target.d1,(source.d1), 0.1);
    }
}