package com.ncr.project.pulsecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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

    @ManyToOne
    @JsonIgnoreProperties("")
    private Questionnaire questionaire;

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

    public Questionnaire getQuestionaire() {
        return questionaire;
    }

    public QuestionnaireAnswer questionaire(Questionnaire questionnaire) {
        this.questionaire = questionnaire;
        return this;
    }

    public void setQuestionaire(Questionnaire questionnaire) {
        this.questionaire = questionnaire;
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
            "}";
    }
}
