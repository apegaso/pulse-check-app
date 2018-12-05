package com.ncr.project.pulsecheck.service.dto;

import java.io.Serializable;

public class QuestionGroupCountVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long count;

    /**
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Long count) {
        this.count = count;
    }

    
}