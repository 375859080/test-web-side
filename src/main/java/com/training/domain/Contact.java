package com.training.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//客服电话表
@Entity
public class Contact {
    @Id
    @GeneratedValue
    private Long id;
    //对应的企业id
    private Long companyId;
    //客服称呼
    private String name;
    //电话
    private String phone;

    public Contact() {
    }

    public Contact(Long companyId, String name, String phone) {
        this.companyId = companyId;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
