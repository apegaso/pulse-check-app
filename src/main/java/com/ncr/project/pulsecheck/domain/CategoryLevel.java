package com.ncr.project.pulsecheck.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CategoryLevel.
 */
@Entity
@Table(name = "category_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoryLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "jhi_label", length = 255, nullable = false)
    private String label;

    @OneToMany(mappedBy = "level")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> categories = new HashSet<>();

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

    public CategoryLevel label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public CategoryLevel categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public CategoryLevel addCategories(Category category) {
        this.categories.add(category);
        category.setLevel(this);
        return this;
    }

    public CategoryLevel removeCategories(Category category) {
        this.categories.remove(category);
        category.setLevel(null);
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
        CategoryLevel categoryLevel = (CategoryLevel) o;
        if (categoryLevel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryLevel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryLevel{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
