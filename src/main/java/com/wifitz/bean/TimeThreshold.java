package com.wifitz.bean;

/**
 * 时间阈值  年月日时
 * @author 黄晟
 *
 */
public enum TimeThreshold {
	
	YERE("year"),
	
	MONTH("month"),
	
	DAY("day"),
	
	HOUR("hour");
	
	private String Threshold;
	
	private TimeThreshold(String Threshold) {
		this.Threshold = Threshold;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.Threshold;
	}
}
