package com.wifitz.joblaunchers;



import com.wifitz.utils.HdfsUtil;

public class StartUp {

	// 输入的参数应该是 第一个用户的名字 第二个时间 第三个为时间阈（年月日时）（2017 2017_5 2017_5_21 2017_5_21.11）
	// 第四个参数为设备的id
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
			// 需要先将小文件合并  成 大文件  加快效率 
			// 先将输入路径过滤 ，合并写成大文件
			if (HdfsUtil.MergeSmallFilesToBigFiles(args[3]+"_"+args[1])) {
				System.out.println("合并成功");
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
