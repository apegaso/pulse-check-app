package com.ncr.project.pulsecheck.service.dto.chart;

import java.io.Serializable;

public class ScatterDataPointDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private Float x;
    private Float y;

    /**
     * @return the x
     */
    public Float getX() {
        return x;
    }

    /**
     * @return the y
     */
    public Float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(Float y) {
        this.y = y;
    }

    /**
     * @param x the x to set
     */
    public void setX(Float x) {
        this.x = x;
    }
}