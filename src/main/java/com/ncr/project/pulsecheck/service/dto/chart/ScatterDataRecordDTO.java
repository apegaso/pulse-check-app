package com.ncr.project.pulsecheck.service.dto.chart;

import java.io.Serializable;
import java.util.List;

public class ScatterDataRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String label;
    private Float radius;
    private String borderColor;
    private String backgroundColor;
    private List<ScatterDataPointDTO> data;

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the data
     */
    public List<ScatterDataPointDTO> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<ScatterDataPointDTO> data) {
        this.data = data;
    }

    /**
     * @return the backgroundColor
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the borderColor
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * @return the radius
     */
    public Float getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(Float radius) {
        this.radius = radius;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
}