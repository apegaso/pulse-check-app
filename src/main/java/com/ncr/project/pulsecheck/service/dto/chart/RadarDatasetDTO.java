package com.ncr.project.pulsecheck.service.dto.chart;

import java.io.Serializable;
import java.util.List;

public class RadarDatasetDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<String> labels;

    private List<RadarDataRecordDTO> datasets;

    /**
     * @return the labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * @return the datasets
     */
    public List<RadarDataRecordDTO> getDatasets() {
        return datasets;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<RadarDataRecordDTO> datasets) {
        this.datasets = datasets;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

}