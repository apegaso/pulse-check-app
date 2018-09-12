package com.ncr.project.pulsecheck.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Questionnaire entity.
 */
public class QuestionnaireDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Instant dateStart;

    private Instant dateEnd;

    private Long eventId;

    private Long participantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateStart() {
        return dateStart;
    }

    public void setDateStart(Instant dateStart) {
        this.dateStart = dateStart;
    }

    public Instant getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Instant dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionnaireDTO questionnaireDTO = (QuestionnaireDTO) o;
        if (questionnaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionnaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionnaireDTO{" +
            "id=" + getId() +
            ", dateStart='" + getDateStart() + "'" +
            ", dateEnd='" + getDateEnd() + "'" +
            ", event=" + getEventId() +
            ", participant=" + getParticipantId() +
            "}";
    }
}
