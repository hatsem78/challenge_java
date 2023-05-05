package com.challenge_java.challenge_java.model.entity;


import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long number;

    private Integer cityCode;

    private String contrycode;

    public Phone() {
    }

    public Phone(Long number, Integer cityCode, String contrycode) {
        this.number = number;
        this.cityCode = cityCode;
        this.contrycode = contrycode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact")
    @JsonIgnore
    User contact;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getContrycode() {
        return contrycode;
    }

    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }
}
