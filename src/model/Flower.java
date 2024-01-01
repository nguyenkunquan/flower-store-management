package model;

import java.io.Serializable;

public class Flower implements Serializable{

    String id, description, importDate, category;
    double unitPrice;
    int no;

    public Flower(int no, String id, String description, String importDate, double unitPrice, String category) {
        this.no = no;
        this.id = id;
        this.description = description;
        this.importDate = importDate;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return String.format("|%-5d|%5s|%-15s|%-12s|%10.2f|%15s|", no, id, description, importDate, unitPrice, category);
    }
}
