package projectdesign;

public class BinarySearchTree {

    protected NodeT root;
    protected Car[] p;
    protected int currentNb;

    BinarySearchTree() {
        root = null;
        currentNb = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getFullCount() {
        return getFullCount(root);
    }

    public int getFullCount(NodeT root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + getFullCount(root.left) + getFullCount(root.right);
        }
    }

    public void createArray() {
        p = new Car[getFullCount()];
    }

    public void clearArray() {
        Car t[] = new Car[getFullCount()];
        p = t;
        currentNb = 0;
    }

    public void insert(Car person) {
        root = insert(root, person);
    }

    private NodeT insert(NodeT root, Car car) {
        NodeT node = new NodeT();
        node.car = car;
        if (root == null) {
            root = node;
            return root;
        } else if (car.price < root.car.price) {
            root.left = insert(root.left, car);
        } else {
            root.right = insert(root.right, car);
        }
        return root;
    }

    public void deleteKey(int key) {
        root = deleteRec(root, key);
    }

    private NodeT deleteRec(NodeT root, int key) {
        /* Base Case: If the tree is empty */
        if (root == null) {
            return null;
        }

        /* Otherwise, recur down the tree */
        if (key < root.car.price) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.car.price) {
            root.right = deleteRec(root.right, key);
        } // if key is same as root's
        // key, then This is the
        // node to be deleted
        else {
            // node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // node with two children: Get the inorder
            // successor (smallest in the right subtree)
            root.car.price = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, (int) root.car.price);
        }

        return root;
    }

    public int minValue(NodeT node) {

        NodeT current = node;
        while (current.left != null) {
            current = current.left;
        }

        return (int) (current.car.price);
    }

    public NodeT SearchNode(double value, NodeT root) {
        if (root == null) {
            System.out.println("Not Found");
            return null;
        }
        if (root.car.price == value) {
            System.out.println("Value is found");
            return root;
        }
        if (value > root.car.price) {
            SearchNode(value, root.right);
        }
        if (value < root.car.price) {
            SearchNode(value, root.left);
        }
        return root;
    }

    public void postorder() {
        root = postorder(root);
    }

    public NodeT postorder(NodeT r) {
        if (r != null) {
            postorder(r.right);
            if(r != null){
             p[currentNb++] = r.car;
            }
            postorder(r.left);
        }
        return r;
    }
}
