package com.wifitz.bean;

/**
 * ??????  ???????
 * @author ????
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
