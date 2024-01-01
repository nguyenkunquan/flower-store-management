package business;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import model.Flower;
import model.Order;
import model.OrderDetail;
import model.OrderHeader;
import util.Validation;

/**
 * Tủ/mảng/danh sách chứa thông tin các hóa đơn/đơn đặt có trong cửa hàng
 * @author acer
 */
public class Orders extends HashSet<Order> {

    /**
     * Hàm kiểm tra xem đã tồn tại id đơn đặt trong cửa hàng chưa
     * @param s: mã số cần kiểm tra
     * @return true: đã tồn tại; false: chưa tồn tại
     */
    public boolean checkExistOrderID(String s) {
        boolean flag = false;
        for (Order o : this) {
            if (o.getoH().getOrderId().equalsIgnoreCase(s)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Hàm kiểm tra xem đã tồn tại id đơn đặt chi tiết trong cửa hàng chưa
     * @param s: mã số cần kiểm tra
     * @return true: đã tồn tại; false: chưa tồn tại
     */
    public boolean checkExistOrderDetailID(String s) {
        boolean flag = false;
        for (Order o : this) {
            for (OrderDetail od : o.getoD()) {
                if (od.getOrderDetailId().equalsIgnoreCase(s)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * Thêm một hóa đơn/ đơn đặt vào trong danh sách
     * @param f: danh sách chứa các thông tin loài hoa đang có trong cửa hàng,
     * phục vụ cho việc thêm hóa đơn sẽ hợp lệ
     */
    public void addAnOrder(Flowers f) {
        List<OrderDetail> oD = new ArrayList<>();
        int no = this.size() + 1;
        if (f.isEmpty()) {
            System.err.println("There is no flower in store!");
            return;
        }

        String orderID = null;
        boolean flag1 = false;
        while (!flag1) {
            try {
                orderID = Validation.getFormatString("Input the order id (Ex: OH0001): ", "^[O][H][0-9]{4}$");
                if (checkExistOrderID(orderID)) {
                    throw new Exception();
                }
                flag1 = true;
            } catch (Exception e) {
                System.err.println("Duplicated id!");
            }
        }
        String orderDate = Validation.getDate("Input the order date: ");
        String customerName = Validation.getString("Input the customer's name: ");
        OrderHeader oH = new OrderHeader(orderID, orderDate, customerName);

        boolean continueAdding = true;
        while (continueAdding) {
            String orderDetailID = null;
            boolean flag2 = false;
            while (!flag2) {
                try {
                    orderDetailID = Validation.getFormatString("Input the order detail id (Ex: OD0001): ", "^[O][D][0-9]{4}");
                    if (checkExistOrderDetailID(orderDetailID)) {
                        throw new Exception();
                    }
                    flag2 = true;
                } catch (Exception e) {
                    System.err.println("Duplicated id!");
                }
            }

            String flowerID = null;
            boolean flag3 = false;
            while (!flag3) {
                try {
                    flowerID = Validation.getFormatString("Input the flower id (Ex: F0001): ", "^[F][0-9]{4}");
                    for (Flower flower : f) {
                        if (flower.getId().equals(flowerID)) {
                            flag3 = true;
                        }
                    }
                    if (flag3 == false) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.err.println("There is no " + flowerID + " id in store");
                }
            }
            int quantity = Validation.getPositiveInteger("Input the quantity: ");
            double cost = quantity * f.getAFlowerByID(flowerID).getUnitPrice();
            OrderDetail od = new OrderDetail(orderDetailID, flowerID, quantity, cost);
            oD.add(od);
            System.out.println("Do you want to continue adding a new order details?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Your choice: ");
            int choice = Validation.getChoice(1, 2);
            if (choice == 2) {
                continueAdding = false;
            }
        }

        this.add(new Order(no, oH, oD));
    }

    /**
     * In ra thông tin của các đơn đặt có ngày đặt hàng nằm trong một khoảng ngày nhất định
     * @param f: danh sách thông tin các loài hoa đang có trong cửa hàng
     * @throws ParseException: ném một ngoại lệ một cách rõ ràng
     */
    public void displayOrders(Flowers f) throws ParseException {        
        List<Order> list = new ArrayList<>();
        int totalF = 0;
        double totalP = 0;
        
        if (isEmpty()) {
            System.err.println("There is no order in store!");
            return;
        }     
        
        String startDate = null, endDate = null;
        boolean flag = false;
        while (!flag) {
            try {
                startDate = Validation.getDate("Input start date: ");
                endDate = Validation.getDate("Input end date: ");
                if (Validation.compareDate(startDate, endDate) == false) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("Your input is wrong (Cause: start date after end date!!!)");
            }
        }

        
        System.out.println("|No. |Order id  |Order date     |Customers    |        Flower count|Order total|");
        for (Order o : this) {            
            if (Validation.compareDate(startDate, o.getoH().getOrderDate()) && Validation.compareDate(o.getoH().getOrderDate(), endDate)) {                
                System.out.println(o.toString(f));
                list.add(o);
            }
        }
        for (Order order : list) {
            totalF += order.getFlowerCount();
            totalP += order.getOrderTotal();
        }
        System.out.printf("|    |Total     |               |             |%20d|%11.2f|", totalF, totalP);
    }

    /**
     * Hàm tính tổng số lượng hoa được đặt trong cửa hàng
     * @return số lượng hoa đã/đang được đặt
     */
    public int totalFlowerCount() {
        int total = 0;
        for (Order o : this) {
            for (OrderDetail od : o.getoD()) {
                total += od.getQuantity();
            }
        }
        return total;
    }
    
    /**
     * Hàm tính tổng số lượng tiền thu về cho cửa hàng
     * @return tổng tiền thu về được
     */
    public double totalPrice() {
        int total = 0;
        for (Order o : this) {
            for (OrderDetail od : o.getoD()) {
                total += od.getCost();
            }
        }
        return total;
    }
    
    /**
     * Sắp xếp các đơn hàng theo trường sắp xếp (ID; Tên khách hàng; Ngày đặt
     * hàng; Tổng tiền đơn hàng) và thứ tự sắp xếp (Tăng dần; Giảm dần)
     *
     * @param list Danh sách đơn hàng đưa vào để sắp xếp
     * @param sortedField I = ID; N = Tên khách hàng; D = Ngày đặt hàng; T = Tổng tiền đơn hàng
     * @param sortedOrder ASC = Tăng dần; DESC = Giảm dần
     * @return Trả về danh sách các đơn hàng đã được sắp xếp
     * @throws Exception ném một ngoại lệ một cách rõ ràng
     */
    public ArrayList<Order> sort(ArrayList<Order> list, String sortedField, String sortedOrder) throws Exception {
        if (sortedField.equalsIgnoreCase("I") && sortedOrder.equalsIgnoreCase("ASC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return (o1.getoH().getOrderId()).compareTo(o2.getoH().getOrderId());
                }
            };
            Collections.sort(list, com);
        }

        if (sortedField.equalsIgnoreCase("I") && sortedOrder.equalsIgnoreCase("DESC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return (o2.getoH().getOrderId()).compareTo(o1.getoH().getOrderId());
                }
            };
            Collections.sort(list, com);
        }

        if (sortedField.equalsIgnoreCase("D") && sortedOrder.equalsIgnoreCase("ASC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date1 = (Date) sdf.parse(o1.getoH().getOrderDate());
                        Date date2 = (Date) sdf.parse(o2.getoH().getOrderDate());
                        return date1.compareTo(date2);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    return 0;
                }
            };
            Collections.sort(list, com);
        }

        if (sortedField.equalsIgnoreCase("D") && sortedOrder.equalsIgnoreCase("DESC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date1 = (Date) sdf.parse(o1.getoH().getOrderDate());
                        Date date2 = (Date) sdf.parse(o2.getoH().getOrderDate());
                        return date2.compareTo(date1);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    return 0;
                }
            };
            Collections.sort(list, com);
        }

        if (sortedField.equalsIgnoreCase("N") && sortedOrder.equalsIgnoreCase("ASC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return (o1.getoH().getCustomerName()).compareTo(o2.getoH().getCustomerName());
                }
            };
            Collections.sort(list, com);
        }

        if (sortedField.equalsIgnoreCase("N") && sortedOrder.equalsIgnoreCase("DESC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return (o2.getoH().getCustomerName()).compareTo(o1.getoH().getCustomerName());
                }
            };
            Collections.sort(list, com);
        }

        if (sortedField.equalsIgnoreCase("T") && sortedOrder.equalsIgnoreCase("ASC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return (int) (o1.getOrderTotal() - o2.getOrderTotal());
                }
            };
            Collections.sort(list, com);
        }

        if (sortedField.equalsIgnoreCase("T") && sortedOrder.equalsIgnoreCase("DESC")) {
            Comparator<Order> com = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return (int) (o2.getOrderTotal() - o1.getOrderTotal());
                }
            };
            Collections.sort(list, com);
        }
        return list;
    }

    /**
     * Sắp xếp danh sách thông tin của các đơn đặt tùy theo lựa chọn
     * @param flo danh sách thông tin các loài hoa đang có trong cửa hàng
     * @throws Exception ném một ngoại lệ một cách rõ ràng
     */
    public void sortOrders(Flowers flo) throws Exception {
        ArrayList<Order> list = new ArrayList<>(this);
        if (list.isEmpty()) {
            System.err.println("There is no order in store");
        } else {
            String sortedField = Validation.checkSortedField("Please, input the sorted field ( I = Order ID Or D = Order Date Or N = Customer's Name Or T = Order Total): ");
            String sortedOrder = Validation.checkSortedOrder("Please, input the sorted order ( ASC = Ascending Or DESC = Descending): ");

            ArrayList<Order> sortedList = sort(list, sortedField, sortedOrder);
            DecimalFormat df = new DecimalFormat("$ ####.##");

            System.out.println(" ------------------------------------------------------------------------- ");
            System.out.println("|No. |Order id  |Order date     |Customers    |        Flower count|Order total|");
            for (int i = 0; i < sortedList.size(); i++) {
                System.out.println(" ------------------------------------------------------------------------------------------------------------------ ");
                System.out.println(sortedList.get(i).toString(flo));
            }
            System.out.printf("|    |Total     |               |             |%20d|%11.2f|", this.totalFlowerCount(), this.totalPrice());
        }
    }
    
    /**
     * Lưu dữ liệu danh sách thông tin các đơn đặt
     * @throws Exception ném một ngoại lệ một cách rõ ràng
     */
    public void saveData() throws Exception {
        if (this.isEmpty()) {
            System.err.println("There is no order in the store!!!");
        } else {
            String pathOrders = System.getProperty("user.dir") + "/src/data/orders.dat";
            FileOutputStream fileOut;
            ObjectOutputStream objectOut;
            try {
                fileOut = new FileOutputStream(pathOrders);
                objectOut = new ObjectOutputStream(fileOut);
                for (Object obj : this) {
                    objectOut.writeObject(obj);
                }
                objectOut.flush();
                objectOut.close();
                fileOut.close();
                System.out.println("SAVED!!!");
            } catch (Exception ex) {
                System.err.println("FAIL!!!");
            }
        }
    }
    
    /**
     * Tải dữ liệu danh sách chứa thông tin các loài hoa đã lưu
     *
     * @throws Exception ném một ngoại lệ một cách rõ ràng
     */
    public void loadData() throws Exception {
        List<Order> arr = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        try {
            String pathOrder = System.getProperty("user.dir") + "/src/data/orders.dat";
            File file = new File(pathOrder);
            fileIn = new FileInputStream(file);
            objectIn = new ObjectInputStream(fileIn);
            Object obj;
            while ((obj = objectIn.readObject()) != null) {
                if (obj instanceof Order) {
                    this.add((Order) obj);
                }
            }
            objectIn.close();
            fileIn.close();

        } catch (EOFException eof) {
            System.out.println("LOADED!!!");
        } catch (Exception ex) {
            System.err.println("FAIL!!!");
        } finally {
            if (objectIn != null) {
                objectIn.close();
            }
            if (fileIn != null) {
                fileIn.close();
            }
        }
    }
}
