package com.isoft.emotion.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * MessageFeedback (message_feedback) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "message_feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MessageFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "system_id")
    private Long systemId;

    @Column(name = "center_id")
    private Long centerId;

    @Column(name = "system_services_id")
    private Long systemServicesId;

    @Column(name = "counter")
    private Long counter;

    @Column(name = "trs_id")
    private Long trsId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private Integer status;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "applicant_name")
    private String applicantName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSystemId() {
        return systemId;
    }

    public MessageFeedback systemId(Long systemId) {
        this.systemId = systemId;
        return this;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public MessageFeedback centerId(Long centerId) {
        this.centerId = centerId;
        return this;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getSystemServicesId() {
        return systemServicesId;
    }

    public MessageFeedback systemServicesId(Long systemServicesId) {
        this.systemServicesId = systemServicesId;
        return this;
    }

    public void setSystemServicesId(Long systemServicesId) {
        this.systemServicesId = systemServicesId;
    }

    public Long getCounter() {
        return counter;
    }

    public MessageFeedback counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getTrsId() {
        return trsId;
    }

    public MessageFeedback trsId(Long trsId) {
        this.trsId = trsId;
        return this;
    }

    public void setTrsId(Long trsId) {
        this.trsId = trsId;
    }

    public Long getUserId() {
        return userId;
    }

    public MessageFeedback userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public MessageFeedback message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public MessageFeedback status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public MessageFeedback feedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public MessageFeedback applicantName(String applicantName) {
        this.applicantName = applicantName;
        return this;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageFeedback)) {
            return false;
        }
        return id != null && id.equals(((MessageFeedback) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageFeedback{" +
            "id=" + getId() +
            ", systemId=" + getSystemId() +
            ", centerId=" + getCenterId() +
            ", systemServicesId=" + getSystemServicesId() +
            ", counter=" + getCounter() +
            ", trsId=" + getTrsId() +
            ", userId=" + getUserId() +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", feedback='" + getFeedback() + "'" +
            ", applicantName='" + getApplicantName() + "'" +
            "}";
    }
}
