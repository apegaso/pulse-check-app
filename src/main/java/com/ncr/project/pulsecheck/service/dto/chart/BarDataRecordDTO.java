package com.ncr.project.pulsecheck.service.dto.chart;

import java.io.Serializable;
import java.util.List;

public class BarDataRecordDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Boolean fill;
    private String label; 
    private String backgroundColor;
    private String borderColor;
    private Float borderWidth;
    private Float pointRadius;
    private List<Float> data;

    /**
     * @return the fill
     */
    public Boolean getFill() {
        return fill;
    }

    /**
     * @return the pointRadius
     */
    public Float getPointRadius() {
        return pointRadius;
    }

    /**
     * @param pointRadius the pointRadius to set
     */
    public void setPointRadius(Float pointRadius) {
        this.pointRadius = pointRadius;
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
     * @return the data
     */
    public List<Float> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<Float> data) {
        this.data = data;
    }

    /**
     * @return the borderWidth
     */
    public Float getBorderWidth() {
        return borderWidth;
    }

    /**
     * @param borderWidth the borderWidth to set
     */
    public void setBorderWidth(Float borderWidth) {
        this.borderWidth = borderWidth;
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
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @param fill the fill to set
     */
    public void setFill(Boolean fill) {
        this.fill = fill;
    }

}