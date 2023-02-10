package projectdesign;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

enum Name{Buy,Sell, Wash,Rent,Repair}
public class Activity {
    Name name;
    String date;
    String duration;
    String personName;

    Activity(Name name, Person p,String duration){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now) ;
        this.name = name;
        this.duration = duration;
        this.personName = p.name;
    }

    Activity(Name name, Person p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now) ;
        this.name = name;
        this.duration = "∞";
        this.personName = p.name;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "name=" + name +
                ", date='" + date + '\'' +
                ", duration='" + duration + '\'' +
                ", personName='" + personName + '\'' +
                '}';
    }
}
