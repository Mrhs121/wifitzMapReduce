package com.wifitz.joblaunchers;

public class StartUpStore {
	// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ�� ������Ϊʱ���У�������ʱ����2017 2017_5 2017_5_21 2017_5_21.11��
	// ���ĸ�����Ϊ�豸��id
	public static void main(String[] args) {
		StoreJobLauncher storeJobLauncher = new StoreJobLauncher();
//		
//		String username = "chen";
//		String time = "2017_5_2.";
//		String type = "hour";
//		String id = "chen";
		Long starttimer  = System.currentTimeMillis();
		try {
			System.out.println("start store");
			storeJobLauncher.start(args);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		Long endtime = System.currentTimeMillis();
		System.out.println("start-"+starttimer);
		System.out.println("end-"+endtime);
	}
}
