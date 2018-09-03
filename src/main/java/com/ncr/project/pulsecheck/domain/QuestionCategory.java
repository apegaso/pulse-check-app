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

    @OneToMany(mappedBy = "father")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionCategory> soons = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("soons")
    private QuestionCategory father;

    @ManyToOne
    @JsonIgnoreProperties("soons")
    private QuestionCategory father;

    @OneToMany(mappedBy = "father")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionCategory> soons = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

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

    public Set<QuestionCategory> getSoons() {
        return soons;
    }

    public QuestionCategory soons(Set<QuestionCategory> questionCategories) {
        this.soons = questionCategories;
        return this;
    }

    public QuestionCategory addSoons(QuestionCategory questionCategory) {
        this.soons.add(questionCategory);
        questionCategory.setFather(this);
        return this;
    }

    public QuestionCategory removeSoons(QuestionCategory questionCategory) {
        this.soons.remove(questionCategory);
        questionCategory.setFather(null);
        return this;
    }

    public void setSoons(Set<QuestionCategory> questionCategories) {
        this.soons = questionCategories;
    }

    public QuestionCategory getFather() {
        return father;
    }

    public QuestionCategory father(QuestionCategory questionCategory) {
        this.father = questionCategory;
        return this;
    }

    public void setFather(QuestionCategory questionCategory) {
        this.father = questionCategory;
    }

    public QuestionCategory getFather() {
        return father;
    }

    public QuestionCategory father(QuestionCategory questionCategory) {
        this.father = questionCategory;
        return this;
    }

    public void setFather(QuestionCategory questionCategory) {
        this.father = questionCategory;
    }

    public Set<QuestionCategory> getSoons() {
        return soons;
    }

    public QuestionCategory soons(Set<QuestionCategory> questionCategories) {
        this.soons = questionCategories;
        return this;
    }

    public QuestionCategory addSoons(QuestionCategory questionCategory) {
        this.soons.add(questionCategory);
        questionCategory.setFather(this);
        return this;
    }

    public QuestionCategory removeSoons(QuestionCategory questionCategory) {
        this.soons.remove(questionCategory);
        questionCategory.setFather(null);
        return this;
    }

    public void setSoons(Set<QuestionCategory> questionCategories) {
        this.soons = questionCategories;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public QuestionCategory questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public QuestionCategory addQuestions(Question question) {
        this.questions.add(question);
        question.setCategory(this);
        return this;
    }

    public QuestionCategory removeQuestions(Question question) {
        this.questions.remove(question);
        question.setCategory(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
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
