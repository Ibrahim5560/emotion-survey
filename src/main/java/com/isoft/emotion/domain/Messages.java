package com.isoft.emotion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Messages (messages) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Messages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @Column(name = "applicant_name")
    private String applicantName;

    @ManyToOne
    @JsonIgnoreProperties(value = "centerMessages", allowSetters = true)
    private Center center;

    @ManyToOne
    @JsonIgnoreProperties(value = "systemMessages", allowSetters = true)
    private System system;

    @ManyToOne
    @JsonIgnoreProperties(value = "systemServicesMessages", allowSetters = true)
    private SystemServices systemServices;

    @ManyToOne
    @JsonIgnoreProperties(value = "usersMessages", allowSetters = true)
    private Users users;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCounter() {
        return counter;
    }

    public Messages counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getTrsId() {
        return trsId;
    }

    public Messages trsId(Long trsId) {
        this.trsId = trsId;
        return this;
    }

    public void setTrsId(Long trsId) {
        this.trsId = trsId;
    }

    public Long getUserId() {
        return userId;
    }

    public Messages userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public Messages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public Messages status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public Messages applicantName(String applicantName) {
        this.applicantName = applicantName;
        return this;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Center getCenter() {
        return center;
    }

    public Messages center(Center center) {
        this.center = center;
        return this;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public System getSystem() {
        return system;
    }

    public Messages system(System system) {
        this.system = system;
        return this;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public SystemServices getSystemServices() {
        return systemServices;
    }

    public Messages systemServices(SystemServices systemServices) {
        this.systemServices = systemServices;
        return this;
    }

    public void setSystemServices(SystemServices systemServices) {
        this.systemServices = systemServices;
    }

    public Users getUsers() {
        return users;
    }

    public Messages users(Users users) {
        this.users = users;
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Messages)) {
            return false;
        }
        return id != null && id.equals(((Messages) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Messages{" +
            "id=" + getId() +
            ", counter=" + getCounter() +
            ", trsId=" + getTrsId() +
            ", userId=" + getUserId() +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", applicantName='" + getApplicantName() + "'" +
            "}";
    }
}
