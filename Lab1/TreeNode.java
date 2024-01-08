

import java.util.ArrayList;
import java.util.List;

class TreeNode<T> {
    private T data;
    private ArrayList<TreeNode<T>> children;
    public int depth;

    public TreeNode(T data,int depth) {
        this.data = data;
        this.depth=depth;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode<T> child) {
        this.children.add(child);
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }
}
