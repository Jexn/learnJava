public class TestBST {
    public static void main(String[] args) {
        BST<String> trees = new BST<>();
        trees.insert("George");
        trees.insert("Michael");
        trees.insert("Tom");
        trees.insert("Adam");
        trees.insert("Jones");
        trees.insert("Peter");
        trees.insert("Daniel");

        System.out.println("Inorder sort:");
        trees.inorder();
        System.out.println("\nPostorder sort:");
        trees.postorder();
        System.out.println("\nPreorder sort:");
        trees.preorder();

    }
}
