package projectdesign;
public class Car {
    String name, yob, brand, color, image; 
    double price;
    double rentPrice;
    boolean needCleaning;
    String carOwner;
    int rentDuration;
    boolean needMaintenance;

    public Car() {}
    
    public Car(Car c) {
        this.name = c.name ;
        this.carOwner = c.carOwner;
        this.yob = c.yob ;
        this.brand = c.brand ;
        this.image = c.image ;
        this.color = c.color ;
        this.price = c.price;
        this.rentPrice = c.rentPrice;
        this.needCleaning = c.needCleaning;
        this.needMaintenance = c.needMaintenance;
    }
          
    public Car(String name, String yob, String brand, String color, double rentPrice, String image, double price, boolean needCleaning,boolean needMaintenance) {
        this.name = name;
        this.yob = yob;
        this.brand = brand;
        this.color = color;
        this.image = image;
        this.rentPrice = rentPrice;
        this.price = price;
        this.needCleaning = needCleaning;
        this.needMaintenance = needMaintenance;
    }

    public Car(String name, String yob, String brand, double rentPrice, String color, String image, double price) {
        this.name = name;
        this.yob = yob;
        this.brand = brand;
        this.image = image;
        this.color = color;
        this.rentPrice = rentPrice;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", yob='" + yob + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", rentPrice=" + rentPrice +
                ", needCleaning=" + needCleaning +
                ", carOwner=" + carOwner +
                ", rentDuration=" + rentDuration +
                '}';
    }
}
