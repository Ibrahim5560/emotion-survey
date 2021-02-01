package com.isoft.emotion.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.isoft.emotion.domain.MessageFeedback} entity.
 */
@ApiModel(description = "MessageFeedback (message_feedback) entity.\n@author Ibrahim Hassanin.")
public class MessageFeedbackDTO implements Serializable {
    
    private Long id;

    private Long systemId;

    private Long centerId;

    private Long systemServicesId;

    private Long counter;

    private Long trsId;

    private Long userId;

    private String message;

    private Integer status;

    private String feedback;

    private String applicantName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getSystemServicesId() {
        return systemServicesId;
    }

    public void setSystemServicesId(Long systemServicesId) {
        this.systemServicesId = systemServicesId;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getTrsId() {
        return trsId;
    }

    public void setTrsId(Long trsId) {
        this.trsId = trsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageFeedbackDTO)) {
            return false;
        }

        return id != null && id.equals(((MessageFeedbackDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageFeedbackDTO{" +
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
