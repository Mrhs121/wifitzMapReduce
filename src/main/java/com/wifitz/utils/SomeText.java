package com.wifitz.utils;

public class SomeText {

	public static final String INPUT_ROOT_PATH = "/wifitzlogs/usr/wifitzlogs/";  // 文件的输入路径
	//public static final String INPUT_ROOT_PATH = "/input/";  // 文件的输入路径
	//public static final String INPUTROOTPATH = "/input/";  // 文件的输入路径
	// 客流量
	public static final String KELIULIANG_MAC_ROOT_PATH = "/keliuliang/macdata/"; // 第一个job分析得到的mac数据存放路径
	public static final String KELIULIANG_PEOPLE_ROOT_PATH = "/keliuliang/people/"; // 第二个job统计mac得到的最后的数据 存放地址
	public static final String KELIULIANG_TMP_PATH = "/keliuliang/tmp/data/"; // 临时文件地址
	
	// 进店量 相关
	public static final String RUDIANLIANG_MAC_ROOT_PATH = "/jindianliang/macdata/"; // root
	public static final String RUDIANLIANG_PEOPLE_ROOT_PATH = "/jindianliang/people/";
	public static final String RUDIANLIANG_TMP_PATH = "/jindianliang/tmp/data/";
	
	public static final String UNDERLINE = "_"; // 下划线
	public static final String WILDCARDS = "*"; // 通配符
	
	public static String getFileName(String username , String time) {
		return username+UNDERLINE+time;
	}
	
	public static String getTimeThresholdPath(String timeThreshold) {
		return timeThreshold+"/";
	}
	
	public static String getUserPath(String username) {
		return username+"/";
	}
	
	public static String getInputFilesName(String tzmac,String time) {

		return tzmac+UNDERLINE+time+WILDCARDS;
	}
}
