package ch.zhaw.ads;

import java.util.*;

class TreeNode<T extends Comparable<T>> {
    List<T> values;  // implemented as list because of doublette handling in AVL
    TreeNode left, right;
    int height; // for AVL

    TreeNode(T value){
        this.values = new LinkedList<>();
        this.values.add(value);
        this.height = 1;
    }

    TreeNode(T value, TreeNode left, TreeNode right){
        this(value); this.left = left; this.right = right;
    }

    T getValue(){return values.get(0);}
}