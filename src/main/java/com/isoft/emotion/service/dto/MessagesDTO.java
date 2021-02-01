package com.isoft.emotion.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.isoft.emotion.domain.Messages} entity.
 */
@ApiModel(description = "Messages (messages) entity.\n@author Ibrahim Hassanin.")
public class MessagesDTO implements Serializable {
    
    private Long id;

    private Long counter;

    private Long trsId;

    private Long userId;

    private String message;

    private Integer status;

    private String applicantName;


    private Long centerId;

    private Long systemId;

    private Long systemServicesId;

    private Long usersId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Long getSystemServicesId() {
        return systemServicesId;
    }

    public void setSystemServicesId(Long systemServicesId) {
        this.systemServicesId = systemServicesId;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessagesDTO)) {
            return false;
        }

        return id != null && id.equals(((MessagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessagesDTO{" +
            "id=" + getId() +
            ", counter=" + getCounter() +
            ", trsId=" + getTrsId() +
            ", userId=" + getUserId() +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", applicantName='" + getApplicantName() + "'" +
            ", centerId=" + getCenterId() +
            ", systemId=" + getSystemId() +
            ", systemServicesId=" + getSystemServicesId() +
            ", usersId=" + getUsersId() +
            "}";
    }
}
