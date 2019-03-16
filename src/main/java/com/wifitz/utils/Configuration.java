package com.wifitz.utils;

public class Configuration {

	public static final String INPUT_ROOT_PATH = "/wifitzlogs/usr/wifitzlogs/";  // �ļ�������·��
	//public static final String INPUT_ROOT_PATH = "/input/";  // �ļ�������·��
	//public static final String INPUTROOTPATH = "/input/";  // �ļ�������·��
	// ������
	public static final String KELIULIANG_MAC_ROOT_PATH = "/keliuliang/macdata/"; // ��һ��job�����õ���mac���ݴ��·��
	public static final String KELIULIANG_PEOPLE_ROOT_PATH = "/keliuliang/people/"; // �ڶ���jobͳ��mac�õ����������� ��ŵ�ַ
	public static final String KELIULIANG_TMP_PATH = "/keliuliang/tmp/data/"; // ��ʱ�ļ���ַ
	
	// ������ ���
	public static final String RUDIANLIANG_MAC_ROOT_PATH = "/jindianliang/macdata/"; // root
	public static final String RUDIANLIANG_PEOPLE_ROOT_PATH = "/jindianliang/people/";
	public static final String RUDIANLIANG_TMP_PATH = "/jindianliang/tmp/data/";
	
	public static final String UNDERLINE = "_"; // �»���
	public static final String WILDCARDS = "*"; // ͨ���
	
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
