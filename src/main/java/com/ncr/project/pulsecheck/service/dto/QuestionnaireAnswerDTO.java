package com.ncr.project.pulsecheck.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the QuestionnaireAnswer entity.
 */
public class QuestionnaireAnswerDTO implements Serializable {

    private Long id;

    private Double importance;

    private Double performance;

    private Long questionaireId;

    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getImportance() {
        return importance;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }

    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    public Long getQuestionaireId() {
        return questionaireId;
    }

    public void setQuestionaireId(Long questionnaireId) {
        this.questionaireId = questionnaireId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionnaireAnswerDTO questionnaireAnswerDTO = (QuestionnaireAnswerDTO) o;
        if (questionnaireAnswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionnaireAnswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionnaireAnswerDTO{" +
            "id=" + getId() +
            ", importance=" + getImportance() +
            ", performance=" + getPerformance() +
            ", questionaire=" + getQuestionaireId() +
            ", question=" + getQuestionId() +
            "}";
    }
}
