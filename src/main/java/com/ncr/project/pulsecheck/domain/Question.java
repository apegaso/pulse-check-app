package com.ncr.project.pulsecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "question", length = 2048, nullable = false)
    private String question;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @Size(min = 1, max = 2048)
    @Column(name = "sub_question", length = 2048)
    private String subQuestion;

    @Column(name = "importance_score_active")
    private Boolean importanceScoreActive;

    @Column(name = "performance_score_active")
    private Boolean performanceScoreActive;

    @Column(name = "show_question")
    private Boolean showQuestion;

    @ManyToOne
    @JsonIgnoreProperties("")
    private QuestionGroup group;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "question_category",
               joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public Question question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getOrder() {
        return order;
    }

    public Question order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSubQuestion() {
        return subQuestion;
    }

    public Question subQuestion(String subQuestion) {
        this.subQuestion = subQuestion;
        return this;
    }

    public void setSubQuestion(String subQuestion) {
        this.subQuestion = subQuestion;
    }

    public Boolean isImportanceScoreActive() {
        return importanceScoreActive;
    }

    public Question importanceScoreActive(Boolean importanceScoreActive) {
        this.importanceScoreActive = importanceScoreActive;
        return this;
    }

    public void setImportanceScoreActive(Boolean importanceScoreActive) {
        this.importanceScoreActive = importanceScoreActive;
    }

    public Boolean isPerformanceScoreActive() {
        return performanceScoreActive;
    }

    public Question performanceScoreActive(Boolean performanceScoreActive) {
        this.performanceScoreActive = performanceScoreActive;
        return this;
    }

    public void setPerformanceScoreActive(Boolean performanceScoreActive) {
        this.performanceScoreActive = performanceScoreActive;
    }

    public Boolean isShowQuestion() {
        return showQuestion;
    }

    public Question showQuestion(Boolean showQuestion) {
        this.showQuestion = showQuestion;
        return this;
    }

    public void setShowQuestion(Boolean showQuestion) {
        this.showQuestion = showQuestion;
    }

    public QuestionGroup getGroup() {
        return group;
    }

    public Question group(QuestionGroup questionGroup) {
        this.group = questionGroup;
        return this;
    }

    public void setGroup(QuestionGroup questionGroup) {
        this.group = questionGroup;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Question categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Question addCategory(Category category) {
        this.categories.add(category);
        category.getQuestions().add(this);
        return this;
    }

    public Question removeCategory(Category category) {
        this.categories.remove(category);
        category.getQuestions().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
        Question question = (Question) o;
        if (question.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", order=" + getOrder() +
            ", subQuestion='" + getSubQuestion() + "'" +
            ", importanceScoreActive='" + isImportanceScoreActive() + "'" +
            ", performanceScoreActive='" + isPerformanceScoreActive() + "'" +
            ", showQuestion='" + isShowQuestion() + "'" +
            "}";
    }
}
