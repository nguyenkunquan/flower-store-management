
package model;

import java.io.Serializable;

public class OrderHeader implements Serializable{
    String orderId, orderDate, customerName;
    
    public OrderHeader(String orderId, String orderDate, String customerName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    @Override
    public String toString() {
        return String.format("|%-10s|%-15s|%-13s|", orderId, orderDate, customerName);
    }
}
