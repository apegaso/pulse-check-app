package com.ncr.project.pulsecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A QuestionnaireAnswer.
 */
@Entity
@Table(name = "questionnaire_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionnaireAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "importance")
    private Double importance;

    @Column(name = "performance")
    private Double performance;

    @Size(max = 2048)
    @Column(name = "note", length = 2048)
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Questionnaire questionnaire;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getImportance() {
        return importance;
    }

    public QuestionnaireAnswer importance(Double importance) {
        this.importance = importance;
        return this;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }

    public Double getPerformance() {
        return performance;
    }

    public QuestionnaireAnswer performance(Double performance) {
        this.performance = performance;
        return this;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    public String getNote() {
        return note;
    }

    public QuestionnaireAnswer note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public QuestionnaireAnswer questionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Question getQuestion() {
        return question;
    }

    public QuestionnaireAnswer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionnaireAnswer questionnaireAnswer = (QuestionnaireAnswer) o;
        if (questionnaireAnswer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionnaireAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionnaireAnswer{" +
            "id=" + getId() +
            ", importance=" + getImportance() +
            ", performance=" + getPerformance() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
