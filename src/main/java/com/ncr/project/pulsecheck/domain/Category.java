package com.ncr.project.pulsecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "jhi_label", length = 255, nullable = false)
    private String label;

    @ManyToOne
    @JsonIgnoreProperties("sons")
    private Category father;

    @ManyToOne
    @JsonIgnoreProperties("categories")
    private CategoryLevel level;

    @OneToMany(mappedBy = "father")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> sons = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
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

    public Category label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Category getFather() {
        return father;
    }

    public Category father(Category category) {
        this.father = category;
        return this;
    }

    public void setFather(Category category) {
        this.father = category;
    }

    public CategoryLevel getLevel() {
        return level;
    }

    public Category level(CategoryLevel categoryLevel) {
        this.level = categoryLevel;
        return this;
    }

    public void setLevel(CategoryLevel categoryLevel) {
        this.level = categoryLevel;
    }

    public Set<Category> getSons() {
        return sons;
    }

    public Category sons(Set<Category> categories) {
        this.sons = categories;
        return this;
    }

    public Category addSons(Category category) {
        this.sons.add(category);
        category.setFather(this);
        return this;
    }

    public Category removeSons(Category category) {
        this.sons.remove(category);
        category.setFather(null);
        return this;
    }

    public void setSons(Set<Category> categories) {
        this.sons = categories;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Category questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Category addQuestions(Question question) {
        this.questions.add(question);
        question.getCategories().add(this);
        return this;
    }

    public Category removeQuestions(Question question) {
        this.questions.remove(question);
        question.getCategories().remove(this);
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
