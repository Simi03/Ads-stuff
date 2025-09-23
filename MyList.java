package ch.zhaw.ads;

import java.util.AbstractList;

public class MyList extends AbstractList {
    protected ListNode head;
    protected int size;

    @Override
    public Object get(int index) {
        ListNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(Object o) {
        if (head == null) {
            head = new ListNode(o);
            size++;
            return true;
        } else {
            ListNode current = head;
            while (current.next != null) {
                current = current.next;
            }

            ListNode newNode = new ListNode(o);
            newNode.prev = current;
            current.next = newNode;
            size++;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (head == null) return false;

        ListNode current = head;
        do {
            if (current.value.equals(o)) {
                if (current.prev != null) current.prev.next = current.next;
                if (current.next != null) current.next.prev = current.prev;
                size--;
                return true;

            } else {
                if (current.next == null) return false;
                current = current.next;
            }
        } while (current.next != null);

        return false;
    }



    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }



    protected class ListNode {
        Object value;
        ListNode next, prev;

        ListNode(Object value) {
            this.value = value;
        }
    }
}