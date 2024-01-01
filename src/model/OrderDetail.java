
package model;

import business.Flowers;
import java.io.Serializable;

public class OrderDetail implements Serializable{
    String orderDetailId, flowerId;
    int quantity;
    double cost;

    public OrderDetail(String orderDetailId, String flowerId, int quantity, double cost) {
        this.orderDetailId = orderDetailId;
        this.flowerId = flowerId;
        this.quantity = quantity;
        this.cost = cost;
    }
    
    public String getOrderDetailId() {
        return orderDetailId;
    }

    public String getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    /**
     * Hàm trả về tên của loài hoa có mã số id trùng với mã số loài hoa được đặt trong 1 hóa đơn
     * @param f danh sách các loài hoa có trong cửa hàng
     * @return tên loài hoa
     */
    public String getFlowerName (Flowers f) {
        String name = null;
        for (Flower flower : f) {
            if (flower.getId().equalsIgnoreCase(flowerId)) {
                name = flower.getDescription();
                break;
            }
        }
        return name;
    }
    
    public String toString() {
        return String.format("|%-6s|%4s|%4d|%8.2f|", orderDetailId, flowerId, quantity, cost);
    }
}
