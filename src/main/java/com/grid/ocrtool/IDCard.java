package com.grid.ocrtool;

public class IDCard {
    private String name;
    private String birth;
    private double[] position;
    private String locate;
    private int id;
    private String location;
    private String line;

    public IDCard(String name, String birth, double[] position, String locate, int id, String location, String line) {
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.locate = locate;
        this.id = id;
        this.location = location;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
