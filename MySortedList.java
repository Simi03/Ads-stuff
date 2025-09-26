package ch.zhaw.ads;



public class MySortedList extends MyList {

	private int compare(Object a, Object b) {
		if (a == null && b == null) return 0;
		if (a == null) return -1;
		if (b == null) return 1;
		return ((Comparable)a).compareTo(b);
	}

	@Override
	public boolean add(Object o) {
		ListNode newNode = new ListNode(o);

		// empty list
		if (head == null) {
			head = newNode;
			size++;
			return true;
		}

		// insert before head
		if (compare(o, head.value) <= 0) {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
			size++;
			return true;
		}

		// walk forward until insertion point
		ListNode current = head;
		while (current.next != null && compare(o, current.next.value) > 0) {
			current = current.next;
		}

		// insert after current
		newNode.next = current.next;
		newNode.prev = current;
		current.next = newNode;
		if (newNode.next != null) {
			newNode.next.prev = newNode;
		}

		size++;
		return true;
	}


}
