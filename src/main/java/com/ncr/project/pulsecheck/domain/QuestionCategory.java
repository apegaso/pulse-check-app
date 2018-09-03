package com.ncr.project.pulsecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A QuestionCategory.
 */
@Entity
@Table(name = "question_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "jhi_level")
    private Integer level;

    @ManyToOne
    @JsonIgnoreProperties("fathers")
    private QuestionCategory soons;

    @ManyToOne
    @JsonIgnoreProperties("categories")
    private Question questions;

    @OneToMany(mappedBy = "soons")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionCategory> fathers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public QuestionCategory label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getLevel() {
        return level;
    }

    public QuestionCategory level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public QuestionCategory getSoons() {
        return soons;
    }

    public QuestionCategory soons(QuestionCategory questionCategory) {
        this.soons = questionCategory;
        return this;
    }

    public void setSoons(QuestionCategory questionCategory) {
        this.soons = questionCategory;
    }

    public Question getQuestions() {
        return questions;
    }

    public QuestionCategory questions(Question question) {
        this.questions = question;
        return this;
    }

    public void setQuestions(Question question) {
        this.questions = question;
    }

    public Set<QuestionCategory> getFathers() {
        return fathers;
    }

    public QuestionCategory fathers(Set<QuestionCategory> questionCategories) {
        this.fathers = questionCategories;
        return this;
    }

    public QuestionCategory addFather(QuestionCategory questionCategory) {
        this.fathers.add(questionCategory);
        questionCategory.setSoons(this);
        return this;
    }

    public QuestionCategory removeFather(QuestionCategory questionCategory) {
        this.fathers.remove(questionCategory);
        questionCategory.setSoons(null);
        return this;
    }

    public void setFathers(Set<QuestionCategory> questionCategories) {
        this.fathers = questionCategories;
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
        QuestionCategory questionCategory = (QuestionCategory) o;
        if (questionCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionCategory{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", level=" + getLevel() +
            "}";
    }
}
