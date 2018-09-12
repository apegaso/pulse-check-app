package com.ncr.project.pulsecheck.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Category entity.
 */
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String label;

    private Long fatherId;

    private String fatherLabel;

    private Long levelId;

    private String levelLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long categoryId) {
        this.fatherId = categoryId;
    }

    public String getFatherLabel() {
        return fatherLabel;
    }

    public void setFatherLabel(String categoryLabel) {
        this.fatherLabel = categoryLabel;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long categoryLevelId) {
        this.levelId = categoryLevelId;
    }

    public String getLevelLabel() {
        return levelLabel;
    }

    public void setLevelLabel(String categoryLevelLabel) {
        this.levelLabel = categoryLevelLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (categoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", father=" + getFatherId() +
            ", father='" + getFatherLabel() + "'" +
            ", level=" + getLevelId() +
            ", level='" + getLevelLabel() + "'" +
            "}";
    }
}
