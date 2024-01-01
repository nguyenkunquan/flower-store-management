package business;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import model.Flower;
import model.Order;
import model.OrderDetail;
import util.Validation;

/**
 * Tủ/mảng/danh sách chứa thông tin các loài hoa có trong cửa hàng
 * @author acer
 */
public class Flowers extends HashSet<Flower> {

    /**
     * Hàm kiểm tra xem đã tồn tại id loài hoa trong danh sách chưa
     * @param id: mã số cần kiểm tra
     * @return true: đã tồn tại; false: chưa tồn tại
     */
    public boolean checkExistByID(String id) {
        boolean flag = false;
        for (Flower f : this) {
            if (f.getId().equalsIgnoreCase(id)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Hàm trả về 1 biến object/biến tham chiếu Flower theo id
     * @param id: id của loài hoa cần tìm
     * @return f: trả về biến object cần tìm; null: trả về null (không tồn tại biến object cần tìm)
     */
    public Flower getAFlowerByID(String id) {
        for (Flower f : this) {
            if (f.getId().equalsIgnoreCase(id))
                return f;
        }
        return null;
    }
    
    /**
     * Hàm trả về 1 biến object/biến tham chiếu Flower theo tên
     * @param name: tên của loài hoa cần tìm
     * @return f: trả về biến object cần tìm; null: trả về null (không tồn tại biến object cần tìm)
     */
    public Flower getAFlowerByName(String name) {
        for (Flower f : this) {
            if (f.getDescription().equalsIgnoreCase(name))
                return f;
        }
        return null;
    }
    
    /**
     * Thêm một hồ sơ chứa các thông tin cần thiết của 1 loài hoa vào trong tủ/mảng/dach sách
     */
    public void addAFlower() {
        String id = null, description, importDate, category;
        double unitPrice;
        int no = this.size() + 1;

        boolean flag = false;
        while (!flag) {
            try {
                id = Validation.getFormatString("Input the flower id (Ex: F0001): ", "^[F][0-9]{4}$");
                if (checkExistByID(id)) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("The id was existed!!!");
            }
        }
        description = Validation.getFormatString("Input the description (3 to 50 characters): ", "^^.{3,50}$");
        importDate = Validation.getDate("Input the import date: ");
        unitPrice = Validation.getPositiveDouble("Input the unit price: ");
        category = Validation.getString("Input the category: ");
        System.out.println("SUCCESS");
        this.add(new Flower(no, id, description, importDate, unitPrice, category));
    }

    /**
     * In ra thông tin của 1 loài hoa cần tìm theo mã số hoặc tên
     */
    public void findAFlower() {
        if (this.isEmpty()) {
            System.err.println("There is no flower in store");
            return;
        }
        String s = Validation.getString("Input the flower id or flower name that you want to find (Ex: F0001): ");
        for (Flower f : this) {            
            if (f.getId().equalsIgnoreCase(s) || f.getDescription().equalsIgnoreCase(s)) {
                System.out.println("|No.  |Id   |Description    |Import Date |Unit Price|       Category|");
                System.out.println(f.toString());
                return;
            }
        }
        System.err.println("The flower does not exist");
    }

    /**
     * Cập nhật lại thông tin của 1 loài hoa theo các lựa chọn
     *
     * - 1: Cập nhật tên - 2: Cập nhật ngày nhập hàng - 3: Cập nhật đơn vị giá
     * - 4: Cập nhật loại hoa - 5: Thoát khỏi việc cập nhật
     */
    public void updateAFlower() {
        int choice = 0;
        Flower f = null;
        if (this.isEmpty()) {
            System.err.println("There is no flower in store!");
            return;
        }
        String s = Validation.getString("Input the flower name that you want to update: ");
        for (Flower flower : this) {
            if (flower.getDescription().equalsIgnoreCase(s)) {
                f = flower;
            }
        }
        if (f == null) {
            System.err.println("The flower does not exist");
            return;
        }
        System.out.println("|-------------------------------------|");
        System.out.println("Do you want to change? (Choosing 1 - 5)");
        System.out.println("1. Update the description");
        System.out.println("2. Update the import date");
        System.out.println("3. Update the unit price");
        System.out.println("4. Update the category");
        System.out.println("5. Quit");
        System.out.print("Choosing your option: ");
        choice = Validation.getChoice(1, 5);
        if (choice != 5) {
            switch (choice) {
                case 1:                
                    f.setDescription(Validation.getFormatString("Input new description", "^.{3,50}$"));
                    break;
                case 2:
                    f.setImportDate(Validation.getDate("Input new import date: "));
                    break;
                case 3:
                    f.setUnitPrice(Validation.getPositiveDouble("Input new unit price: "));
                    break;
                case 4:
                    f.setCategory(Validation.getString("Input new category: "));
                    break;
        }
            System.out.println("SUCCESS");
        }
    }
    
    /**
     * Xóa thông tin của 1 loài hoa khỏi danh sách, không được xóa nếu loài hoa đã tồn tại trong đơn đặt
     * @param o: danh sách lưu trữ các hóa đơn/đơn đặt có trong cửa hàng,
     * phục vụ cho việc kiểm tra xem loài hoa muốn xóa đã có trong đơn đặt nào hay không
     */
    public void deleteAFLower(Orders o) {
        if (this.isEmpty()) {
            System.err.println("There is no flower in store!");
            return;
        }
        String id = Validation.getFormatString("Input the flower id that you want to delete (Ex: F0001): ", "^[F][0-9]{4}$");
        if (checkExistByID(id) != true) {
            System.err.println("There is no " + id + " id in store!!!");
            return;
        }
        for (Order order : o) {
            for (OrderDetail orderDetail : order.getoD()) {
                if (orderDetail.getFlowerId().equalsIgnoreCase(id)) {
                    System.out.println("The flower can not be deleted!");
                    return;
                }
            }
        }
        System.out.print("Do you want to delete " + id + " id in store? (1. yes; 2. no): ");
        int choice = Validation.getChoice(1, 2);
        if (choice == 1) {
            for (Flower f : this) {
                if (f.getNo() > getAFlowerByID(id).getNo())
                    f.setNo(f.getNo() - 1);
        }
            this.remove(getAFlowerByID(id));
            System.out.println("SUCCESS");
        }
    }
    
    /**
     * Lưu dữ liệu danh sách thông tin các loài hoa
     * @throws Exception ném một ngoại lệ một cách rõ ràng
     */
    public void saveData() throws Exception {
        if (this.isEmpty()) {
            System.err.println("There is no flower in the store!!!");
        } else {
            String pathFlower = System.getProperty("user.dir") + "/src/data/flowers.dat";
            FileOutputStream fileOut;
            ObjectOutputStream objectOut;
            try {
                fileOut = new FileOutputStream(pathFlower);
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
        String pathFlower = System.getProperty("user.dir") + "/src/data/flowers.dat";
        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        try {
            File file = new File(pathFlower);
            fileIn = new FileInputStream(file);
            objectIn = new ObjectInputStream(fileIn);
            Object obj;
            while ((obj = objectIn.readObject()) != null) {
                if (obj instanceof Flower) {
                    this.add((Flower) obj);
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
