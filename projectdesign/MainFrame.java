/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdesign;

import chart.ModelPieChart;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public final class MainFrame extends javax.swing.JFrame {

    Garage garage;
    Person p;
    Car sellCar;
    Car washCar;
    Car repairCar;
    Node<Car> temp;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        garage = new Garage("Bau Garage");
        showDashboard();
        updateTable();
        showChart();
        alignTable();
        showCar();
    }

    public MainFrame(Person p) {
        initComponents();
        garage = new Garage("Bau Garage");
        showDashboard();
        updateTable();
        showChart();
        alignTable();
        username.setText(p.name);
        amountlabel.setText("Balance: " + p.budget + "$");
        Node<Car> temp = garage.temp;
        showCar(temp);
        brandName.setText(temp.data.brand);
        Year.setText(temp.data.yob);
        Color.setText(temp.data.color);
        Price.setText(String.valueOf(temp.data.price));
        this.p = p;
        if (p.cars[0] != null) {
            sellCar = p.cars[0];
        }
    }

    /* Tables */
    public final void updateTable() {

        Node<Activity> temp = garage.stack.getTop();
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0);
        for (int i = 0; i < garage.stack.getSize(); i++) {
            Vector v = new Vector();
            for (int j = 0; j < 4; j++) {
                v.add(temp.data.personName);
                v.add(temp.data.name);
                v.add(temp.data.date);
            }
            model.addRow(v);
            temp = temp.getNext();
        }
    }

    public final void updateTableOfHistory() {
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        model.setRowCount(0);
        Node<Activity> temp = p.recentActivity.top;
        for (int i = 0; i < p.recentActivity.getSize(); i++) {
            Vector v = new Vector();
            for (int j = 0; j < 4; j++) {
                v.add(temp.data.name);
                v.add(temp.data.date);
                v.add(temp.data.duration);
            }
            model.addRow(v);
            temp = temp.next;
        }
    }

    public void updateBuyTable() {
        garage.Person.clearArray();
        garage.Person.postorder();
        Car[] tempP = garage.Person.p;
        DefaultTableModel Buymodel = (DefaultTableModel) garageBuytable.getModel();
        Buymodel.setRowCount(0);

        if (tempP.length == 0) {
            return;
        }
        for (Car tempP1 : tempP) {
            Vector v = new Vector();
            v.add(tempP1.carOwner);
            v.add("Buy");
            v.add(tempP1.price);
            v.add(tempP1.brand);
            Buymodel.addRow(v);
        }

    }

    public void updateRentTable() {
        DefaultTableModel Rentmodel = (DefaultTableModel) garageRentTable.getModel();
        Rentmodel.setRowCount(0);
         
        Stack l = garage.pq.Reverse();
        System.out.println("queue size: " + garage.pq.queue.getSize());
        System.out.println("stack size: " + l.getSize());
        int size = l.getSize();
        for (int i = 0; i < size; i++) {
            System.out.println(i);
            Node<Car> temp = (Node<Car>) l.pop();
            Vector v = new Vector();
            v.add(temp.data.carOwner);
            v.add("Rent");
            v.add(temp.data.rentDuration);
            Rentmodel.addRow(v);
        }
    }

    /* Navigation */
    
    public void washCarNext() {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.cars[1] != null) {
                washCar = p.cars[1];
                img = ImageIO.read(new File(p.cars[1].image));
                OwnedCarsDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }
    }

    public void washCarPrev() {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.cars[0] != null) {
                washCar = p.cars[0];
                img = ImageIO.read(new File(p.cars[0].image));
                OwnedCarsDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }
    }
    
    public void repairCarPrev() {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.cars[0] != null) {
                repairCar = p.cars[0];
                img = ImageIO.read(new File(p.cars[0].image));
                repairCarDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }
    }
    
    public void repairCarNext() {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.cars[1] != null) {
                repairCar = p.cars[1];
                img = ImageIO.read(new File(p.cars[1].image));
                repairCarDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }
    }

    public void prevCar() {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.cars[0] != null) {
                sellCar = p.cars[0];
                img = ImageIO.read(new File(p.cars[0].image));
                sellCarDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }
    }

    public void forward() {
        garage.traverseForward();
        Node<Car> temp = garage.temp;
        showCar(temp);
        showCarDetails(temp);
    }

    public void backward() {
        garage.traverseBackward();
        Node<Car> temp = garage.temp;
        showCar(temp);
        showCarDetails(temp);
    }

    /* Display Cars */
    public void showCar() {
        Node<Car> temp = garage.temp;

        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            img = ImageIO.read(new File(temp.data.image));
            CarPhoto.setIcon(new ImageIcon(img));
        } catch (IOException e) {
        }
    }

    public void showCar(Node<Car> temp) {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            img = ImageIO.read(new File(temp.data.image));
            CarPhoto.setText("");
            CarPhoto.setIcon(new ImageIcon(img));
        } catch (IOException e) {
        }
    }

    public void showWashCar(Node<Car> temp) {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.cars[0] != null) {
                washCar = p.cars[0];
                img = ImageIO.read(new File(p.cars[0].image));
                OwnedCarsDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel20 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        bg = new javax.swing.JPanel();
        sidepanel = new javax.swing.JPanel();
        settings = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        history = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        RentedCar = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        wash = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        sell = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        buy = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        repair = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        amountlabel = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        DashboardPanel = new javax.swing.JPanel();
        pieChart = new chart.PieChart();
        dashboardTable2 = new javax.swing.JScrollPane();
        activityTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        BuyCarPanel = new javax.swing.JPanel();
        traversePrev = new javax.swing.JLabel();
        CarPhoto = new javax.swing.JLabel();
        BuyCarButton = new javax.swing.JButton();
        traverseNext = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        Year = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        brandName = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        Color = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        Price = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        rentPrice = new javax.swing.JTextField();
        day = new javax.swing.JTextField();
        rentCarButton = new javax.swing.JButton();
        SellCarPanel = new javax.swing.JPanel();
        sellCarPrev = new javax.swing.JLabel();
        sellCarDisplay = new javax.swing.JLabel();
        requestSellButton = new javax.swing.JButton();
        sellCarNext = new javax.swing.JLabel();
        WashCarPanel = new javax.swing.JPanel();
        OwnedCarsDisplay = new javax.swing.JLabel();
        washCarPrev = new javax.swing.JLabel();
        washcarButtin = new javax.swing.JButton();
        washCarNext = new javax.swing.JLabel();
        repairCarPanel = new javax.swing.JPanel();
        repairCarDisplay = new javax.swing.JLabel();
        repairCarPrev = new javax.swing.JLabel();
        RepairCarButton = new javax.swing.JButton();
        repairCarNext = new javax.swing.JLabel();
        RentCarPanel = new javax.swing.JPanel();
        RentedCarDisplay = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        rentedcaryear = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        rentedcarbrand = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        rentedcarcolor = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        rentedcarprice = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        rentedcarrentprice = new javax.swing.JLabel();
        rentedcarduration = new javax.swing.JLabel();
        HistoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        garageRentTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        garageBuytable = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();

        jLabel20.setText("jLabel20");

        jLabel35.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel35.setText("Brand Name: ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(2000, 1045));
        setMinimumSize(new java.awt.Dimension(2000, 1045));
        setPreferredSize(new java.awt.Dimension(2000, 1045));
        setResizable(false);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bgFocusLost(evt);
            }
        });

        sidepanel.setBackground(new java.awt.Color(54, 33, 89));

        settings.setBackground(new java.awt.Color(64, 43, 100));
        settings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsMouseClicked(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/007-settings.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Logout");

        javax.swing.GroupLayout settingsLayout = new javax.swing.GroupLayout(settings);
        settings.setLayout(settingsLayout);
        settingsLayout.setHorizontalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        settingsLayout.setVerticalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        history.setBackground(new java.awt.Color(64, 43, 100));
        history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historyMouseClicked(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/002-history.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("History");

        javax.swing.GroupLayout historyLayout = new javax.swing.GroupLayout(history);
        history.setLayout(historyLayout);
        historyLayout.setHorizontalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
        );
        historyLayout.setVerticalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        RentedCar.setBackground(new java.awt.Color(64, 43, 100));
        RentedCar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RentedCarMouseClicked(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/002-car-rent.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Rented Car");

        javax.swing.GroupLayout RentedCarLayout = new javax.swing.GroupLayout(RentedCar);
        RentedCar.setLayout(RentedCarLayout);
        RentedCarLayout.setHorizontalGroup(
            RentedCarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RentedCarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
        );
        RentedCarLayout.setVerticalGroup(
            RentedCarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RentedCarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RentedCarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addContainerGap())
        );

        wash.setBackground(new java.awt.Color(64, 43, 100));
        wash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                washMouseClicked(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/001-car-service.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Wash Car");

        javax.swing.GroupLayout washLayout = new javax.swing.GroupLayout(wash);
        wash.setLayout(washLayout);
        washLayout.setHorizontalGroup(
            washLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(washLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
        );
        washLayout.setVerticalGroup(
            washLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(washLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(washLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        sell.setBackground(new java.awt.Color(64, 43, 100));
        sell.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sellMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                sellMouseMoved(evt);
            }
        });
        sell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sellMouseClicked(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/car.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sell Car");

        javax.swing.GroupLayout sellLayout = new javax.swing.GroupLayout(sell);
        sell.setLayout(sellLayout);
        sellLayout.setHorizontalGroup(
            sellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        sellLayout.setVerticalGroup(
            sellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sellLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        buy.setBackground(new java.awt.Color(64, 43, 100));
        buy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buyMouseEntered(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/001-car.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Buy / Rent Car");

        javax.swing.GroupLayout buyLayout = new javax.swing.GroupLayout(buy);
        buy.setLayout(buyLayout);
        buyLayout.setHorizontalGroup(
            buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        buyLayout.setVerticalGroup(
            buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        dashboard.setBackground(new java.awt.Color(85, 65, 118));
        dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardMouseClicked(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/006-home.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Dashboard");

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("BAU GARAGE");

        repair.setBackground(new java.awt.Color(64, 43, 100));
        repair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repairMouseClicked(evt);
            }
        });

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/maintenance.png"))); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Repair Car");

        javax.swing.GroupLayout repairLayout = new javax.swing.GroupLayout(repair);
        repair.setLayout(repairLayout);
        repairLayout.setHorizontalGroup(
            repairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repairLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
        );
        repairLayout.setVerticalGroup(
            repairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repairLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(repairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout sidepanelLayout = new javax.swing.GroupLayout(sidepanel);
        sidepanel.setLayout(sidepanelLayout);
        sidepanelLayout.setHorizontalGroup(
            sidepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(wash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(history, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(settings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sidepanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(sidepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(RentedCar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(repair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sidepanelLayout.setVerticalGroup(
            sidepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidepanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(buy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(wash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(repair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(RentedCar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(history, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(settings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        amountlabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        amountlabel.setForeground(new java.awt.Color(54, 33, 89));
        amountlabel.setText("Balance: ");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user.png"))); // NOI18N

        username.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        username.setForeground(new java.awt.Color(54, 33, 89));
        username.setText("USER HERE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(amountlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
                .addGap(53, 53, 53))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(amountlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setMaximumSize(new java.awt.Dimension(1610, 1260));
        mainPanel.setMinimumSize(new java.awt.Dimension(1610, 1260));
        mainPanel.setName(""); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1610, 1260));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DashboardPanel.setBackground(new java.awt.Color(255, 255, 255));
        DashboardPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        DashboardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DashboardPanelMouseClicked(evt);
            }
        });

        activityTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        activityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Person", "Activity", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        activityTable.setRowHeight(24);
        activityTable.setRowMargin(2);
        activityTable.setSelectionBackground(new java.awt.Color(204, 204, 255));
        activityTable.getTableHeader().setReorderingAllowed(false);
        dashboardTable2.setViewportView(activityTable);
        if (activityTable.getColumnModel().getColumnCount() > 0) {
            activityTable.getColumnModel().getColumn(0).setResizable(false);
            activityTable.getColumnModel().getColumn(1).setResizable(false);
            activityTable.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel6.setFont(new java.awt.Font("Arial", 0, 32)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Garage Recent Activities");

        javax.swing.GroupLayout DashboardPanelLayout = new javax.swing.GroupLayout(DashboardPanel);
        DashboardPanel.setLayout(DashboardPanelLayout);
        DashboardPanelLayout.setHorizontalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DashboardPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(pieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dashboardTable2, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );
        DashboardPanelLayout.setVerticalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dashboardTable2, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE))
                .addContainerGap(176, Short.MAX_VALUE))
        );

        mainPanel.add(DashboardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1610, 920));

        BuyCarPanel.setBackground(new java.awt.Color(255, 255, 255));
        BuyCarPanel.setMaximumSize(new java.awt.Dimension(1610, 890));
        BuyCarPanel.setMinimumSize(new java.awt.Dimension(1610, 890));
        BuyCarPanel.setPreferredSize(new java.awt.Dimension(1610, 890));
        BuyCarPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuyCarPanelMouseClicked(evt);
            }
        });

        traversePrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/001-left-arrow.png"))); // NOI18N
        traversePrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        traversePrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                traversePrevMouseClicked(evt);
            }
        });

        CarPhoto.setBackground(new java.awt.Color(51, 51, 51));
        CarPhoto.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        CarPhoto.setForeground(new java.awt.Color(51, 0, 51));
        CarPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CarPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CarPhoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        BuyCarButton.setBackground(new java.awt.Color(153, 153, 255));
        BuyCarButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        BuyCarButton.setForeground(new java.awt.Color(54, 33, 89));
        BuyCarButton.setText("Buy Car");
        BuyCarButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(54, 33, 89)));
        BuyCarButton.setContentAreaFilled(false);
        BuyCarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuyCarButton.setFocusPainted(false);
        BuyCarButton.setFocusable(false);
        BuyCarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuyCarButtonMouseClicked(evt);
            }
        });

        traverseNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/002-right-arrow.png"))); // NOI18N
        traverseNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        traverseNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                traverseNextMouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel23.setText("Car Details:");

        jLabel24.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel24.setText("days");

        Year.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Year.setText("Brand Name");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel26.setText("Year:");

        brandName.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        brandName.setText("Brand Name");

        jLabel28.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel28.setText("Color:");

        Color.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Color.setText("Brand Name");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel30.setText("Price:");

        Price.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Price.setText("Brand Name");

        jLabel33.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel33.setText("Rent Price:");

        jLabel34.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel34.setText("Rent This Car:");

        jLabel36.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel36.setText("Brand Name: ");

        rentPrice.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rentPrice.setText("0$");
        rentPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentPriceActionPerformed(evt);
            }
        });

        day.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        day.setText("0");
        day.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                dayCaretUpdate(evt);
            }
        });
        day.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                dayInputMethodTextChanged(evt);
            }
        });
        day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayActionPerformed(evt);
            }
        });

        rentCarButton.setBackground(new java.awt.Color(153, 153, 255));
        rentCarButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        rentCarButton.setForeground(new java.awt.Color(54, 33, 89));
        rentCarButton.setText("Rent Car");
        rentCarButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(54, 33, 89)));
        rentCarButton.setContentAreaFilled(false);
        rentCarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rentCarButton.setFocusPainted(false);
        rentCarButton.setFocusable(false);
        rentCarButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                rentCarButtonMouseDragged(evt);
            }
        });
        rentCarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rentCarButtonMouseClicked(evt);
            }
        });
        rentCarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentCarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BuyCarPanelLayout = new javax.swing.GroupLayout(BuyCarPanel);
        BuyCarPanel.setLayout(BuyCarPanelLayout);
        BuyCarPanelLayout.setHorizontalGroup(
            BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuyCarPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CarPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BuyCarPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(traversePrev)
                        .addGap(187, 187, 187)
                        .addComponent(BuyCarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184)
                        .addComponent(traverseNext)))
                .addGap(183, 183, 183)
                .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BuyCarPanelLayout.createSequentialGroup()
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel28)
                            .addComponent(jLabel30)
                            .addComponent(jLabel36))
                        .addGap(94, 94, 94)
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Price)
                            .addComponent(brandName)
                            .addComponent(Color)
                            .addComponent(Year)))
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(rentCarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(BuyCarPanelLayout.createSequentialGroup()
                            .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(day)
                                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(77, 77, 77)
                            .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(rentPrice)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)))))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        BuyCarPanelLayout.setVerticalGroup(
            BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuyCarPanelLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BuyCarPanelLayout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(brandName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(Year))
                        .addGap(18, 18, 18)
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(Color))
                        .addGap(18, 18, 18)
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(Price))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(day))
                        .addGap(18, 18, 18)
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(rentPrice))
                        .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BuyCarPanelLayout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addGroup(BuyCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(traversePrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(traverseNext, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(BuyCarPanelLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(rentCarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(BuyCarPanelLayout.createSequentialGroup()
                        .addComponent(CarPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BuyCarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(121, 121, 121))
        );

        mainPanel.add(BuyCarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1610, 890));

        SellCarPanel.setBackground(new java.awt.Color(255, 255, 255));
        SellCarPanel.setMaximumSize(new java.awt.Dimension(1610, 890));
        SellCarPanel.setMinimumSize(new java.awt.Dimension(1610, 890));
        SellCarPanel.setPreferredSize(new java.awt.Dimension(1610, 890));

        sellCarPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/001-left-arrow.png"))); // NOI18N
        sellCarPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sellCarPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sellCarPrevMouseClicked(evt);
            }
        });

        sellCarDisplay.setBackground(new java.awt.Color(51, 51, 51));
        sellCarDisplay.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        sellCarDisplay.setForeground(new java.awt.Color(51, 0, 51));
        sellCarDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sellCarDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sellCarDisplay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        requestSellButton.setBackground(new java.awt.Color(153, 153, 255));
        requestSellButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        requestSellButton.setForeground(new java.awt.Color(54, 33, 89));
        requestSellButton.setText("Request Sell");
        requestSellButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(54, 33, 89)));
        requestSellButton.setContentAreaFilled(false);
        requestSellButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        requestSellButton.setFocusPainted(false);
        requestSellButton.setFocusable(false);
        requestSellButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestSellButtonMouseClicked(evt);
            }
        });
        requestSellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestSellButtonActionPerformed(evt);
            }
        });

        sellCarNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/002-right-arrow.png"))); // NOI18N
        sellCarNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sellCarNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sellCarNextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout SellCarPanelLayout = new javax.swing.GroupLayout(SellCarPanel);
        SellCarPanel.setLayout(SellCarPanelLayout);
        SellCarPanelLayout.setHorizontalGroup(
            SellCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellCarPanelLayout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addGroup(SellCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SellCarPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(sellCarPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(180, 180, 180)
                        .addComponent(requestSellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(180, 180, 180)
                        .addComponent(sellCarNext))
                    .addComponent(sellCarDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(385, Short.MAX_VALUE))
        );
        SellCarPanelLayout.setVerticalGroup(
            SellCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellCarPanelLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(sellCarDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SellCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(requestSellButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sellCarPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sellCarNext, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        mainPanel.add(SellCarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        WashCarPanel.setBackground(new java.awt.Color(255, 255, 255));
        WashCarPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        WashCarPanel.setName(""); // NOI18N

        OwnedCarsDisplay.setBackground(new java.awt.Color(51, 51, 51));
        OwnedCarsDisplay.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        OwnedCarsDisplay.setForeground(new java.awt.Color(51, 0, 51));
        OwnedCarsDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OwnedCarsDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        washCarPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/001-left-arrow.png"))); // NOI18N
        washCarPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        washCarPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                washCarPrevMouseClicked(evt);
            }
        });

        washcarButtin.setBackground(new java.awt.Color(153, 153, 255));
        washcarButtin.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        washcarButtin.setForeground(new java.awt.Color(54, 33, 89));
        washcarButtin.setText("Wash Car");
        washcarButtin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(54, 33, 89)));
        washcarButtin.setContentAreaFilled(false);
        washcarButtin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        washcarButtin.setFocusPainted(false);
        washcarButtin.setFocusable(false);
        washcarButtin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                washcarButtinMouseClicked(evt);
            }
        });
        washcarButtin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                washcarButtinActionPerformed(evt);
            }
        });

        washCarNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/002-right-arrow.png"))); // NOI18N
        washCarNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        washCarNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                washCarNextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout WashCarPanelLayout = new javax.swing.GroupLayout(WashCarPanel);
        WashCarPanel.setLayout(WashCarPanelLayout);
        WashCarPanelLayout.setHorizontalGroup(
            WashCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WashCarPanelLayout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addGroup(WashCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(WashCarPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(washCarPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(172, 172, 172)
                        .addComponent(washcarButtin, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188)
                        .addComponent(washCarNext))
                    .addComponent(OwnedCarsDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(385, Short.MAX_VALUE))
        );
        WashCarPanelLayout.setVerticalGroup(
            WashCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WashCarPanelLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(OwnedCarsDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(WashCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(washcarButtin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(washCarPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(washCarNext, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        mainPanel.add(WashCarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1610, 890));

        repairCarPanel.setBackground(new java.awt.Color(255, 255, 255));
        repairCarPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        repairCarPanel.setName(""); // NOI18N

        repairCarDisplay.setBackground(new java.awt.Color(51, 51, 51));
        repairCarDisplay.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        repairCarDisplay.setForeground(new java.awt.Color(51, 0, 51));
        repairCarDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repairCarDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        repairCarPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/001-left-arrow.png"))); // NOI18N
        repairCarPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repairCarPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repairCarPrevMouseClicked(evt);
            }
        });

        RepairCarButton.setBackground(new java.awt.Color(153, 153, 255));
        RepairCarButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        RepairCarButton.setForeground(new java.awt.Color(54, 33, 89));
        RepairCarButton.setText("Repair Car");
        RepairCarButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(54, 33, 89)));
        RepairCarButton.setContentAreaFilled(false);
        RepairCarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RepairCarButton.setFocusPainted(false);
        RepairCarButton.setFocusable(false);
        RepairCarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RepairCarButtonMouseClicked(evt);
            }
        });
        RepairCarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RepairCarButtonActionPerformed(evt);
            }
        });

        repairCarNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/002-right-arrow.png"))); // NOI18N
        repairCarNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repairCarNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repairCarNextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout repairCarPanelLayout = new javax.swing.GroupLayout(repairCarPanel);
        repairCarPanel.setLayout(repairCarPanelLayout);
        repairCarPanelLayout.setHorizontalGroup(
            repairCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repairCarPanelLayout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addGroup(repairCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(repairCarPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(repairCarPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(172, 172, 172)
                        .addComponent(RepairCarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188)
                        .addComponent(repairCarNext))
                    .addComponent(repairCarDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(385, Short.MAX_VALUE))
        );
        repairCarPanelLayout.setVerticalGroup(
            repairCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repairCarPanelLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(repairCarDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(repairCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(RepairCarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(repairCarPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(repairCarNext, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        mainPanel.add(repairCarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1610, 890));

        RentCarPanel.setBackground(new java.awt.Color(255, 255, 255));
        RentCarPanel.setMaximumSize(new java.awt.Dimension(1610, 890));
        RentCarPanel.setMinimumSize(new java.awt.Dimension(1610, 890));
        RentCarPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RentCarPanelMouseClicked(evt);
            }
        });

        RentedCarDisplay.setBackground(new java.awt.Color(51, 51, 51));
        RentedCarDisplay.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        RentedCarDisplay.setForeground(new java.awt.Color(51, 0, 51));
        RentedCarDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RentedCarDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        RentedCarDisplay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel25.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel25.setText("Car Details:");

        rentedcaryear.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rentedcaryear.setText("null");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel29.setText("Year:");

        rentedcarbrand.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rentedcarbrand.setText("null");

        jLabel31.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel31.setText("Color:");

        rentedcarcolor.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rentedcarcolor.setText("null");

        jLabel32.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel32.setText("Price:");

        rentedcarprice.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rentedcarprice.setText("null");

        jLabel39.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel39.setText("Brand Name: ");

        jLabel27.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel27.setText("Remaining Days:");

        jLabel37.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel37.setText("Price:");

        rentedcarrentprice.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rentedcarrentprice.setText("null");

        rentedcarduration.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        rentedcarduration.setText("null");

        javax.swing.GroupLayout RentCarPanelLayout = new javax.swing.GroupLayout(RentCarPanel);
        RentCarPanel.setLayout(RentCarPanelLayout);
        RentCarPanelLayout.setHorizontalGroup(
            RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RentCarPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(RentedCarDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(183, 183, 183)
                .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RentCarPanelLayout.createSequentialGroup()
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel39)
                            .addComponent(jLabel37)
                            .addComponent(jLabel27))
                        .addGap(55, 55, 55)
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentedcarrentprice)
                            .addComponent(rentedcarprice)
                            .addComponent(rentedcarbrand)
                            .addComponent(rentedcarcolor)
                            .addComponent(rentedcaryear)
                            .addComponent(rentedcarduration))))
                .addContainerGap(163, Short.MAX_VALUE))
        );
        RentCarPanelLayout.setVerticalGroup(
            RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RentCarPanelLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RentCarPanelLayout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rentedcarbrand, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(rentedcaryear))
                        .addGap(18, 18, 18)
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(rentedcarcolor))
                        .addGap(18, 18, 18)
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(rentedcarprice))
                        .addGap(18, 18, 18)
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(rentedcarrentprice))
                        .addGap(18, 18, 18)
                        .addGroup(RentCarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(rentedcarduration))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(RentCarPanelLayout.createSequentialGroup()
                        .addComponent(RentedCarDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 225, Short.MAX_VALUE))))
        );

        mainPanel.add(RentCarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1610, 890));

        HistoryPanel.setBackground(new java.awt.Color(255, 255, 255));
        HistoryPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        HistoryPanel.setName(""); // NOI18N

        historyTable.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Activity Name", "Date", "Duration"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        historyTable.setRowHeight(30);
        historyTable.setRowMargin(5);
        jScrollPane1.setViewportView(historyTable);
        if (historyTable.getColumnModel().getColumnCount() > 0) {
            historyTable.getColumnModel().getColumn(0).setResizable(false);
            historyTable.getColumnModel().getColumn(1).setResizable(false);
            historyTable.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel5.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel5.setText("My Recent Activity");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        garageRentTable.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        garageRentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Activity Name", "Duration"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        garageRentTable.setRowHeight(30);
        garageRentTable.setRowMargin(5);
        jScrollPane2.setViewportView(garageRentTable);
        if (garageRentTable.getColumnModel().getColumnCount() > 0) {
            garageRentTable.getColumnModel().getColumn(0).setResizable(false);
            garageRentTable.getColumnModel().getColumn(1).setResizable(false);
            garageRentTable.getColumnModel().getColumn(2).setResizable(false);
        }

        garageBuytable.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        garageBuytable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Activity Name", "Amount", "Car"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        garageBuytable.setRowHeight(30);
        garageBuytable.setRowMargin(5);
        jScrollPane3.setViewportView(garageBuytable);
        if (garageBuytable.getColumnModel().getColumnCount() > 0) {
            garageBuytable.getColumnModel().getColumn(0).setResizable(false);
            garageBuytable.getColumnModel().getColumn(1).setResizable(false);
            garageBuytable.getColumnModel().getColumn(2).setResizable(false);
            garageBuytable.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel15.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel15.setText("Garage Recent Activity");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout HistoryPanelLayout = new javax.swing.GroupLayout(HistoryPanel);
        HistoryPanel.setLayout(HistoryPanelLayout);
        HistoryPanelLayout.setHorizontalGroup(
            HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistoryPanelLayout.createSequentialGroup()
                .addGroup(HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HistoryPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HistoryPanelLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HistoryPanelLayout.createSequentialGroup()
                        .addGroup(HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HistoryPanelLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(136, 136, 136))))
        );
        HistoryPanelLayout.setVerticalGroup(
            HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HistoryPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel15))
                .addGap(44, 44, 44)
                .addGroup(HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(HistoryPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(118, 118, 118))
        );

        mainPanel.add(HistoryPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1610, 890));

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addComponent(sidepanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidepanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(bgLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 913, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bgFocusLost

        // TODO add your handling code here:
    }//GEN-LAST:event_bgFocusLost

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
        // TODO add your handling code here:
        setColor(settings);
        resetColor(wash);
        resetColor(buy);
        resetColor(sell);
        resetColor(dashboard);
        resetColor(history);
        resetColor(repair);

        new Login().show();
        this.setVisible(false);

    }//GEN-LAST:event_settingsMouseClicked

    private void buyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buyMouseClicked
        // TODO add your handling code here:
        setColor(buy);
        resetColor(wash);
        resetColor(settings);
        resetColor(sell);
        resetColor(dashboard);
        resetColor(history);
        resetColor(repair);
        resetColor(RentedCar);

        BuyCarPanel.setVisible(true);
        showCar();
        DashboardPanel.setVisible(false);
        
        HistoryPanel.setVisible(false);
        SellCarPanel.setVisible(false);
        WashCarPanel.setVisible(false);
        RentCarPanel.setVisible(false);
        repairCarPanel.setVisible(false);


    }//GEN-LAST:event_buyMouseClicked

    private void sellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellMouseClicked
        // TODO add your handling code here:
        setColor(sell);
        resetColor(wash);
        resetColor(buy);
        resetColor(settings);
        resetColor(dashboard);
        resetColor(history);
        resetColor(repair);

        SellCarPanel.setVisible(true);
        DashboardPanel.setVisible(false);
        WashCarPanel.setVisible(false);
        RentCarPanel.setVisible(false);
        BuyCarPanel.setVisible(false);
        HistoryPanel.setVisible(false);
        repairCarPanel.setVisible(false);
        
        BufferedImage img = null;
        JFrame f = new JFrame();
        if (p.noCar()) {
            sellCarDisplay.setIcon(null);
            sellCarDisplay.setText("You have no cars!");
        } else {
            sellCarDisplay.setText("");
        }
        try {
            if (p.cars[0] != null) {
                img = ImageIO.read(new File(p.cars[0].image));
                sellCarDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }


    }//GEN-LAST:event_sellMouseClicked

    private void sellMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellMouseDragged
        // TODO add your handling code here:

    }//GEN-LAST:event_sellMouseDragged

    private void sellMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellMouseMoved
        // TODO add your handling code here:

    }//GEN-LAST:event_sellMouseMoved

    private void washMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_washMouseClicked
        // TODO add your handling code here:

        setColor(wash);
        resetColor(buy);
        resetColor(RentedCar);
        resetColor(settings);
        resetColor(sell);
        resetColor(dashboard);
        resetColor(history);
        resetColor(repair);

        WashCarPanel.setVisible(true);
        BuyCarPanel.setVisible(false);
        RentCarPanel.setVisible(false);
        DashboardPanel.setVisible(false);
        HistoryPanel.setVisible(false);
        SellCarPanel.setVisible(false);
        repairCarPanel.setVisible(false);

        BufferedImage img = null;
        JFrame f = new JFrame();
        if (p.noCar()) {
            OwnedCarsDisplay.setIcon(null);
            OwnedCarsDisplay.setText("You have no cars!");
        } else {
            OwnedCarsDisplay.setText("");
        }
        try {
            if (p.cars[0] != null) {
                img = ImageIO.read(new File(p.cars[0].image));
                washCar = p.cars[0];
                OwnedCarsDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }


    }//GEN-LAST:event_washMouseClicked

    private void dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMouseClicked
        // TODO add your handling code here:

        showChart();
        setColor(dashboard);
        resetColor(wash);
        resetColor(buy);
        resetColor(settings);
        resetColor(sell);
        resetColor(history);
        resetColor(repair);
        
        if (DashboardPanel.isVisible() == false) {
            DashboardPanel.setVisible(true);
        }
        WashCarPanel.setVisible(false);
        BuyCarPanel.setVisible(false);
        RentCarPanel.setVisible(false);
        HistoryPanel.setVisible(false);
        SellCarPanel.setVisible(false);
        repairCarPanel.setVisible(false);


    }//GEN-LAST:event_dashboardMouseClicked

    public final void showChart() {
        pieChart.clearData();
        int a = 0;
        String Models[] = garage.computeModels();
        int numOfEachModel[] = garage.computeProbability();
        //sort with corresponding brand
        for (int i = 0; i < numOfEachModel.length; i++) {
            for (int j = i; j < numOfEachModel.length; j++) {
                if (numOfEachModel[i] < numOfEachModel[j]) {
                    int num = numOfEachModel[j];
                    String s = Models[j];
                    numOfEachModel[j] = numOfEachModel[i];
                    Models[j] = Models[i];
                    numOfEachModel[i] = num;
                    Models[i] = s;
                    break;
                }
            }
        }

        //choose the color
        for (int i = 0; i < Models.length; i++) {
            if (i >= 1 && numOfEachModel[i] == numOfEachModel[i - 1]) {
                pieChart.addData(new ModelPieChart(Models[i], numOfEachModel[i], getColor(a)));
            } else {
                a = a + 20;
                pieChart.addData(new ModelPieChart(Models[i], numOfEachModel[i], getColor(a)));
            }
        }
    }

    public Color getColor(int index) {
        return new Color(54 + index, 33 + index, 89 + index);
    }

    private void historyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyMouseClicked
        // TODO add your handling code here:
        HistoryPanel.setVisible(true);
        BuyCarPanel.setVisible(false);
        SellCarPanel.setVisible(false);
        WashCarPanel.setVisible(false);
        RentCarPanel.setVisible(false);
        DashboardPanel.setVisible(false);
        repairCarPanel.setVisible(false);

        setColor(history);
        resetColor(wash);
        resetColor(buy);
        resetColor(settings);
        resetColor(dashboard);
        resetColor(sell);
        resetColor(RentedCar);
        resetColor(repair);

        updateTableOfHistory();
        updateRentTable();
        updateBuyTable();


    }//GEN-LAST:event_historyMouseClicked

    private void requestSellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestSellButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_requestSellButtonActionPerformed

    private void rentPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rentPriceActionPerformed

    private void dayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dayActionPerformed

    private void rentCarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentCarButtonActionPerformed
        // TODO add your handling code here:
        JFrame f = new JFrame();
        Node<Car> temp = garage.temp;
        if (Integer.parseInt(day.getText()) == 0) {
            return;
        }
        if(garage.rentCar(p, temp, Integer.parseInt(day.getText()))) {
            temp.data.carOwner = p.name;
            showCar(garage.cars_to_traverse.head);
            showCarDetails(garage.cars_to_traverse.head);
            amountlabel.setText("Balance: " + p.budget + "$");
            day.setText("");
            rentPrice.setText("0$");
            // the name of priority queue pq , it insert the rented car according to numbers of day to fetch the rented Car
            updateTableOfHistory();
            updateRentTable();
        } else {
             JOptionPane.showMessageDialog(f, "You already rented a car!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_rentCarButtonActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_jLabel5MouseClicked

    private void DashboardPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_DashboardPanelMouseClicked

    private void BuyCarPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuyCarPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BuyCarPanelMouseClicked

    private void traverseNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_traverseNextMouseClicked
        // TODO add your handling code here:
        forward();
    }//GEN-LAST:event_traverseNextMouseClicked


    private void traversePrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_traversePrevMouseClicked
        // TODO add your handling code here:
        backward();
    }//GEN-LAST:event_traversePrevMouseClicked

    private void dayInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_dayInputMethodTextChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_dayInputMethodTextChanged

    private void dayCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_dayCaretUpdate
        // TODO add your handling code here:
        JFrame f = new JFrame();

        Node<Car> temp = garage.temp;
        Runnable doAssist = new Runnable() {
            @Override
            public void run() {
                String input = day.getText();
                if (input.matches("[0-9]*") && !input.isEmpty()) {
                    rentPrice.setText((Integer.parseInt(input) * temp.data.rentPrice) + "");
                } else {
                    day.setText("");
                    rentPrice.setText("0");
                }
                if (input.isEmpty()) {
                    rentCarButton.setEnabled(false);
                } else {
                    rentCarButton.setEnabled(true);
                }
            }
        };
        SwingUtilities.invokeLater(doAssist);
    }//GEN-LAST:event_dayCaretUpdate

    private void rentCarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rentCarButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rentCarButtonMouseClicked

    private void BuyCarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuyCarButtonMouseClicked
        // TODO add your handling code here:
        temp = garage.temp;
        BufferedImage img = null;
        if (sellCar == null) {
            sellCar = temp.data;
            try {
                img = ImageIO.read(new File(sellCar.image));

            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            sellCarDisplay.setIcon(new ImageIcon(img));
            sellCarDisplay.setText("");
        }
        garage.sellCar(temp, p);
        temp = garage.temp;
        showCar(temp);
        showCarDetails(temp);
        amountlabel.setText("Balance: " + p.budget + "$");
        updateTableOfHistory();
        updateTable();
        updateBuyTable();

    }//GEN-LAST:event_BuyCarButtonMouseClicked

    private void sellCarPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellCarPrevMouseClicked
        if (p.cars[0] != null) {
            prevCar();
        }
    }//GEN-LAST:event_sellCarPrevMouseClicked
    public void nextCar() {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.cars[1] != null) {
                sellCar = p.cars[1];
                img = ImageIO.read(new File(p.cars[1].image));
                sellCarDisplay.setIcon(new ImageIcon(img));
            }
        } catch (IOException e) {
        }
    }
    private void sellCarNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellCarNextMouseClicked
        // TODO add your handling code here:  
        if (p.cars[1] != null) {
            nextCar();
        }
    }//GEN-LAST:event_sellCarNextMouseClicked

    private void requestSellButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requestSellButtonMouseClicked
        // TODO add your handling code here:
        JFrame f = new JFrame();
        if (p.cars[0].image.equals(sellCar.image) && p.cars[1] == null) {
            System.out.println("pass");
            if (!garage.buyCar(p, p.cars[0])) {
                return;
            }
            sellCar = null;
            amountlabel.setText("Balance: " + p.budget + "$");
            sellCarDisplay.setIcon(null);
            sellCarDisplay.setText("Empty Cars");
            JOptionPane.showMessageDialog(f, "Your car has been successfully sold to the garage!", "Success", JOptionPane.DEFAULT_OPTION);
            updateTable();
            return;
        }
        if (p.cars[0].image.equals(sellCar.image) && p.cars[1] != null) {
            BufferedImage img = null;
            Car temp = p.cars[1];
            if (!garage.buyCar(p, p.cars[0])) {
                return;
            }
            p.cars[0] = temp;
            p.cars[1] = null;
            sellCar = p.cars[0];
            try {
                img = ImageIO.read(new File(p.cars[0].image));
                sellCarDisplay.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            amountlabel.setText("Balance: " + p.budget + "$");
            JOptionPane.showMessageDialog(f, "Your car has been successfully sold to the garage!", "Success", JOptionPane.DEFAULT_OPTION);
        } else if (p.cars[1].image.equals(sellCar.image) && p.cars[0] != null) {
            if (!garage.buyCar(p, p.cars[1])) {
                return;
            }
            JOptionPane.showMessageDialog(f, "Your car has been successfully sold to the garage!", "Success", JOptionPane.DEFAULT_OPTION);
            prevCar();
        }
        updateTable();
        updateTableOfHistory();
    }//GEN-LAST:event_requestSellButtonMouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15MouseClicked

    private void rentCarButtonMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rentCarButtonMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_rentCarButtonMouseDragged

    private void RentedCarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RentedCarMouseClicked
        // TODO add your handling code here:
        setColor(RentedCar);
        resetColor(wash);
        resetColor(buy);
        resetColor(sell);
        resetColor(dashboard);
        resetColor(history);
        resetColor(repair);

        RentCarPanel.setVisible(true);
        
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            if (p.rentedCar != null) {
                RentedCarDisplay.setText("");
                img = ImageIO.read(new File(p.rentedCar.data.image));
                RentedCarDisplay.setIcon(new ImageIcon(img));
                rentedcarbrand.setText(p.rentedCar.data.brand);
                rentedcarcolor.setText(p.rentedCar.data.color);
                rentedcarprice.setText(p.rentedCar.data.price+"$");
                rentedcarrentprice.setText(p.rentedCar.data.rentPrice+"$");
                rentedcaryear.setText(p.rentedCar.data.yob);

                String s = (p.rentedcarDuration < 2) ? " day" : " days";
                rentedcarduration.setText(p.rentedcarDuration+s);
            } else {
                RentedCarDisplay.setText("No Rented Car Yet!");
            }
        } catch (IOException e) {
        }
        
        
        
        WashCarPanel.setVisible(false);
        DashboardPanel.setVisible(false);
        HistoryPanel.setVisible(false);
        BuyCarPanel.setVisible(false);
        SellCarPanel.setVisible(false);
        repairCarPanel.setVisible(false);


    }//GEN-LAST:event_RentedCarMouseClicked

    private void washCarPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_washCarPrevMouseClicked
        // TODO add your handling code here:
        washCarPrev();
    }//GEN-LAST:event_washCarPrevMouseClicked

    private void washcarButtinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_washcarButtinMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_washcarButtinMouseClicked

    private void washcarButtinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_washcarButtinActionPerformed
        // TODO add your handling code here:
        garage.WashCar(p, washCar);
        amountlabel.setText("Balance: " + p.budget + "$");
        updateTable();
    }//GEN-LAST:event_washcarButtinActionPerformed

    private void washCarNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_washCarNextMouseClicked
        // TODO add your handling code here:
        washCarNext();
    }//GEN-LAST:event_washCarNextMouseClicked

    private void RentCarPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RentCarPanelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_RentCarPanelMouseClicked

    private void buyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buyMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_buyMouseEntered

    private void repairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repairMouseClicked
        // TODO add your handling code here:
        setColor(repair);
        resetColor(wash);
        resetColor(RentedCar);
        resetColor(buy);
        resetColor(sell);
        resetColor(dashboard);
        resetColor(history);
        
        repairCarPanel.setVisible(true);
        
        if (p.noCar()) {
            repairCarDisplay.setIcon(null);
            repairCarDisplay.setText("You have no cars!");
        } else {
            repairCarDisplay.setText("");
        }
        repairCarPrev();
        
        RentCarPanel.setVisible(false);
        WashCarPanel.setVisible(false);
        DashboardPanel.setVisible(false);
        HistoryPanel.setVisible(false);
        BuyCarPanel.setVisible(false);
        SellCarPanel.setVisible(false);
        
        
        
    }//GEN-LAST:event_repairMouseClicked

    private void repairCarPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repairCarPrevMouseClicked
        // TODO add your handling code here:
        repairCarPrev();
    }//GEN-LAST:event_repairCarPrevMouseClicked

    private void RepairCarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RepairCarButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_RepairCarButtonMouseClicked

    private void RepairCarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RepairCarButtonActionPerformed
        // TODO add your handling code here:
        garage.RepairCar(p, repairCar);
        amountlabel.setText("Balance: " + p.budget + "$");
        updateTable();
    }//GEN-LAST:event_RepairCarButtonActionPerformed

    private void repairCarNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repairCarNextMouseClicked
        // TODO add your handling code here:
        repairCarNext();
    }//GEN-LAST:event_repairCarNextMouseClicked

    public boolean checkIfEmptyCars() {
        return p.noCar();
    }

    public void showCarDetails(Node<Car> temp) {
        BufferedImage img = null;
        JFrame f = new JFrame();
        try {
            img = ImageIO.read(new File(temp.data.image));
            CarPhoto.setIcon(new ImageIcon(img));
        } catch (IOException e) {
        }
        brandName.setText(temp.data.brand);
        Year.setText(temp.data.yob);
        Color.setText(temp.data.color);
        Price.setText(String.valueOf(temp.data.price));
    }

    public void setColor(JPanel panel) {
        panel.setBackground(new Color(85, 65, 118));
    }

    public void resetColor(JPanel panel) {
        panel.setBackground(new Color(64, 43, 100));
    }

    public final void showDashboard() {
        if (DashboardPanel.isVisible() == false) {
            DashboardPanel.setVisible(true);
        }
        WashCarPanel.setVisible(false);
        HistoryPanel.setVisible(false);
        RentCarPanel.setVisible(false);
        BuyCarPanel.setVisible(false);
        SellCarPanel.setVisible(false);
        repairCarPanel.setVisible(false);

    }

    public void alignTable() {
        Font headerFont = new Font("Verdana", Font.PLAIN, 18);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) activityTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        activityTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        for (int i = 0; i < activityTable.getColumnCount(); i++) {
            activityTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            activityTable.getTableHeader().setFont(headerFont);
        }

        ((DefaultTableCellRenderer) historyTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        historyTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        for (int i = 0; i < historyTable.getColumnCount(); i++) {
            historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            historyTable.getTableHeader().setFont(headerFont);
        }

        ((DefaultTableCellRenderer) garageBuytable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        garageBuytable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        for (int i = 0; i < garageBuytable.getColumnCount(); i++) {
            garageBuytable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            garageBuytable.getTableHeader().setFont(headerFont);
        }

        ((DefaultTableCellRenderer) garageRentTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        garageRentTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        for (int i = 0; i < garageRentTable.getColumnCount(); i++) {
            garageRentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            garageRentTable.getTableHeader().setFont(headerFont);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
//                new MainFrame().DashboardPanel.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuyCarButton;
    private javax.swing.JPanel BuyCarPanel;
    private javax.swing.JLabel CarPhoto;
    private javax.swing.JLabel Color;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JPanel HistoryPanel;
    private javax.swing.JLabel OwnedCarsDisplay;
    private javax.swing.JLabel Price;
    private javax.swing.JPanel RentCarPanel;
    private javax.swing.JPanel RentedCar;
    private javax.swing.JLabel RentedCarDisplay;
    private javax.swing.JButton RepairCarButton;
    private javax.swing.JPanel SellCarPanel;
    private javax.swing.JPanel WashCarPanel;
    private javax.swing.JLabel Year;
    private javax.swing.JTable activityTable;
    private javax.swing.JLabel amountlabel;
    private javax.swing.JPanel bg;
    private javax.swing.JLabel brandName;
    private javax.swing.JPanel buy;
    private javax.swing.JPanel dashboard;
    private javax.swing.JScrollPane dashboardTable2;
    private javax.swing.JTextField day;
    private javax.swing.JTable garageBuytable;
    private javax.swing.JTable garageRentTable;
    private javax.swing.JPanel history;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private chart.PieChart pieChart;
    private javax.swing.JButton rentCarButton;
    private javax.swing.JTextField rentPrice;
    private javax.swing.JLabel rentedcarbrand;
    private javax.swing.JLabel rentedcarcolor;
    private javax.swing.JLabel rentedcarduration;
    private javax.swing.JLabel rentedcarprice;
    private javax.swing.JLabel rentedcarrentprice;
    private javax.swing.JLabel rentedcaryear;
    private javax.swing.JPanel repair;
    private javax.swing.JLabel repairCarDisplay;
    private javax.swing.JLabel repairCarNext;
    private javax.swing.JPanel repairCarPanel;
    private javax.swing.JLabel repairCarPrev;
    private javax.swing.JButton requestSellButton;
    private javax.swing.JPanel sell;
    private javax.swing.JLabel sellCarDisplay;
    private javax.swing.JLabel sellCarNext;
    private javax.swing.JLabel sellCarPrev;
    private javax.swing.JPanel settings;
    private javax.swing.JPanel sidepanel;
    private javax.swing.JLabel traverseNext;
    private javax.swing.JLabel traversePrev;
    private javax.swing.JLabel username;
    private javax.swing.JPanel wash;
    private javax.swing.JLabel washCarNext;
    private javax.swing.JLabel washCarPrev;
    private javax.swing.JButton washcarButtin;
    // End of variables declaration//GEN-END:variables
}
