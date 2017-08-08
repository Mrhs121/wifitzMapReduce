package com.wifitz.joblaunchers;



import com.wifitz.utils.HdfsUtil;

public class StartUp {

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
			// ��Ҫ�Ƚ�С�ļ��ϲ�  �� ���ļ�  �ӿ�Ч�� 
			// �Ƚ�����·������ ���ϲ�д�ɴ��ļ�
			if (HdfsUtil.MergeSmallFilesToBigFiles(args[3]+"_"+args[1])) {
				System.out.println("�ϲ��ɹ�");
				args[4] = "/input/"+args[3]+"_"+args[1]+".log.all";
				System.out.println(args[4]);
				keliulianglauncher.start(args);
				storeJobLauncher.start(args);
			}

//		} catch (ClassNotFoundException e) {
//			
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		Long endtime = System.currentTimeMillis();
		System.out.println("start-"+starttimer);
		System.out.println("end-"+endtime);
	}
}
