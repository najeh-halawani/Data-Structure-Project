package projectdesign;

import javax.swing.*;
public class DoublyLinkedList{
    Node <Car> head;

    DoublyLinkedList(){
        head = null;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void InsertAtFront(Car data){
        Node node = new Node(data);
        if(!isEmpty()) {
            node.next = head;
            head.previous = node;
        }
        head = node;
    }

    public void InsertAtBack(Car data){
        Node node = new Node(data);
        if(isEmpty()){
            head = node;
        }else{
            Node temp = head;
            while(temp.next != null){
                temp = temp.next;
            }
            node.previous = temp;
            temp.next = node;
        }
    }


    public int getSize(){
        if(isEmpty())
            return 0;
        else{
            Node temp = head;
            int count=0;
            while(temp != null){
                count++;
                temp = temp.next;
            }
            return count;
        }
    }


    public void InsertAt(int pos,Car data) {
        JFrame f = new JFrame();
        try {
            Node node = new Node(data);
            if (isEmpty()) {
                head = node;
            } else if (pos < 0) {
                JOptionPane.showMessageDialog(f, "Invalid Position", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            } else if (pos == 0) {
                InsertAtFront(data);
            } else if (pos > getSize()) {
                JOptionPane.showMessageDialog(f, "Invalid Position", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                int i = 0;
                Node temp = head;
                Node prev = null;
                while (i < pos) {
                    prev = temp;
                    if (temp.next == null) {
                        JOptionPane.showMessageDialog(f, "Invalid Position", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    i++;
                    temp = temp.next;
                }
                prev.next = node;
                node.previous = prev;
                node.next = temp;
                temp.previous = node;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    public Car RemoveAtFront(){
        JFrame f = new JFrame();
        try{
            if(isEmpty()){
                return null;
            }else{
                Car temp = (Car) head.data;
                head.next.previous = null;
                head = head.next;
                return temp;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public Car RemoveAtBack(){
        JFrame f = new JFrame();
        try{
            if(isEmpty()){
                return null;
            }else{
                Node temp = head;
                while(temp.next.next != null){
                    temp = temp.next;
                }
                Car car = (Car) temp.next.data;
                temp.next.previous = null;
                temp.next = null;
                return car;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public Car Remove(Car data){
        JFrame f = new JFrame();

        Car car = null;
        try {
            if (isEmpty()) {
                return null;
            } else {
                if (isFound(data)) {
                    if (head.data == data) {
                        car = RemoveAtFront();
                    } else {
                        Node temp = head;
                        Node before = head;
                        while (temp.next != null) {
                            before = temp;
                            if (temp.next.data == data) {
                                temp = temp.next;
                                break;
                            }
                            temp = temp.next;
                        }
                        car = (Car) temp.data;
                        if (temp.next == null) {
                            before.next = null;
                            temp.previous = null;
                        } else{
                            before.next = temp.next;
                            temp.next.previous = before;
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(f, "Element Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
                    return null;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return (Car) car;
    }



    public boolean isFound(Car data){
        JFrame f = new JFrame();
        try{
            Node temp = head;
            while(temp != null){
                if(temp.data == data){
                    return true;
                }
                temp = temp.next;
            }
            return false;
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public Car GetCar(Car car){
        JFrame f = new JFrame();
        try{
            if(isFound(car)){
                Node temp = head;
                while(temp!=null){
                    if(temp.data == car)
                        break;
                    temp = temp.next;
                }
                return (Car) temp.data;
            }else{
                JOptionPane.showMessageDialog(f, "Car Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public void display(){
        if(isEmpty()){
            return;
        }else{
            Node temp = head;
            while(temp != null){
                //Printing
                System.out.print(temp.data+" ");
                temp = temp.next;
            }
        }
    }
}
