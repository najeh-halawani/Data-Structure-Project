package projectdesign;
public class Stack<T> {
    Node<T> top;

    Stack() {
        this.top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public Node getTop() {
        return top;
    }

    public void push(T data) {
        Node<Activity> newNode = new Node(data);
        if (isEmpty()) {
            top = (Node<T>) newNode;
            return;
        }
        newNode.setNext(top);
        top = (Node<T>) newNode;
    }

    public int getSize(){
        int count;
        if(isEmpty()){
            return -1;
        }else{
            Node temp = top;
            count=0;
            while(temp != null){
                count++;
                temp = temp.next;
            }
        }
        return count;
    }

    
    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack empty");
            return null;
        }
        T temp = (T) top;
        top = top.getNext();
        return temp;
    }

    public T peek() {
        if (isEmpty()) {
            System.out.println("Stack empty");
            return null;
        }
        T temp = (T) top.getData();
        return temp;
    }

    public void Display() {
        if (isEmpty()) {
            System.out.println("Stack is Empty");
            return;
        }
        Stack<T> temp = new Stack<T>();
        while (!isEmpty()) {
            T t = pop();
            System.out.println(t);
            temp.push(t);
        }
        this.top = temp.top;
    }

}
