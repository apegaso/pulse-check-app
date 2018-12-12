package com.ncr.project.pulsecheck.service.dto.chart;

import java.io.Serializable;
import java.util.List;

public class ScatterDatasetDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ScatterDataRecordDTO> datasets;

    /**
     * @return the datasets
     */
    public List<ScatterDataRecordDTO> getDatasets() {
        return datasets;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<ScatterDataRecordDTO> datasets) {
        this.datasets = datasets;
    }

}
