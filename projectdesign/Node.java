package projectdesign;
public class Node<T>{

    protected T data;

    protected Node<T> next;
    protected Node<T>  previous;

    Node(){

    }

    public T getData() {
        return data;
    }

    Node(T data){
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data.toString() +
                '}';
    }
}
