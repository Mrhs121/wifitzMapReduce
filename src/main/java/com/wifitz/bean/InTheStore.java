package com.wifitz.bean;

public class InTheStore {

	private String Time;
	private String UserName;
	private String TotalRecorder;
	private String FastGetOutRecorder;
	private String NormalRecorder;
	private String DeepVisitRecorder;
	private String KeliuLiang; // ���������
	private String FastGetOutPercent; // ������
	private String DeepVisitPercent; // �����
	private String InTheStorePercent; // �����
	
	public InTheStore() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param time   ʱ��
	 * @param userName  �û�������
	 * @param totalRecorder   �ڵ������������
	 * @param fastGetOutRecorder �ܿ���뿪����
	 * @param midVisitRecorder  ����һС���뿪����
	 * @param deepVisitRecorder ���˺ܾò��뿪����
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
