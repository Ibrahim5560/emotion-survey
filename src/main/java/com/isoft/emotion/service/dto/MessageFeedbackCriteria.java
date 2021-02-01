package com.isoft.emotion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.isoft.emotion.domain.MessageFeedback} entity. This class is used
 * in {@link com.isoft.emotion.web.rest.MessageFeedbackResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /message-feedbacks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageFeedbackCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter systemId;

    private LongFilter centerId;

    private LongFilter systemServicesId;

    private LongFilter counter;

    private LongFilter trsId;

    private LongFilter userId;

    private StringFilter message;

    private IntegerFilter status;

    private StringFilter feedback;

    private StringFilter applicantName;

    public MessageFeedbackCriteria() {
    }

    public MessageFeedbackCriteria(MessageFeedbackCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.systemId = other.systemId == null ? null : other.systemId.copy();
        this.centerId = other.centerId == null ? null : other.centerId.copy();
        this.systemServicesId = other.systemServicesId == null ? null : other.systemServicesId.copy();
        this.counter = other.counter == null ? null : other.counter.copy();
        this.trsId = other.trsId == null ? null : other.trsId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.feedback = other.feedback == null ? null : other.feedback.copy();
        this.applicantName = other.applicantName == null ? null : other.applicantName.copy();
    }

    @Override
    public MessageFeedbackCriteria copy() {
        return new MessageFeedbackCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSystemId() {
        return systemId;
    }

    public void setSystemId(LongFilter systemId) {
        this.systemId = systemId;
    }

    public LongFilter getCenterId() {
        return centerId;
    }

    public void setCenterId(LongFilter centerId) {
        this.centerId = centerId;
    }

    public LongFilter getSystemServicesId() {
        return systemServicesId;
    }

    public void setSystemServicesId(LongFilter systemServicesId) {
        this.systemServicesId = systemServicesId;
    }

    public LongFilter getCounter() {
        return counter;
    }

    public void setCounter(LongFilter counter) {
        this.counter = counter;
    }

    public LongFilter getTrsId() {
        return trsId;
    }

    public void setTrsId(LongFilter trsId) {
        this.trsId = trsId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public StringFilter getFeedback() {
        return feedback;
    }

    public void setFeedback(StringFilter feedback) {
        this.feedback = feedback;
    }

    public StringFilter getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(StringFilter applicantName) {
        this.applicantName = applicantName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageFeedbackCriteria that = (MessageFeedbackCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(systemId, that.systemId) &&
            Objects.equals(centerId, that.centerId) &&
            Objects.equals(systemServicesId, that.systemServicesId) &&
            Objects.equals(counter, that.counter) &&
            Objects.equals(trsId, that.trsId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(message, that.message) &&
            Objects.equals(status, that.status) &&
            Objects.equals(feedback, that.feedback) &&
            Objects.equals(applicantName, that.applicantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        systemId,
        centerId,
        systemServicesId,
        counter,
        trsId,
        userId,
        message,
        status,
        feedback,
        applicantName
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageFeedbackCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (systemId != null ? "systemId=" + systemId + ", " : "") +
                (centerId != null ? "centerId=" + centerId + ", " : "") +
                (systemServicesId != null ? "systemServicesId=" + systemServicesId + ", " : "") +
                (counter != null ? "counter=" + counter + ", " : "") +
                (trsId != null ? "trsId=" + trsId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (feedback != null ? "feedback=" + feedback + ", " : "") +
                (applicantName != null ? "applicantName=" + applicantName + ", " : "") +
            "}";
    }

}
