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
		ListNode current = head;
		while (current != null) {
			if (current.value.equals(o)) {
				if (current.prev != null) {
					current.prev.next = current.next;
				} else {
					head = current.next; // removing head
				}
				if (current.next != null) {
					current.next.prev = current.prev;
				}
				size--;
				return true;
			}
			current = current.next;
		}
		return false;
	}



    @Override
    public void clear() {
		head = null;
		size = 0;
    }

    @Override
    public boolean isEmpty() {
        return head == null && size == 0;
    }



    protected class ListNode {
        Object value;
        ListNode next, prev;

        ListNode(Object value) {
            this.value = value;
        }
    }
}