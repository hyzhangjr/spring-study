package com.hyzhangjr.spring.bean;

/*
drop table  demo_table;
create table demo_table (
	id		VARCHAR2(10),
	name	VARCHAR2(100),
	age		VARCHAR2(10),
	address	VARCHAR2(1000),
	note	VARCHAR2(1000)
);
 */
public class DemoBean {

	private String id;
	private String name;
	private String age;
	private String address;
	private String note;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
