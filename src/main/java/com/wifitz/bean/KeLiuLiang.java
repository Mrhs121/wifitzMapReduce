package com.wifitz.bean;

public class KeLiuLiang {
	private String Time;
	private String UserName;
	private String TotalKeliuliang;

	public KeLiuLiang() {
		// TODO Auto-generated constructor stub
	}
	
	public KeLiuLiang(String time, String userName, String totalKeliuliang) {
		super();
		Time = time;
		this.UserName = userName;
		this.TotalKeliuliang = totalKeliuliang;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		this.UserName = userName;
	}

	public String getTotalKeliuliang() {
		return TotalKeliuliang;
	}

	public void setTotalKeliuliang(String totalKeliuliang) {
		this.TotalKeliuliang = totalKeliuliang;
	}

}
