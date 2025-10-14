package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.Queue;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {

    private TreeNode<T> root;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    public void inorder(Visitor<T> vis) {
        inorder(root, vis);
    }

    public void preorder(Visitor<T> vis) {
        preorder(root, vis);
    }

    public void postorder(Visitor<T> vis) {
        postorder(root, vis);
    }

    public void levelorder(Visitor<T> visitor) {
        levelorder(root, visitor);
    }

    public void interval(T min, T max, Visitor<T> visitor) {
        interval(root, min, max, visitor);
    }

    public void interval(TreeNode<T> node, T min, T max, Visitor<T> visitor) {
        if (node == null) return;
        if (node.getValue().compareTo(min) >= 0) {
            interval(node.left, min, max, visitor);
        }
        if (node.getValue().compareTo(min) >= 0 && node.getValue().compareTo(max) <= 0) {
            visitor.visit(node.getValue());
        }
        if (node.getValue().compareTo(max) <= 0) {
            interval(node.right, min, max, visitor);
        }
    }


    public void preorder(TreeNode<T> node, Visitor<T> visitor) {
        if (node != null) {
            visitor.visit(node.getValue());
            preorder(node.left, visitor);
            preorder(node.right, visitor);
        }
    }

    public void postorder(TreeNode<T> node, Visitor<T> visitor) {
        if (node != null) {
            postorder(node.left, visitor);
            postorder(node.right, visitor);
            visitor.visit(node.getValue());
        }
    }

    public void inorder(TreeNode<T> node, Visitor<T> visitor) {
        if (node != null) {
            inorder(node.left, visitor);
            visitor.visit(node.getValue());
            inorder(node.right, visitor);
        }
    }


    public void levelorder(TreeNode<T> node, Visitor<T> visitor) {
        Queue<TreeNode<T>> q = new LinkedList<>();

        if (node != null) q.add(node);
        while (!q.isEmpty()) {
            node = q.remove();
            visitor.visit(node.getValue());
            if (node.left != null) q.add(node.left);
            if (node.right != null) q.add(node.right);
        }
    }
}