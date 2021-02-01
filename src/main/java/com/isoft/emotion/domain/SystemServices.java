package com.isoft.emotion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * SystemServices (system_services) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "system_services")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemServices implements Serializable {

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

    @OneToMany(mappedBy = "systemServices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Messages> systemServicesMessages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "systemServices", allowSetters = true)
    private System system;

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

    public SystemServices nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public SystemServices nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public SystemServices code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public SystemServices status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Messages> getSystemServicesMessages() {
        return systemServicesMessages;
    }

    public SystemServices systemServicesMessages(Set<Messages> messages) {
        this.systemServicesMessages = messages;
        return this;
    }

    public SystemServices addSystemServicesMessages(Messages messages) {
        this.systemServicesMessages.add(messages);
        messages.setSystemServices(this);
        return this;
    }

    public SystemServices removeSystemServicesMessages(Messages messages) {
        this.systemServicesMessages.remove(messages);
        messages.setSystemServices(null);
        return this;
    }

    public void setSystemServicesMessages(Set<Messages> messages) {
        this.systemServicesMessages = messages;
    }

    public System getSystem() {
        return system;
    }

    public SystemServices system(System system) {
        this.system = system;
        return this;
    }

    public void setSystem(System system) {
        this.system = system;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemServices)) {
            return false;
        }
        return id != null && id.equals(((SystemServices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemServices{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
