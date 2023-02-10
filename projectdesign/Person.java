package projectdesign;
import javax.swing.*;
import java.util.Arrays;

public class Person {
    protected String name;
    protected Node<Car> rentedCar;
    protected int rentedcarDuration;
    protected String yob;
    protected String location;
    protected double budget;
    protected Car[] cars;
    protected int numberOfCars;
    protected Stack<Activity> recentActivity;
    protected double lastTransaction;

    Person(String name,String yob,String location){
        this.cars = new Car[2];
        numberOfCars =0;
        this.name = name;
        this.yob = yob;
        this.location = location;
        this.budget=0.0;
        recentActivity = new Stack();
    }

    public Person() {
           numberOfCars=0;
           recentActivity = new Stack();
    }

    public void Deposit(double a){
        JFrame f = new JFrame();
        try{
            if(a < 0 ){
                JOptionPane.showMessageDialog(f,"You Must enter a positive amount", "Warning", JOptionPane.WARNING_MESSAGE);
            }else{
                this.budget+=a;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    public double getBudget(){
        return budget;
    }

    public void setLastTransaction(double lastTransaction){
        this.lastTransaction = lastTransaction;
    }
    
    


    public boolean retrieveAmount(double a){
        JFrame f = new JFrame();
        try{
            if(a > budget ){
                JOptionPane.showMessageDialog(f,"Balance not sufficient", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }else{
                this.budget -=a ;
                return true;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(f, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public boolean noCar() {
        return numberOfCars == 0;
    }

    public boolean isFull() {
        return numberOfCars == cars.length;
    }

    public int getNumberOfCars() {
        if(!noCar()) {
            return numberOfCars;
        }
        return 0;
    }

    public void removeCar(Car c) {
        for(int i =0; i < cars.length; i++) {
            System.out.println("p: "+i);
            if(cars[i] == c) {
                cars[i] = null;
                System.out.println("sold car: " + i);
                recentActivity.push(new Activity(Name.Sell, this));
            }
        }
        numberOfCars--;
    }

    public boolean buyCar(Node<Car> c) {
        JFrame f = new JFrame();
        if(isFull()) {
            JOptionPane.showMessageDialog(f, "Can't purchase more cars!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            if(retrieveAmount(c.data.price)) {
                cars[numberOfCars] = c.data;
                numberOfCars++;
                recentActivity.push(new Activity(Name.Buy, this));
                return true;
            }
        }
        return false;
    }

    public Car hasSpecificCar(Car car){
        JFrame f = new JFrame();
        try{
            for(int i=0;i<2;i++){
                if(car == cars[i]){
                    return cars[i];
                }
            }
            return null;
        }catch(Exception e){
            JOptionPane.showMessageDialog(f,e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", yob='" + yob + '\'' +
                ", location='" + location + '\'' +
                ", budget=" + budget +
                ", Cars=" + Arrays.toString(cars) +
                '}';
    }
}
