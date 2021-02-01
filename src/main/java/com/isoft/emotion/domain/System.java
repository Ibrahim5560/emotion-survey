package com.isoft.emotion.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * System (system) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class System implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "system")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Messages> systemMessages = new HashSet<>();

    @OneToMany(mappedBy = "system")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SystemServices> systemServices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public System nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public System nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public System code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public System status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Messages> getSystemMessages() {
        return systemMessages;
    }

    public System systemMessages(Set<Messages> messages) {
        this.systemMessages = messages;
        return this;
    }

    public System addSystemMessages(Messages messages) {
        this.systemMessages.add(messages);
        messages.setSystem(this);
        return this;
    }

    public System removeSystemMessages(Messages messages) {
        this.systemMessages.remove(messages);
        messages.setSystem(null);
        return this;
    }

    public void setSystemMessages(Set<Messages> messages) {
        this.systemMessages = messages;
    }

    public Set<SystemServices> getSystemServices() {
        return systemServices;
    }

    public System systemServices(Set<SystemServices> systemServices) {
        this.systemServices = systemServices;
        return this;
    }

    public System addSystemServices(SystemServices systemServices) {
        this.systemServices.add(systemServices);
        systemServices.setSystem(this);
        return this;
    }

    public System removeSystemServices(SystemServices systemServices) {
        this.systemServices.remove(systemServices);
        systemServices.setSystem(null);
        return this;
    }

    public void setSystemServices(Set<SystemServices> systemServices) {
        this.systemServices = systemServices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof System)) {
            return false;
        }
        return id != null && id.equals(((System) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "System{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
