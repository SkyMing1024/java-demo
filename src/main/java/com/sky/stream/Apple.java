package com.sky.stream;

public class Apple {
    private int id;
    private String color;
    private int weight;
    private String place;

    public Apple() {
    }

    public Apple(int id, String color, int weight, String place) {
        this.id = id;
        this.color = color;
        this.weight = weight;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", weight=" + weight +
                ", place='" + place + '\'' +
                '}';
    }
}
