package com.example.lastestcar01.pojo;

public class Car {
    private String brand;
    private String model;
    private int mileage;
    private double price;
    private String time;

    public Car(String brand, String model, int mileage, double price) {
        this.brand = brand;
        this.model = model;
        this.mileage = mileage;
        this.price = price;
        this.time =  time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
