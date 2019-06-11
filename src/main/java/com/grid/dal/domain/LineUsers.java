package com.grid.dal.domain;

import java.math.BigDecimal;

public class LineUsers extends BaseDO {
    private Short id;

    private Short inspector;

    private String insname;

    private String line;

    private String linename;

    private BigDecimal distance;

    private Short inside;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getInspector() {
        return inspector;
    }

    public void setInspector(Short inspector) {
        this.inspector = inspector;
    }

    public String getInsname() {
        return insname;
    }

    public void setInsname(String insname) {
        this.insname = insname == null ? null : insname.trim();
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line == null ? null : line.trim();
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename == null ? null : linename.trim();
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Short getInside() {
        return inside;
    }

    public void setInside(Short inside) {
        this.inside = inside;
    }
}