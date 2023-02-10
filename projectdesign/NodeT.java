package projectdesign;
public class NodeT {
    protected NodeT left;
    protected NodeT right;

    protected Car car;

    NodeT(){
        this.left = null;
        this.right = null;
        car = new Car();

    }

    public void setPerson(Car car) {
        this.car = car;
    }

    public Car getPerson() {
        return car;
    }

    public NodeT getLeft() {
        return left;
    }

    public void setLeft(NodeT left) {
        this.left = left;
    }

    public NodeT getRight() {
        return right;
    }

    public void setRight(NodeT right) {
        this.right = right;
    }
}
