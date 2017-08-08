package com.wifitz.joblaunchers;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.wifitz.bean.KeLiuLiang;
import com.wifitz.mapreduce.MacMergedMapper;
import com.wifitz.mapreduce.MacMergedReducer;
import com.wifitz.mapreduce.MacMergedReducer.FileRecorder;
import com.wifitz.utils.HdfsUtil;
import com.wifitz.utils.JsonUtil;
import com.wifitz.utils.SomeText;
import com.wifitz.utils.TimeStrUtil;

/**
 * 一共西药 输入两个 参数应该是 第一个用户的名字 第二个时间 例如用户 chen 时间必须按照这种格式输入 2017_5_24.16
 * 
 * @author 黄晟
 *
 */
public class KeLiuLiangJobLauncher implements IJob{

	// private static Logger logger =
	// Logger.getLogger(KeLiuLiangJobLauncher.class);
	// private static Configuration configuration = new Configuration();


	
	@SuppressWarnings("deprecation")
	public void start(String[] args) throws Exception, ClassNotFoundException, InterruptedException{

		if (args.length < 4) {
			System.out.println("the args length less 4 exit");
			System.exit(1);
			return;
		}
		// args[0] + SomeText.UNDERLINE + args[1] 文件名
		// 输入的参数应该是 第一个用户的名字 第二个时间
		// args[0] + SomeText.UNDERLINE + args[1] 文件名
		// 输入的参数应该是 第一个用户的名字 第二个时间SomeText.getInputFilesName(args[3], args[1])
		String[] otherArgs = { SomeText.INPUT_ROOT_PATH + SomeText.getInputFilesName(args[3], args[1]), // 需要分析的文件的路径
		//String[] otherArgs = { args[4], 		
				SomeText.KELIULIANG_MAC_ROOT_PATH + args[0] + "/" + SomeText.getTimeThresholdPath(args[2])
				+ TimeStrUtil.changeTime2Path(args[1], args[2]) + SomeText.getFileName(args[3], args[1]) , // 分析得到的mac信息的输出路径
				
				SomeText.KELIULIANG_TMP_PATH, // 临时文件
				
				SomeText.KELIULIANG_PEOPLE_ROOT_PATH+ args[0] + "/" + SomeText.getTimeThresholdPath(args[2])
						+ TimeStrUtil.changeTime2Path(args[1], args[2]) + SomeText.getFileName(args[3], args[1]) };

		Configuration conf = new Configuration();
		// 第一个job的配置
		Job job1 = new Job(conf, "清洗作业");
		job1.setJarByClass(KeLiuLiangJobLauncher.class);

		job1.setMapperClass(MacMergedMapper.class);
		job1.setReducerClass(MacMergedReducer.class);

		job1.setMapOutputKeyClass(Text.class);// map阶段的输出的key
		job1.setMapOutputValueClass(IntWritable.class);// map阶段的输出的value

		job1.setOutputKeyClass(Text.class);// reduce阶段的输出的key
		job1.setOutputValueClass(IntWritable.class);// reduce阶段的输出的value

		// 加入控制容器
		ControlledJob ctrljob1 = new ControlledJob(conf);
		ctrljob1.setJob(job1);
		// 先判断 job1 输出目录是否存在
		Path outputPath = new Path(otherArgs[1]);
		outputPath.getFileSystem(conf).delete(outputPath, true);
		// job1的输入输出文件路径
		FileInputFormat.addInputPath(job1, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job1, new Path(otherArgs[1]));

		if(job1.waitForCompletion(true)){
			// 如果作业成功完成，就打印成功作业的信息
			
			KeLiuLiang data = new KeLiuLiang(args[1], args[0],
					"" + job1.getCounters().findCounter(FileRecorder.TotalRecorder).getValue());
			HdfsUtil.updateToHDFS(otherArgs[3], JsonUtil.creatJsonString(data));
			
		}
		
	}


	
	
	
/*	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		if (args.length < 4) {
			System.out.println("the args length less 4 exit");
			System.exit(1);
			return;
		}
		// args[0] + SomeText.UNDERLINE + args[1] 文件名f
		// 输入的参数应该是 第一个用户的名字 第二个时间
		// args[0] + SomeText.UNDERLINE + args[1] 文件名
		// 输入的参数应该是 第一个用户的名字 第二个时间SomeText.getInputFilesName(args[3], args[1])
		String[] otherArgs = { SomeText.INPUT_ROOT_PATH + SomeText.getInputFilesName(args[3], args[1]), // 需要分析的文件的路径
				SomeText.KELIULIANG_MAC_ROOT_PATH + SomeText.getUserPath(args[0])
						+ SomeText.getTimeThresholdPath(args[2]) + TimeStrUtil.changeTime2Path(args[1], args[2])
						+ SomeText.getFileName(args[3], args[1]), // 分析得到的mac信息的输出路径
				SomeText.KELIULIANG_TMP_PATH, // 临时文件
				SomeText.KELIULIANG_PEOPLE_ROOT_PATH+ args[0] + "/" + SomeText.getTimeThresholdPath(args[2])
						+ TimeStrUtil.changeTime2Path(args[1], args[2]) + SomeText.getFileName(args[3], args[1]) };

		Configuration conf = new Configuration();
		// 第一个job的配置
		Job job1 = new Job(conf, "清洗作业");
		job1.setJarByClass(KeLiuLiangJobLauncher.class);

		job1.setMapperClass(MacMergedMapper.class);
		job1.setReducerClass(MacMergedReducer.class);

		job1.setMapOutputKeyClass(Text.class);// map阶段的输出的key
		job1.setMapOutputValueClass(IntWritable.class);// map阶段的输出的value

		job1.setOutputKeyClass(Text.class);// reduce阶段的输出的key
		job1.setOutputValueClass(IntWritable.class);// reduce阶段的输出的value

		// 加入控制容器
		ControlledJob ctrljob1 = new ControlledJob(conf);
		ctrljob1.setJob(job1);
		// 先判断 job1 输出目录是否存在
		Path outputPath = new Path(otherArgs[1]);
		outputPath.getFileSystem(conf).delete(outputPath, true);
		// job1的输入输出文件路径
		FileInputFormat.addInputPath(job1, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job1, new Path(otherArgs[1]));

		// 第二个作业的配置
		Job job2 = new Job(conf, "整理作业");
		job2.setJarByClass(KeLiuLiangJobLauncher.class);

		job2.setMapperClass(KeLiuLiangCountMapper.class);
		// job2.setReducerClass(Reduce_Second.class);

		job2.setMapOutputKeyClass(LongWritable.class);// map阶段的输出的key
		job2.setMapOutputValueClass(IntWritable.class);// map阶段的输出的value

		// job2.setOutputKeyClass(Text.class);//reduce阶段的输出的key
		// job2.setOutputValueClass(IntWritable.class);//reduce阶段的输出的value

		// 作业2加入控制容器
		ControlledJob ctrljob2 = new ControlledJob(conf);
		ctrljob2.setJob(job2);

		// 设置多个作业直接的依赖关系
		// 如下所写：
		// 意思为job2的启动，依赖于job1作业的完成

		ctrljob2.addDependingJob(ctrljob1);
		// 先判断 job1 输出目录是否存在
		Path outputPath2 = new Path(otherArgs[2]);
		outputPath.getFileSystem(conf).delete(outputPath2, true);
		// 输入路径是上一个作业的输出路径，因此这里填args[1],要和上面对应好
		FileInputFormat.addInputPath(job2, new Path(otherArgs[1]));

		FileOutputFormat.setOutputPath(job2, new Path(otherArgs[2]));

		// 主的控制容器，控制上面的总的两个子作业
		JobControl jobCtrl = new JobControl("myctrl");

		// 添加到总的JobControl里，进行控制
		jobCtrl.addJob(ctrljob1);
		jobCtrl.addJob(ctrljob2);

		// 在线程启动
		Thread t = new Thread(jobCtrl);
		t.start();

		while (true) {
			if (jobCtrl.allFinished()) {
				// 如果作业成功完成，就打印成功作业的信息
				System.out
						.println("Total num:" + job2.getCounters().findCounter(FileRecorder.TotalRecorder).getValue());
				System.out.println("reduce 里面的呵呵呵 num:" + job1.getCounters().findCounter(test.hehe).getValue());
				KeLiuLiang data = new KeLiuLiang(args[1], args[0],
						"" + job2.getCounters().findCounter(FileRecorder.TotalRecorder).getValue());
				HdfsUtil.updateToHDFS(otherArgs[3], JsonUtil.creatJsonString(data));
				System.out.println(jobCtrl.getSuccessfulJobList());
				jobCtrl.stop();
				break;
			}
		}
	}
*/
}
