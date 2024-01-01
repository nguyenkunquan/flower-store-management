
package model;

import business.Flowers;
import java.io.Serializable;
import java.util.List;

/**
 * Thông tin của 1 hóa đơn.
 * Mỗi hóa đơn chỉ có duy nhất một OrderHeader, tối thiểu một OrderDetail
 * nhưng có thể có nhiều hơn một OrderDetail
 * @author acer
 */
public class Order implements Serializable{
    OrderHeader oH;
    List<OrderDetail> oD;
    int no;

    public Order(int no, OrderHeader oH, List<OrderDetail> oD) {
        this.oH = oH;
        this.oD = oD;
        this.no = no;
    }

    public OrderHeader getoH() {
        return oH;
    }

    public List<OrderDetail> getoD() {
        return oD;
    }

    public void setoD(List<OrderDetail> oD) {
        this.oD = oD;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
    
    /**
     * Hàm trả về chi phí tiền của 1 hóa đơn
     * @return số tiền của hóa đơn
     */
    public double getOrderTotal() {
        double total = 0;
        for (OrderDetail orderDetail : oD) {
            total += orderDetail.getCost();
        }
        return total;
    }
    
    /**
     * Hàm trả về số lượng hoa được đặt trong 1 hóa đơn
     * @return số lượng hoa được đặt trong 1 hóa đơn
     */
    public int getFlowerCount() {
        int total = 0;
        for (OrderDetail orderDetail : oD) {
            total += orderDetail.getQuantity();
        }
        return total;
    }
    
    /**
     * Hàm trả về chuỗi thông tin chi tiết về các loài hoa kèm số lượng được mua trong 1 hóa đơn
     * @param f danh sách thông tin các loài hoa có trong cửa hàng
     * @return chuỗi thông tin chi tiết về các loài hoa kèm số lượng được mua
     */
    public String getFlowerCount(Flowers f) {
        String flr = "";
        for (OrderDetail orderDetail : oD) {
            flr = flr + (orderDetail.getFlowerName(f) + "(" + orderDetail.getQuantity() + ")" ) + ", ";
        }
        return flr.trim().substring(0, flr.length() - 2);
    }
    
    public String toString(Flowers f) {
        return String.format("|%4d", no) + oH.toString()
             + String.format("%20s|%11.2f|", getFlowerCount(f), getOrderTotal());
    }
    
}
