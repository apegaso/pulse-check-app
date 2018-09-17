package com.ncr.project.pulsecheck.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Question entity.
 */
public class QuestionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 1, max = 2048)
    private String question;

    @NotNull
    private Integer order;

    @Size(min = 1, max = 2048)
    private String subQuestion;

    private Boolean importanceScoreActive;

    private Boolean performanceScoreActive;

    private Boolean showQuestion;

    private Long groupId;

    private Set<CategoryDTO> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(String subQuestion) {
        this.subQuestion = subQuestion;
    }

    public Boolean isImportanceScoreActive() {
        return importanceScoreActive;
    }

    public void setImportanceScoreActive(Boolean importanceScoreActive) {
        this.importanceScoreActive = importanceScoreActive;
    }

    public Boolean isPerformanceScoreActive() {
        return performanceScoreActive;
    }

    public void setPerformanceScoreActive(Boolean performanceScoreActive) {
        this.performanceScoreActive = performanceScoreActive;
    }

    public Boolean isShowQuestion() {
        return showQuestion;
    }

    public void setShowQuestion(Boolean showQuestion) {
        this.showQuestion = showQuestion;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long questionGroupId) {
        this.groupId = questionGroupId;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionDTO questionDTO = (QuestionDTO) o;
        if (questionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", order=" + getOrder() +
            ", subQuestion='" + getSubQuestion() + "'" +
            ", importanceScoreActive='" + isImportanceScoreActive() + "'" +
            ", performanceScoreActive='" + isPerformanceScoreActive() + "'" +
            ", showQuestion='" + isShowQuestion() + "'" +
            ", group=" + getGroupId() +
            "}";
    }
}
