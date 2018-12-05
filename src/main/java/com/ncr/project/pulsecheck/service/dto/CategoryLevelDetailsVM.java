package com.ncr.project.pulsecheck.service.dto;

import java.io.Serializable;
import java.util.List;

public class CategoryLevelDetailsVM extends CategoryLevelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CategoryDTO> categories;

    /**
     * @return the categories
     */
    public List<CategoryDTO> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

}