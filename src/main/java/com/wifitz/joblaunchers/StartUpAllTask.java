package com.wifitz.joblaunchers;



public class StartUpAllTask {

	// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ�� ������Ϊʱ���У�������ʱ����2017 2017_5 2017_5_21 2017_5_21.11��
	// ���ĸ�����Ϊ�豸��id
	public static void main(String[] args) {


		StoreJobLauncher storeJobLauncher = new StoreJobLauncher();
		KeLiuLiangJobLauncher keliulianglauncher= new KeLiuLiangJobLauncher();
		
//		
//		String username = "chen";
//		String time = "2017_5_2.";
//		String type = "hour";
//		String id = "chen";
		Long starttimer  = System.currentTimeMillis();
		try {
			System.out.println("start keliuliang");
			keliulianglauncher.start(args);
			System.out.println("start store");
			storeJobLauncher.start(args);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		Long endtime = System.currentTimeMillis();
		System.out.println("start-"+starttimer);
		System.out.println("end-"+endtime);
	}
}
