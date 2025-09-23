package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;


public class ListStack implements Stack {

    private final List<Object> list = new LinkedList<>();
    private final int maxSize = 100;

    @Override
    public void push(Object item) {
        if(!isFull()) list.add(item);
    }

    @Override
    public Object pop() {
        if (isEmpty()) return null;

        Object stackTop = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return stackTop;
    }

    @Override
    public Object peek() {
        if (isEmpty()) return null;
        return list.get(list.size() - 1);
    }

    @Override
    public boolean isFull() {
        return list.size() >= maxSize;
    }

    @Override
    public void removeAll() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
