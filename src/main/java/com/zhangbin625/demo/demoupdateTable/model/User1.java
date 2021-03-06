package com.zhangbin625.demo.demoupdateTable.model;

import com.huawei.testcasebot.mybatisACT.annotation.Column;
import com.huawei.testcasebot.mybatisACT.annotation.Table;
import com.huawei.testcasebot.mybatisACT.constants.MySqlColumnType;

@Table(name="t_user1")
public class User1 {
	@Column(name = "id",type = MySqlColumnType.INT,length = 11,isKey = true, isNull=false, isAutoIncrement = true)
	private long id;
	@Column(name = "user_name",type = MySqlColumnType.VARCHAR,length = 111,isNull=false)
	private String userName;
	@Column(name = "gender",type = MySqlColumnType.VARCHAR,length = 6,isNull=false)
	private String gender;
	@Column(name = "age",type = MySqlColumnType.INT,length = 3,isNull=false)
	private int age;
	@Column(name = "address",type = MySqlColumnType.VARCHAR,length = 255)
	private String address;
	@Column(name = "phone",type = MySqlColumnType.VARCHAR,length = 11, isUnique=true)
	private String phone;
	@Column(name="hobby",type=MySqlColumnType.VARCHAR,length=10)
	private String hobby;
	@Column(name="mail",type=MySqlColumnType.VARCHAR,length=10)
	private String mail;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
