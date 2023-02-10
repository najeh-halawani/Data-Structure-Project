package projectdesign;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class Garage<T> {

    public static final String ACCOUNT_SID = "AC308103dd74eb2da59fc90a38eea94ebc";
    public static final String AUTH_TOKEN = "f9dd1d9756bf8751ebdb6b74d6e7479b";

    protected String Garage_Name;
    protected ArrayList<Person> person;
    protected Node<Car> temp;
    protected DoublyLinkedList cars_to_traverse;
    protected Stack<Activity> stack;
    protected PriorityQueue pq;
    protected double getLastCarSoldAmount;
    protected BinarySearchTree Person;
    protected int max_cs;
    protected int cnb;

    Garage(String Garage_Name){
        this.Garage_Name = Garage_Name;
        this.cars_to_traverse = new DoublyLinkedList(); // (catalog)
        this.person = new ArrayList<>(); // arrayList of all people
        this.pq = new PriorityQueue(); // Priority Queue for the rented car according -->  duration
        this.stack = new Stack<>(); // Stack of the action completed in the garage
        this.max_cs = 50; // garage => 50 cars =>  need to expand
        this.cnb =0; // useless
        this.Person = new BinarySearchTree(); // for the customers who buy a car
        boilerPlate(); // auto generate some virtual customer
        boilerplateCars(); // auto generate some people with their cars
        boilerRentedCar(); 
        this.temp = cars_to_traverse.head;
    }

    public boolean isFull(){
        return cars_to_traverse.getSize() == max_cs;
    }

    // generate people with at least 1 car to facilitate the listing of our customers
    public void boilerPlate() {
        for (int i = 0; i < 5; i++) {
            Person p = new Person("white" + i, "2004" + i, "tripoli" + i);
            Car car = new Car("c300" + i, "2004" + i, "mercedes", "blue", 10 + i, "uploads/carImages/car00" + i + ".png", 2000 + i, false, false);
            p.cars[0] = car;

            int choice = new Random().nextInt(4);
            Activity a;
            switch (choice) {
                case 0:
                    a = new Activity(Name.Buy, p);
                    stack.push(a);
                    break;
                case 1:
                    a = new Activity(Name.Wash, p);
                    stack.push(a);
                    break;
                case 2:
                    a = new Activity(Name.Rent, p, i + 5 + "");
                    stack.push(a);
                    break;
                case 3:
                    a = new Activity(Name.Repair, p);
                    stack.push(a);
                    break;
            }

            cnb++;
        }
    }

    public void boilerRentedCar() {
        for (int i = 0; i < 4; i++) {
            int choice = new Random().nextInt(4);
            Car c;
            switch (choice) {
                case 0:
                    c = new Car("bmw x5", "2004", "bmw", 30.0, "blue", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    c.carOwner = "najeh";
                    c.rentDuration = i+5;
                    pq.enqueue(c, i + 5);
                    break;
                case 1:
                    c = new Car("G-Class", "2004", "mercedes", 30.0, "grey", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    c.carOwner = "ralph";
                    c.rentDuration = i+20;
                    pq.enqueue(c, i + 20);
                    break;
                case 2:
                    c = new Car("Civic", "2004", "honda", 30.0, "red", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    c.carOwner = "mustafa";
                    c.rentDuration = i+4;
                    pq.enqueue(c, i + 4);
                    pq.display();
                    break;
                case 3:
                    c = new Car("Camaro", "2004", "chervolet", 30.0, "yellow", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    c.carOwner = "abdalla";
                    c.rentDuration = i+1;
                    pq.enqueue(c, i + 1);
                    break;
            }
        }
    }

    //  generate cars for the garage to perform the sell action
    private void boilerplateCars() {
        for (int i = 0; i < 5; i++) {
            int choice = new Random().nextInt(4);
            Car c;
            switch (choice) {
                case 0:
                    c = new Car("bmw x5", "2004", "bmw", 30.0, "blue", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    cars_to_traverse.InsertAtBack(c);
                    max_cs++;
                    break;
                case 1:
                    c = new Car("G-Class", "2004", "mercedes", 30.0, "grey", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    cars_to_traverse.InsertAtBack(c);
                    max_cs++;
                    break;
                case 2:
                    c = new Car("Civic", "2004", "honda", 30.0, "red", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    cars_to_traverse.InsertAtBack(c);
                    max_cs++;
                    break;
                case 3:
                    c = new Car("Camaro", "2004", "chervolet", 30.0, "yellow", "C:\\Users\\pc\\Documents\\BAU\\2nd Year\\1st Semester\\Data Structures\\DataProjectFinal\\ProjectDesign\\src\\uploads\\CarImages\\car00" + i + ".png", 2000);
                    cars_to_traverse.InsertAtBack(c);
                    max_cs++;
                    break;
            }

        }
        temp = cars_to_traverse.head;
    }

    // display the garage car (Catalog)  by using doubly LinkedList
    public void displayCars() {
        for (int i = 0; i < cnb; i++) {
            System.out.println(person.get(i));
        }
    }

    // when the number of cars is more than the maximum size  =>  expand() by 10 other places
    public void Expand(int number){
        DoublyLinkedList cars = new DoublyLinkedList();
        while(cars_to_traverse.head != null){
            Car tempC = cars_to_traverse.Remove((Car) cars_to_traverse.head.getData());
            cars.InsertAtBack(tempC);
            cars_to_traverse.head = cars_to_traverse.head.next;
        }
        cars_to_traverse.head = cars.head;
        temp = cars_to_traverse.head;
    }

    public void FetchCar(){
        System.out.println(temp.getData());
    }


    public void traverseBackward() {
        if(temp.getPrevious() == null) {
            return;
        }
        System.out.println(temp.getPrevious().getData());
        temp = temp.getPrevious();
    }

    public void traverseForward() {
        if(temp.getNext() == null) {
            return;
        }
        System.out.println(temp.getNext().getData());
        temp = temp.getNext();
    }


    public boolean buyCar(Person p, Car c){
        JFrame f = new JFrame();
        if(p.noCar()) {
            System.out.println("p");
            JOptionPane.showMessageDialog(f, p.name+" has no cars!", "Warning", JOptionPane.WARNING_MESSAGE);
        }else if(p.getNumberOfCars() == 1){
            
            if(p.cars[0].needMaintenance){
                System.out.println("p1");
                JOptionPane.showMessageDialog(f, p.name+" sorry, we can't accept a boken car", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            } else {
                cars_to_traverse.InsertAtBack(c);
                temp = cars_to_traverse.head;
                p.Deposit(c.price);
                p.removeCar(c);
                Activity a1 = new Activity(Name.Sell, p);
                stack.push(a1);
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(new PhoneNumber("+96176566465"),
                new PhoneNumber("+18635091758"),
                "You car " +c.name + c.yob +  "  has been sold successfully!").create();

        System.out.println(message.getSid());
                max_cs++;
            }
        }else if(p.getNumberOfCars()==2){
            if(p.cars[0] == c && p.cars[0].needMaintenance){
                System.out.println("p2");
                JOptionPane.showMessageDialog(f, p.name+" sorry, we can't accept a boken car", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }else if(p.cars[1] == c && p.cars[1].needMaintenance){
                JOptionPane.showMessageDialog(f, p.name+" sorry, we can't accept a boken car", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            } else if(p.cars[0] == c) {
                cars_to_traverse.InsertAtBack(c);
                temp = cars_to_traverse.head;
                p.Deposit(c.price);
                p.removeCar(c);
                Activity a1 = new Activity(Name.Sell, p);
                stack.push(a1);
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(new PhoneNumber("+96176566465"),
                new PhoneNumber("+18635091758"),
                "You Bought your car !").create();

        System.out.println(message.getSid());
                max_cs++;
            }
        } else if(isFull()){
            JOptionPane.showMessageDialog(f, "Please wait...");
            Expand(5);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Garage.class.getName()).log(Level.SEVERE, null, ex);
            }
                cars_to_traverse.InsertAtBack(c);
                temp = cars_to_traverse.head;
                p.Deposit(c.price);
                p.removeCar(c);
                Activity a1 = new Activity(Name.Sell, p);
                stack.push(a1);
                max_cs++;
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(new PhoneNumber("+96176566465"),
                new PhoneNumber("+18635091758"),
                "You Bought your car !").create();

                System.out.println(message.getSid());
            }
            return true;
        }
    


    public int[] computeProbability(){

        if(!cars_to_traverse.isEmpty()){
            String s[] = computeModels();
            int array[] = new int[s.length];
            int a=0;
            for(int i=0;i<s.length;i++){
                Node<Car> tempC = cars_to_traverse.head;
                String cr = s[i];
                a=0;
                while(tempC!=null){
                    if(tempC.data.brand.equalsIgnoreCase(cr)){
                        a++;
                    }
                    tempC = tempC.next;
                }
                array[i] = a;
            }
            return array;
        }else{
            return null;
        }
    }


    public String[] computeModels(){
        String s[] = new String[cars_to_traverse.getSize()];
        if(!cars_to_traverse.isEmpty()){
            Node<Car> tempC = cars_to_traverse.head;
            int i=0;
            while(tempC != null){
                s[i] = tempC.data.brand;
                i++;
                tempC = tempC.next;
            }
            //remove duplicates using HashSet
            s = new HashSet<>(Arrays.asList(s)).toArray(new String[0]);
            return s;
        }else{
            return null;
        }
    }

    public void sellCar(Node<Car> c, Person p) {
        if(p.buyCar(c)){
            getLastCarSoldAmount = c.data.price;
            p.setLastTransaction(c.data.price);
            cars_to_traverse.Remove(c.data);
            temp = cars_to_traverse.head;
            person.add(p);
            c.data.carOwner = p.name;
            Car car = new Car(c.data);
            Person.insert(car);
            Activity a1 = new Activity(Name.Buy, p);
            stack.push(a1);
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(new PhoneNumber("+96176566465"),
            new PhoneNumber("+18635091758"),
            "Congrats "+p.name+"!, You bought a new Car").create();

            System.out.println(message.getSid());
        }
    }


    public Car SearchCar(Car toSearch){
        JFrame f = new JFrame();
        try{
            return cars_to_traverse.GetCar(toSearch);
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    
    
    public void WashCar(Person p, Car toWash) {
        JFrame f = new JFrame();
        if (p.hasSpecificCar(toWash) == toWash) {
            if (p.hasSpecificCar(toWash).needCleaning) {
                if(p.budget >= toWash.price*0.15){
                    p.budget -= toWash.price*0.15;
                    p.hasSpecificCar(toWash).needCleaning = false;
                    JOptionPane.showMessageDialog(f, "-"+toWash.price*0.02+"$", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                    p.recentActivity.push(new Activity(Name.Wash, p));
                    stack.push(new Activity(Name.Wash, p));

                }else{
                    JOptionPane.showMessageDialog(f, "Insufficient budget", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(f, "Your Car doesn't need washing", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(f, "Car Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    

    
    public void RepairCar(Person p, Car toRepair) {
        JFrame f = new JFrame();
        if (p.hasSpecificCar(toRepair) == toRepair) {
            if (p.hasSpecificCar(toRepair).needMaintenance) {
                if(p.budget >= 50){
                    p.budget -= 50;
                    p.hasSpecificCar(toRepair).needMaintenance = false;
                    JOptionPane.showMessageDialog(f, "-"+50+"$" + " your car has been repaired successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    p.recentActivity.push(new Activity(Name.Repair, p));
                    stack.push(new Activity(Name.Repair, p));
                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                    Message message = Message.creator(new PhoneNumber("+96176566465"),
                    new PhoneNumber("+18635091758"),
                    p.name+", Your car has been repaired successfully! Your new balance is: "+p.budget+"$").create();

                    System.out.println(message.getSid());
                }else{
                    JOptionPane.showMessageDialog(f, "Insufficient budget", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(f, "Your Car doesn't need maintenance", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(f, "Car Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public boolean rentCar(Person p, Node<Car> c, int day){
        JFrame f = new JFrame();
        if(p.budget < c.data.rentPrice * day) {
            JOptionPane.showMessageDialog(f, p.name+"Insufficient Amount", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            System.out.println(p.rentedCar);
            if(p.rentedCar == null) {
                p.budget -= c.data.rentPrice*day;
                p.rentedcarDuration = day;
                c.data.rentDuration = day;
                c.data.carOwner = p.name;
                p.rentedCar = c;

                pq.enqueue(cars_to_traverse.Remove(c.data), day);
                Activity a = new Activity(Name.Rent, p);
                stack.push(a);

                p.recentActivity.push(new Activity(Name.Rent, p,day+""));
                temp = cars_to_traverse.head;
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                Message message = Message.creator(new PhoneNumber("+96176566465"),
                new PhoneNumber("+18635091758"),
                p.name+" "+day+" left").create();

                System.out.println(message.getSid());  
                return true;
            } 
        }
        return false;
    }

    public void WashCar(){
        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Node <Car> temp = cars_to_traverse.head;
                        while(temp != null){
                            if(temp.data.needCleaning){
                                temp.data.needCleaning = false;
                            }
                            temp = temp.next;
                        }
                    }
                },
                10
        );
    }

    public void MakeDirty(){
        // update method
        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Node<Car> temp = cars_to_traverse.head;
                        while(temp != null){
                            if(new Random().nextInt(2) == 0){
                                temp.data.needCleaning = true;
                            }
                            temp = temp.next;
                        }
                    }
                },
                10
        );
    }


    public  void retrieveCar(int day) {
        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        cars_to_traverse.InsertAtBack(pq.dequeue());
                        t.cancel();
                    }
                },
                day
        );
    }

}