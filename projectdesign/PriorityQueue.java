package projectdesign;
public class PriorityQueue {
    LinkedList queue;
    int currentNb;



    public PriorityQueue() {
        queue = new LinkedList("queue");
        this.currentNb = 0;
    }
    
     public void priorityEnqueue(Car data, int priority) {
        LinkedList tempQueue = new LinkedList("temp");
        while(!queue.isEmpty()) {
            NodeP temp = queue.deleteFromFront();
            if(priority >= temp.priority) {
                queue.insertAtFront(temp.car, temp.priority);
                queue.insertAtFront(data, priority);
                return;
            } else if(queue.getSize() == 0) {
                queue.insertAtFront(temp.car, temp.priority);
                queue.insertAtBack(data, priority);
                return;
            }else {
                tempQueue.insertAtFront(temp.car, temp.priority);
                while(!queue.isEmpty()) {
                    NodeP tt = queue.deleteFromFront();
                    if(priority >= tt.priority) {
                        queue.insertAtFront(tt.car,tt.priority);
                        queue.insertAtFront(data, priority);
                        while(!tempQueue.isEmpty()) {
                            NodeP ss = tempQueue.deleteFromFront();
                            queue.insertAtFront(ss.car, ss.priority);
                        }
                        return;
                    } else if(tt.next == null) {
                        queue.insertAtFront(data, priority);
                        queue.insertAtFront(tt.car, tt.priority);
                        while(!tempQueue.isEmpty()) {
                            NodeP ss = tempQueue.deleteFromFront();
                            queue.insertAtFront(ss.car, ss.priority);
                        }
                        return;
                    } else {
                        tempQueue.insertAtFront(tt.car, tt.priority);
                    }
                }
            }
        }
    }


     
     public Stack Reverse() {
        LinkedList list = new LinkedList("temp");
        list.currentNB = queue.currentNB;
        list.first = queue.first; // to make a copy
        NodeP temp = list.first;
        Stack stack = new Stack();
        while(temp != null){
            stack.push(temp.car);
            temp = temp.next;
        }
        return stack;
    }



    public int getSize() {
        return currentNb;
    }

    public void enqueue(Car data, int priority) {
          if(queue.isEmpty()) {
            queue.insertAtFront(data, priority);
            currentNb++;
            return;
        }
        priorityEnqueue(data, priority);
        currentNb++;
    }


    public Car dequeue(){
        currentNb--;
        return queue.deleteFromFront().car;
    }
    
    
    public void display() {
        queue.display();
    }

}
