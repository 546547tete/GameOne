package com.example.bean;

public class NumberBean {

    private int radius;  // 半径
    private int place_x;  // X-坐标
    private int place_y;  // Y-坐标
    private int number;  // 具体数字
    private String colour;  // 颜色
    private int shape;  // 形状

    public NumberBean(int radius, int place_x, int place_y, int number, String colour, int shape) {
        this.radius = radius;
        this.place_x = place_x;
        this.place_y = place_y;
        this.number = number;
        this.colour = colour;
        this.shape = shape;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getPlace_x() {
        return place_x;
    }

    public void setPlace_x(int place_x) {
        this.place_x = place_x;
    }

    public int getPlace_y() {
        return place_y;
    }

    public void setPlace_y(int place_y) {
        this.place_y = place_y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "NumberBean{" +
                "radius=" + radius +
                ", place_x=" + place_x +
                ", place_y=" + place_y +
                ", number=" + number +
                ", colour=" + colour +
                ", shape=" + shape +
                '}';
    }
}
