package ch.zhaw.ads;
/**
 * @(#)StackTest.java
 *
 *
 * @author
 * @version 1.00 2017/8/30
 */

import org.junit.Before;
import static org.junit.Assert.*;

public class Test {
    ListStack stack;

    @Before
    public void setUp() throws Exception {
        stack = new ListStack();
    }

    @org.junit.Test
    public void testPush1() {
        stack.push("A");
        Object o = stack.pop();
        assertEquals("A",o);
    }

    @org.junit.Test
    public void testPush2() {
        stack.push("A");
        stack.push("B");
        assertEquals("B",stack.pop());
        assertEquals("A",stack.pop());
    }

    @org.junit.Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push("A");
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @org.junit.Test
    public void testIsFull() {
        assertFalse(stack.isFull());
    }

    @org.junit.Test
    public void testEmptyPop() {
        assertEquals(stack.pop(), null);
    }

    @org.junit.Test
    public void testBracket() {
        BracketServer bs = new BracketServer();
        assertTrue(bs.checkBrackets("()"));
        assertTrue(bs.checkBrackets("([])"));
        assertTrue(bs.checkBrackets("([{}])"));
        assertFalse(bs.checkBrackets("([)]"));
        assertFalse(bs.checkBrackets("((())"));
        assertFalse(bs.checkBrackets("())"));
        assertEquals(bs.checkBrackets("("), false);
        assertEquals(bs.checkBrackets(")"), false);

        assertEquals(bs.checkBrackets("<(<>)>"), true);
        assertEquals(bs.checkBrackets("<(<)>>"), false);

        assertEquals(bs.checkBrackets("/* hallo */"), true);
        assertEquals(bs.checkBrackets("/*/* */"), false);
        assertEquals(bs.checkBrackets("/*"), false);
    }



    @org.junit.Test
    public void testAdd() {
        MyList list = new MyList();
        list.clear();
        list.add("A");
        Object o = list.get(0);
        assertEquals("A", o);
    }

    @org.junit.Test
    public void testAdd2() {
        MyList list = new MyList();
        list.clear();
        list.add("A");
        list.add("B");
        Object o = list.get(0);
        assertEquals("A",o);
        o = list.get(1);
        assertEquals("B",o);
    }

    @org.junit.Test
    public void testAdd3() {
        MyList list = new MyList();
        list.clear();
        list.add("A");
        list.add("B");
        list.add("C");
        Object o = list.get(0);
        assertEquals("A",o);
        o = list.get(1);
        assertEquals("B",o);
        o = list.get(2);
        assertEquals("C",o);
    }

    @org.junit.Test
    public void testRemove() {
        MyList list = new MyList();
        list.clear();
        list.add("A");
        list.remove("A");
        assertEquals(0, list.size());
        list.add("A");
        list.remove("B");
        assertEquals(1, list.size());
        list.remove("A");
        assertEquals(0, list.size());
    }
}