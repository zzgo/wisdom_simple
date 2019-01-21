package com.zachary.optimisticlock.entity;
//G:/software/maven/apache-maven-3.3.9-bin/repos/mysql/mysql-connector-java/5.1.18/mysql-connector-java-5.1.18.jar
public class GoodsInfo {
    private String id;//id
    private String name;//商品名称
    private int number;//数量
    private double price;//价格
    private int version;//版本号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
