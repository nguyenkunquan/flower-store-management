package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Nơi lưu trữ các hàm được tái sử dụng nhiều lần
 * @author acer
 */
public class Validation {

    private static Scanner sc = new Scanner(System.in);

    /**
     * Hàm trả về chuỗi theo đúng định dạng ta cần
     * @param mess thông điệp gửi tới người dùng
     * @param format định dạng ta cần
     * @return chuỗi đúng theo định dạng ta cần
     */
    public static String getFormatString(String mess, String format) {
        String s = null;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print(mess);
                s = sc.nextLine();
                if (!s.matches(format)) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("Invalid input!!!");
            }
        }
        return s;
    }

    /**
     * Hàm trả về một số thực dương
     * @param mess thông điệp gửi tới người dùng
     * @return số thực dương
     */
    public static double getPositiveDouble(String mess) {
        double d = 0;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print(mess);
                d = Double.parseDouble(sc.nextLine());
                if (d <= 0) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("Please input a positive number!");
            }
        }
        return d;
    }

    /**
     * Hàm trả về một số nguyên dương
     * @param mess thông điệp gửi tới người dùng
     * @return số nguyên dương
     */
    public static int getPositiveInteger(String mess) {
        int i = 0;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print(mess);
                i = Integer.parseInt(sc.nextLine());
                if (i <= 0) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("Please input a positive number!");
            }
        }
        return i;
    }

    /**
     * Hàm trả về một chuỗi không rỗng
     * @param mess thông điệp gửi tới người dùng
     * @return chuỗi có kí tự
     */
    public static String getString(String mess) {
        String s = null;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print(mess);
                s = sc.nextLine();
                if (s.isEmpty()) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("Empty input!");
            }
        }
        return s;
    }

    /**
     * Hàm trả về lựa chọn của người dùng
     * @param min lựa chọn thấp nhất
     * @param max lựa chọn cao nhất
     * @return lựa chọn nằm trong khoảng lựa chọn thấp nhấp đến cao nhất
     */
    public static int getChoice(int min, int max) {
        int choice = 0;
        boolean flag = false;
        while (!flag) {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < min || choice > max) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println("Invalid choice!");
            }
        }
        return choice;
    }
    
    /**
     * Hàm trả về lựa chọn của người dùng
     * @param msg thông điệp gửi tới người dùng
     * @param errorMsg thông báo lỗi
     * @param min lựa chọn thấp nhất
     * @param max lựa chọn cao nhất
     * @return lựa chọn nằm trong khoảng lựa chọn thấp nhấp đến cao nhất
     */
    public static int getChoiceWithMess(String msg, String errorMsg, int min, int max) {
        int choice = 0;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print(msg);
                choice = Integer.parseInt(sc.nextLine());
                if (choice < min || choice > max) {
                    throw new Exception();
                }
                flag = true;
            } catch (Exception e) {
                System.err.println(errorMsg);
            }
        }
        return choice;
    }
    
    /**
     * Hàm so sánh việc nhập 2 ngày có hợp lệ hay không
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return true: ngày bắt đầu nhỏ hơn hoặc trùng ngày kết thúc; false: ngày bắt đầu lớn hơn ngày kết thúc
     * @throws ParseException
     */
    public static boolean compareDate(String startDate, String endDate) throws ParseException{
        boolean flag = true;
        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdformat.parse(startDate);
        Date d2 = sdformat.parse(endDate);
        if(d1.compareTo(d2) > 0)
            flag = false;
        return flag;
    }
    
    /**
     * Hàm yêu cầu người dùng nhập lựa chọn field hợp lệ
     * @param mess thông điệp gửi tới người dùng
     * @return field mà ta cần
     */
    public static String checkSortedField(String mess) {
        String value = "";
        boolean flag = false;
        while (flag != true) {
            try {
                System.out.print(mess);
                value = sc.nextLine();
                if (value.isEmpty()) {
                    throw new Exception("Please input value!");
                }
                if (value.equalsIgnoreCase("I") || value.equalsIgnoreCase("N") || value.equalsIgnoreCase("D") || value.equalsIgnoreCase("T")) {
                    flag = true;
                } else {
                    throw new Exception("Please input I = Order ID Or D = Order Date Or N = Customer's Name Or T = Order Total!");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return value;
    }
    
    /**
     * Hàm yêu cầu người dùng nhập lựa chọn order hợp lệ
     * @param mess thông điệp gửi tới người dùng
     * @return order mà ta cần
     */
    public static String checkSortedOrder(String mess) {
        String order = null;
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print(mess);
                order = sc.nextLine();
                if (order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC")) {
                    flag = true;
                }
                else throw new Exception();
            } catch (Exception e) {
                System.err.println("Invalid input!!!");
            }
        }
        return order;
    }
    
    /**
     * Hàm kiểm tra xem người dùng có nhập đúng định dạng ngày hay không
     * @param date chuỗi cần kiểm tra
     * @return true: chuỗi đúng theo định dạng ngày; false: chuỗi không đúng theo định dạng ngày
     */
    public static boolean checkDate(String date) {
        if (date == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    /**
     * Kiểm tra chuỗi có là định dạng "ngày/tháng/năm"
     *
     * @param mess Tin nhắn được in ra trước khi nhập dữ liệu
     * @return Trả về chuỗi có định dạng "ngày/tháng/năm"
     */
    public static String getDate(String mess) {
        String date = "";
        boolean flag = false;
        while (flag != true) {
            date = Validation.getString(mess);
            if (Validation.checkDate(date)) {
                flag = true;
            } else {
                System.err.println("Please re-enter the date!");
            }
        }
        return date;
    }
}
