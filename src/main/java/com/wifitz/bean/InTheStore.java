package com.wifitz.bean;

public class InTheStore {

	private String Time;
	private String UserName;
	private String TotalRecorder;
	private String FastGetOutRecorder;
	private String NormalRecorder;
	private String DeepVisitRecorder;
	private String KeliuLiang; // 总体客流量
	private String FastGetOutPercent; // 跳出率
	private String DeepVisitPercent; // 深访率
	private String InTheStorePercent; // 入店率
	
	public InTheStore() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param time   时间
	 * @param userName  用户的名字
	 * @param totalRecorder   在店里面的总人数
	 * @param fastGetOutRecorder 很快就离开的人
	 * @param midVisitRecorder  逛了一小会离开的人
	 * @param deepVisitRecorder 逛了很久才离开的人
	 */
	public InTheStore(String time, String userName, String totalRecorder, String fastGetOutRecorder,
			String midVisitRecorder, String deepVisitRecorder,String keliuliang) {
		super();
		Time = time;
		UserName = userName;
		TotalRecorder = totalRecorder;
		FastGetOutRecorder = fastGetOutRecorder;
		NormalRecorder = midVisitRecorder;
		DeepVisitRecorder = deepVisitRecorder;
		this.KeliuLiang = keliuliang;
	    
		this.FastGetOutPercent =( 100*Float.parseFloat(this.FastGetOutRecorder)/Float.parseFloat(this.KeliuLiang))+"%";
		this.DeepVisitPercent =( 100*Float.parseFloat(this.DeepVisitRecorder)/Float.parseFloat(this.KeliuLiang))+"%";
		this.InTheStorePercent =( 100*Float.parseFloat(this.TotalRecorder)/Float.parseFloat(this.KeliuLiang))+"%";
		
		
		
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
		UserName = userName;
	}
	public String getTotalRecorder() {
		return TotalRecorder;
	}
	public void setTotalRecorder(String totalRecorder) {
		TotalRecorder = totalRecorder;
	}
	public String getFastGetOutRecorder() {
		return FastGetOutRecorder;
	}
	public void setFastGetOutRecorder(String fastGetOutRecorder) {
		FastGetOutRecorder = fastGetOutRecorder;
	}
	public String getMidVisitRecorder() {
		return NormalRecorder;
	}
	public void setMidVisitRecorder(String midVisitRecorder) {
		NormalRecorder = midVisitRecorder;
	}
	public String getDeepVisitRecorder() {
		return DeepVisitRecorder;
	}
	public void setDeepVisitRecorder(String deepVisitRecorder) {
		DeepVisitRecorder = deepVisitRecorder;
	}

	public String getKeliuLiang() {
		return KeliuLiang;
	}

	public void setKeliuLiang(String keliuLiang) {
		KeliuLiang = keliuLiang;
	}

	public String getFastGetOutPercent() {
		return FastGetOutPercent;
	}

	public void setFastGetOutPercent(String fastGetOutPercent) {
		FastGetOutPercent = fastGetOutPercent;
	}

	public String getDeepVisitPercent() {
		return DeepVisitPercent;
	}

	public void setDeepVisitPercent(String deepVisitPercent) {
		DeepVisitPercent = deepVisitPercent;
	}

	public String getInTheStorePercent() {
		return InTheStorePercent;
	}

	public void setInTheStorePercent(String inTheStorePercent) {
		InTheStorePercent = inTheStorePercent;
	}
	
	
}
