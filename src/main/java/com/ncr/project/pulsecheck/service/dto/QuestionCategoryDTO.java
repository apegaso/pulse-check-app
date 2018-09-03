package com.ncr.project.pulsecheck.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the QuestionCategory entity.
 */
public class QuestionCategoryDTO implements Serializable {

    private Long id;

    private String label;

    private Integer level;

    private Long soonsId;

    private Long questionsId;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getSoonsId() {
        return soonsId;
    }

    public void setSoonsId(Long questionCategoryId) {
        this.soonsId = questionCategoryId;
    }

    public Long getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(Long questionId) {
        this.questionsId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionCategoryDTO questionCategoryDTO = (QuestionCategoryDTO) o;
        if (questionCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionCategoryDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", level=" + getLevel() +
            ", soons=" + getSoonsId() +
            ", questions=" + getQuestionsId() +
            "}";
    }
}
