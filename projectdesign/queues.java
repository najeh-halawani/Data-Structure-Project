package projectdesign;
public class queues<T> {

    private Node front;
    private Node rear;

    queues() {
        this.front = null;
        this.rear = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public Node getFront() {
        return front;
    }

    public Node getRear() {
        return rear;
    }

    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            front = rear = newNode;
            return;
        }
        rear.setNext(newNode);
        rear = newNode;
    }

    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Empty queue");
            return null;
        }
        T temp = (T) front.getData();
        front = front.getNext();
        return temp;
    }

    public T peek() {
        if (isEmpty()) {
            System.out.println("Empty queue");
            return null;
        }
        T temp = (T) front.getData();
        return temp;
    }

    public void Display() {
        if (isEmpty()) {
            System.out.println("Queue is Empty");
            return;
        }
        queues<T> temp = new queues<T>();
        while (!isEmpty()) {
            T t = dequeue();
            System.out.println(t.toString());
            temp.enqueue(t);
        }
        this.front = temp.front;
        this.rear = temp.rear;

    }



}
