import java.util.ArrayList;
import java.util.List;
public class Node {
    public Node leftNode;
    public Node rightNode;
    public Object value;

    public void add(Object v) {
        // 如果当前节点没有值，那么就把值放入到当前节点
        if (value == null) {
            value = v;
        } else {
            // 如果当前节点的值比父节点的值小，那么将当前节点放入左边
            if (((Integer) v) - ((Integer) value) <= 0) {
                // 如果子节点还没有赋值，那就创建新的节点。
                if (leftNode == null) {
                    leftNode = new Node();
                }
                // 如果子节点还存在，将进入下一层节点遍历
                leftNode.add(v);
            } else {
                // 和左节点原理一样
                if (rightNode == null) {
                    rightNode = new Node();
                }
                rightNode.add(v);
            }
        }
    }
    // 中序遍历二叉树
    public List<Object> values(){
        List<Object> values = new ArrayList<>();
        if (leftNode != null){
            values.addAll(leftNode.values());
        }

        values.add(value);

        if (rightNode != null){
            values.addAll(rightNode.values());
        }

        return values;
    }

    public static void main(String[] args) {
        int[] randoms = new int[] { 67, 7, 30, 73, 10, 0, 78, 81, 10, 74 };
        Node roots = new Node();
        for (int num : randoms) {
            roots.add(num);
        }
    }
}