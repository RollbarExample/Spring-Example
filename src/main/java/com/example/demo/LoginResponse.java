package com.example.demo;

import java.io.Serializable;

public class LoginResponse extends GenericResponse implements Serializable{

	private User userDetail;

	public User getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(User userDetail) {
		this.userDetail = userDetail;
	}
	

}
