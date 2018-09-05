package com.ncr.project.pulsecheck.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_from")
    private String from;

    @Column(name = "addresses")
    private String addresses;

    @Column(name = "subject")
    private String subject;

    @Column(name = "jhi_body")
    private String body;

    @Column(name = "is_html")
    private Boolean isHtml;

    @Column(name = "is_sent")
    private Boolean isSent;

    @Column(name = "date_insert")
    private Instant dateInsert;

    @Column(name = "date_sent")
    private Instant dateSent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public Email from(String from) {
        this.from = from;
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAddresses() {
        return addresses;
    }

    public Email addresses(String addresses) {
        this.addresses = addresses;
        return this;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getSubject() {
        return subject;
    }

    public Email subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public Email body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean isIsHtml() {
        return isHtml;
    }

    public Email isHtml(Boolean isHtml) {
        this.isHtml = isHtml;
        return this;
    }

    public void setIsHtml(Boolean isHtml) {
        this.isHtml = isHtml;
    }

    public Boolean isIsSent() {
        return isSent;
    }

    public Email isSent(Boolean isSent) {
        this.isSent = isSent;
        return this;
    }

    public void setIsSent(Boolean isSent) {
        this.isSent = isSent;
    }

    public Instant getDateInsert() {
        return dateInsert;
    }

    public Email dateInsert(Instant dateInsert) {
        this.dateInsert = dateInsert;
        return this;
    }

    public void setDateInsert(Instant dateInsert) {
        this.dateInsert = dateInsert;
    }

    public Instant getDateSent() {
        return dateSent;
    }

    public Email dateSent(Instant dateSent) {
        this.dateSent = dateSent;
        return this;
    }

    public void setDateSent(Instant dateSent) {
        this.dateSent = dateSent;
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
        Email email = (Email) o;
        if (email.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), email.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Email{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", addresses='" + getAddresses() + "'" +
            ", subject='" + getSubject() + "'" +
            ", body='" + getBody() + "'" +
            ", isHtml='" + isIsHtml() + "'" +
            ", isSent='" + isIsSent() + "'" +
            ", dateInsert='" + getDateInsert() + "'" +
            ", dateSent='" + getDateSent() + "'" +
            "}";
    }
}
