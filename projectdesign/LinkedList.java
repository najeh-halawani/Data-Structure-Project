package projectdesign;
public class LinkedList {
    NodeP first;
    String name;
    int currentNB;
    public LinkedList(String name) {
        this.name = name;
        currentNB=0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void insertAtBack(Car data) {
        NodeP newNode= new NodeP(data);
        if(isEmpty()) {
            first = newNode;
            return;
        }
        NodeP current = first;
        while(current.next != null) {

            current = current.next;
        }
        current.next = newNode;
        currentNB++;
    }

    public void insertAtBack(Car data, int p) {
        NodeP newNode= new NodeP(data, p);
        if(isEmpty()) {
            first = newNode;
        }
        NodeP current = first;
        while(current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        currentNB++;
    }


    public void insertAtFront(Car data, int p) {
        NodeP newNode = new NodeP(data, p);
        if (!isEmpty()) {
            newNode.next = first;
        }
        first = newNode;
        currentNB++;
    }


    public NodeP deleteFromFront() {
        if(isEmpty()) {
            System.out.println("Empty List");
            return null;
        }
        NodeP temp = first;
        first = first.next;
        currentNB--;
        return temp;
    }

    public Car deleteFromFront(String s) {
        if(isEmpty()) {
            System.out.println("Empty List");
            return null;
        }
        NodeP temp = first;
        first = first.next;
        currentNB--;
        return temp.car;
    }


    public int getSize() {
        return currentNB;
    }
    
    public void removeEl(Car el) {
        if(isEmpty()) {
            System.out.println("Empty List");
        } else {
            if(first.car == el) {
                first = first.next;
                return;
            }
            NodeP current = first;
            NodeP temp = first;
            while(current != null) {
                if(current.car == el) {
                    temp.next = current.next;
                }
                temp = current;
                current = current.next;
            }
            currentNB--;
        }
    }


    public void display() {
        NodeP current = first;
        while(current!= null) {
            System.out.print(current.car + "\n");
            current = current.next;
        }
    }

}
