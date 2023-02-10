package projectdesign;
public class NodeP {
    protected Car car;

    protected NodeP next;
    protected int priority;

    public NodeP() {}


    public NodeP(Car data) {
        this.car = data;
        this.next = null;
        this.priority = 0;
    }

    public NodeP(Car person, int priority) {
        this.car = person;
        this.priority = priority;
    }

    public Car getData() {
        return car;
    }

    public NodeP getNext() {
        return next;
    }

    public void setNext(NodeP next) {
        this.next = next;
    }

}
