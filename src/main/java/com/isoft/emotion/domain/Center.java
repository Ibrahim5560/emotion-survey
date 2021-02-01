package com.isoft.emotion.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Center (center) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "center")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Center implements Serializable {

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

    @OneToMany(mappedBy = "center")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Messages> centerMessages = new HashSet<>();

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

    public Center nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Center nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public Center code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public Center status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Messages> getCenterMessages() {
        return centerMessages;
    }

    public Center centerMessages(Set<Messages> messages) {
        this.centerMessages = messages;
        return this;
    }

    public Center addCenterMessages(Messages messages) {
        this.centerMessages.add(messages);
        messages.setCenter(this);
        return this;
    }

    public Center removeCenterMessages(Messages messages) {
        this.centerMessages.remove(messages);
        messages.setCenter(null);
        return this;
    }

    public void setCenterMessages(Set<Messages> messages) {
        this.centerMessages = messages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Center)) {
            return false;
        }
        return id != null && id.equals(((Center) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Center{" +
            "id=" + getId() +
            ", nameAr='" + getNameAr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
