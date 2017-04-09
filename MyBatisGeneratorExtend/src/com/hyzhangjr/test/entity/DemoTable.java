package com.hyzhangjr.test.entity;

import javax.persistence.*;

/**
 * null
 * 
 * @mbg.generated
 */
@Table(name = "CTP4.DEMO_TABLE")
public class DemoTable {
    @Column(name = "ID")
    @GeneratedValue(generator = "JDBC")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private String age;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "NOTE")
    private String note;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return AGE
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return ADDRESS
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return NOTE
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return String.format("{id=%s,name=%s,age=%s,address=%s,note=%s}", this.id, this.name, this.age, this.address, this.note);
    }
}