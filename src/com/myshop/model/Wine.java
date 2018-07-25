package com.myshop.model;

import java.io.Serializable;

public class Wine implements Serializable{
    private int id;
    private String name;
    private String kind;
    private double price;
    private String detail;
    private String picture;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Override
    public String toString() {
        return "Wine [id=" + id + ", name=" + name + ", kind=" + kind
                + ", price=" + price + ", detail=" + detail + ", picture="
                + picture + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Wine other = (Wine) obj;
        if (id != other.id)
            return false;
        return true;
    }
    

}
