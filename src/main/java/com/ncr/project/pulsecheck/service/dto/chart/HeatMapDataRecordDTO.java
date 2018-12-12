package com.ncr.project.pulsecheck.service.dto.chart;

import java.io.Serializable;
import java.util.List;

public class HeatMapDataRecordDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String groupLabel;
    private String label;
    private Float importanceGap;
    private Float performanceGap;
    private Float otherGap;
    private Boolean showSubItems;
    private List<HeatMapDataRecordDTO> subItems;

    /**
     * @return the groupLabel
     */
    public String getGroupLabel() {
        return groupLabel;
    }

    /**
     * @return the subItems
     */
    public List<HeatMapDataRecordDTO> getSubItems() {
        return subItems;
    }

    /**
     * @param subItems the subItems to set
     */
    public void setSubItems(List<HeatMapDataRecordDTO> subItems) {
        this.subItems = subItems;
    }

    /**
     * @return the showSubItems
     */
    public Boolean getShowSubItems() {
        return showSubItems;
    }

    /**
     * @param showSubItems the showSubItems to set
     */
    public void setShowSubItems(Boolean showSubItems) {
        this.showSubItems = showSubItems;
    }

    /**
     * @return the otherGap
     */
    public Float getOtherGap() {
        return otherGap;
    }

    /**
     * @param otherGap the otherGap to set
     */
    public void setOtherGap(Float otherGap) {
        this.otherGap = otherGap;
    }

    /**
     * @return the performanceGap
     */
    public Float getPerformanceGap() {
        return performanceGap;
    }

    /**
     * @param performanceGap the performanceGap to set
     */
    public void setPerformanceGap(Float performanceGap) {
        this.performanceGap = performanceGap;
    }

    /**
     * @return the importanceGap
     */
    public Float getImportanceGap() {
        return importanceGap;
    }

    /**
     * @param importanceGap the importanceGap to set
     */
    public void setImportanceGap(Float importanceGap) {
        this.importanceGap = importanceGap;
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
     * @param groupLabel the groupLabel to set
     */
    public void setGroupLabel(String groupLabel) {
        this.groupLabel = groupLabel;
    }

}