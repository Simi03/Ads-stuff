package ch.zhaw.ads;

/* interface of Traversal ADT */
public interface Traversal<T> {
    /* traverse elements of tree in preorder */
    public void preorder(Visitor<T> visitor);
    /* traverse elements of tree in inorder */
    public void inorder(Visitor<T> visitor);
    /* traverse elements of tree in postorder */
    public void postorder(Visitor<T> visitor);
    /* traverse elements of tree in levelorder */
    public void levelorder(Visitor<T> visitor);
    /* traverse elements of tree interval */
    public void interval(T min, T max, Visitor<T> visitor);
}