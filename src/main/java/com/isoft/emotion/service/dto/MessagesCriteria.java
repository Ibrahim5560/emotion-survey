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
 * Criteria class for the {@link com.isoft.emotion.domain.Messages} entity. This class is used
 * in {@link com.isoft.emotion.web.rest.MessagesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /messages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessagesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter counter;

    private LongFilter trsId;

    private LongFilter userId;

    private StringFilter message;

    private IntegerFilter status;

    private StringFilter applicantName;

    private LongFilter centerId;

    private LongFilter systemId;

    private LongFilter systemServicesId;

    private LongFilter usersId;

    public MessagesCriteria() {
    }

    public MessagesCriteria(MessagesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.counter = other.counter == null ? null : other.counter.copy();
        this.trsId = other.trsId == null ? null : other.trsId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.applicantName = other.applicantName == null ? null : other.applicantName.copy();
        this.centerId = other.centerId == null ? null : other.centerId.copy();
        this.systemId = other.systemId == null ? null : other.systemId.copy();
        this.systemServicesId = other.systemServicesId == null ? null : other.systemServicesId.copy();
        this.usersId = other.usersId == null ? null : other.usersId.copy();
    }

    @Override
    public MessagesCriteria copy() {
        return new MessagesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public StringFilter getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(StringFilter applicantName) {
        this.applicantName = applicantName;
    }

    public LongFilter getCenterId() {
        return centerId;
    }

    public void setCenterId(LongFilter centerId) {
        this.centerId = centerId;
    }

    public LongFilter getSystemId() {
        return systemId;
    }

    public void setSystemId(LongFilter systemId) {
        this.systemId = systemId;
    }

    public LongFilter getSystemServicesId() {
        return systemServicesId;
    }

    public void setSystemServicesId(LongFilter systemServicesId) {
        this.systemServicesId = systemServicesId;
    }

    public LongFilter getUsersId() {
        return usersId;
    }

    public void setUsersId(LongFilter usersId) {
        this.usersId = usersId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessagesCriteria that = (MessagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(counter, that.counter) &&
            Objects.equals(trsId, that.trsId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(message, that.message) &&
            Objects.equals(status, that.status) &&
            Objects.equals(applicantName, that.applicantName) &&
            Objects.equals(centerId, that.centerId) &&
            Objects.equals(systemId, that.systemId) &&
            Objects.equals(systemServicesId, that.systemServicesId) &&
            Objects.equals(usersId, that.usersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        counter,
        trsId,
        userId,
        message,
        status,
        applicantName,
        centerId,
        systemId,
        systemServicesId,
        usersId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (counter != null ? "counter=" + counter + ", " : "") +
                (trsId != null ? "trsId=" + trsId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (applicantName != null ? "applicantName=" + applicantName + ", " : "") +
                (centerId != null ? "centerId=" + centerId + ", " : "") +
                (systemId != null ? "systemId=" + systemId + ", " : "") +
                (systemServicesId != null ? "systemServicesId=" + systemServicesId + ", " : "") +
                (usersId != null ? "usersId=" + usersId + ", " : "") +
            "}";
    }

}
