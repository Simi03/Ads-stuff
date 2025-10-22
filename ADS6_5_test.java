/**
 * @(#)TreeTest.java
 *
 *
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */

package ch.zhaw.ads;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

public class ADS6_5_test {
    Tree<String> tree;

    String fileToTest = "AVLSearchTree.java";

    @Test
    public void testMixed() {
        tree = new AVLSearchTree<String>();
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            Character c = (char) ('A' + (Math.random() * 26));
            int op = (int) (Math.random() * 2);
            switch (op) {
                case 0:
                    list.add(c.toString());
                    tree.add(c.toString());
                    break;
                case 1:
                    list.remove(c.toString());
                    tree.remove(c.toString());
                    break;
            }
        }
        assertEquals(list.size(),tree.size());
        Collections.sort(list);
        StringBuilder b = new StringBuilder();
        for (String s : list) {b.append(s);};
        Visitor<String> v = new MyVisitor<String>();
        tree.traversal().inorder(v);
        assertEquals("mixed",b.toString(), v.toString());

        assertTrue("balanced",tree.balanced());
    }


}

class MyVisitor<T> implements Visitor<T> {
    StringBuilder output;

    MyVisitor() {
        output = new StringBuilder();
    }

    public void visit(T s) {
        output.append(s);
    }

    public String toString() {
        return output.toString();
    }
}