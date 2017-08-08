package com.wifitz.joblaunchers;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import com.wifitz.bean.InTheStore;
import com.wifitz.mapreduce.InToStoreMapper;
import com.wifitz.mapreduce.InToStoreReducer;
import com.wifitz.mapreduce.InToStoreReducer.VisitLivenessRecorder2;
import com.wifitz.utils.HdfsUtil;
import com.wifitz.utils.JsonUtil;
import com.wifitz.utils.SomeText;
import com.wifitz.utils.TimeStrUtil;

public class StoreJobLauncher implements IJob{
	// private static Configuration configuration = new Configuration();
	// private static Logger logger = Logger.getLogger(StoreJobLauncher.class);

	// 年：2017 月2017_6 日2017_5_21 时2017_5_21.17
	// 输入的参数应该是 第一个用户的名字 第二个时间 第三个为时间阈（年月日时）（2017 2017_5 2017_5_21 2017_5_21.11）
	// 第四个参数为设备的id
	@SuppressWarnings("deprecation")
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */

	public void start(String[] args) throws IOException {

		//System.out.println("hung"+args[1]);
		
		
		if (args.length < 4) {
			System.out.println("the args length less 2 exit");
			System.exit(1);
			return;
		}
		
		
		// args[0] + SomeText.UNDERLINE + args[1] 文件名
		// 输入的参数应该是 第一个用户的名字 第二个时间SomeText.getInputFilesName(args[3], args[1])
		String[] otherArgs = { SomeText.INPUT_ROOT_PATH + SomeText.getInputFilesName(args[3], args[1]), // 需要分析的文件的路径
		//String[] otherArgs = { args[4], 			
				SomeText.RUDIANLIANG_MAC_ROOT_PATH + args[0] + "/" + SomeText.getTimeThresholdPath(args[2])
				+ TimeStrUtil.changeTime2Path(args[1], args[2]) + SomeText.getFileName(args[3], args[1]), // 分析得到的mac信息的输出路径
				
				SomeText.RUDIANLIANG_TMP_PATH, // 临时文件
				
				SomeText.RUDIANLIANG_PEOPLE_ROOT_PATH + args[0] + "/" + SomeText.getTimeThresholdPath(args[2])
						+ TimeStrUtil.changeTime2Path(args[1], args[2]) + SomeText.getFileName(args[3], args[1]) }; // 最终结果输出路径


		Configuration conf = new Configuration();

		// 第一个job的配置
		Job job1 = new Job(conf, "清洗作业");
		JobConf jobConf = new JobConf();
		jobConf.setNumMapTasks(2);
		jobConf.setNumReduceTasks(2);
		job1.setJarByClass(StoreJobLauncher.class);
		// Mapper<LongWritable, Text, Text, Text>
		job1.setMapperClass(InToStoreMapper.class);
		// Reducer<Text, Text, Text, LongWritable>
		job1.setReducerClass(InToStoreReducer.class);

		job1.setMapOutputKeyClass(Text.class);// map阶段的输出的key
		job1.setMapOutputValueClass(Text.class);// map阶段的输出的value

		job1.setOutputKeyClass(Text.class);// reduce阶段的输出的key
		job1.setOutputValueClass(LongWritable.class);// reduce阶段的输出的value


		Path outputPath = new Path(otherArgs[1]);
		outputPath.getFileSystem(conf).delete(outputPath, true);
		// job1的输入输出文件路径
		FileInputFormat.addInputPath(job1, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job1, new Path(otherArgs[1]));


		try {
			if (job1.waitForCompletion(true)) {
				InTheStore data1 = new InTheStore(args[1], args[0],
						"" + job1.getCounters().findCounter(VisitLivenessRecorder2.TotalRecorder).getValue(),
						"" + job1.getCounters().findCounter(VisitLivenessRecorder2.FastGetOutRecorder).getValue(),
						"" + job1.getCounters().findCounter(VisitLivenessRecorder2.MidVisitRecorder).getValue(),
						"" + job1.getCounters().findCounter(VisitLivenessRecorder2.DeepVisitRecorder).getValue(),
						"" + job1.getCounters().findCounter(VisitLivenessRecorder2.KeLiuLiang).getValue());
				// System.out.println(data);
				// System.out.println(data2);
				HdfsUtil.updateToHDFS(otherArgs[3], JsonUtil.creatJsonString(data1));
				// System.out.println(jobCtrl.getSuccessfulJobList());
			}
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		// // 在线程启动
		// Thread t = new Thread(jobCtrl);
		// t.start();
		// while (true) {
		// if (jobCtrl.allFinished()) {
		// // 如果作业成功完成，就打印成功作业的信息
		//
		// InTheStore data1 = new InTheStore(args[1], args[0],
		// "" +
		// job1.getCounters().findCounter(VisitLivenessRecorder2.TotalRecorder).getValue(),
		// "" +
		// job1.getCounters().findCounter(VisitLivenessRecorder2.FastGetOutRecorder).getValue(),
		// "" +
		// job1.getCounters().findCounter(VisitLivenessRecorder2.MidVisitRecorder).getValue(),
		// "" +
		// job1.getCounters().findCounter(VisitLivenessRecorder2.DeepVisitRecorder).getValue(),
		// "" +
		// job1.getCounters().findCounter(VisitLivenessRecorder2.KeLiuLiang).getValue());
		// // System.out.println(data);
		// // System.out.println(data2);
		// HdfsUtil.updateToHDFS(otherArgs[3], JsonUtil.creatJsonString(data1));
		// System.out.println(jobCtrl.getSuccessfulJobList());
		// jobCtrl.stop();
		// break;
		// }
		// }
	}
	/*
	 * public static void main(String[] args) throws IOException {
	 * 
	 * if (args.length < 4) { System.out.println("the args length less 2 exit");
	 * System.exit(1); return; } // args[0] + SomeText.UNDERLINE + args[1] 文件名
	 * // 输入的参数应该是 第一个用户的名字 第二个时间SomeText.getInputFilesName(args[3], args[1])
	 * String[] otherArgs = { SomeText.INPUT_ROOT_PATH +
	 * SomeText.getInputFilesName(args[3], args[1]), // 需要分析的文件的路径
	 * SomeText.RUDIANLIANG_MAC_ROOT_PATH + SomeText.getUserPath(args[0]) +
	 * SomeText.getTimeThresholdPath(args[2]) +
	 * TimeStrUtil.changeTime2Path(args[1], args[2]) +
	 * SomeText.getFileName(args[3], args[1]), // 分析得到的mac信息的输出路径
	 * SomeText.RUDIANLIANG_TMP_PATH, // 临时文件
	 * SomeText.RUDIANLIANG_PEOPLE_ROOT_PATH + args[0] + "/" +
	 * SomeText.getTimeThresholdPath(args[2]) +
	 * TimeStrUtil.changeTime2Path(args[1], args[2]) +
	 * SomeText.getFileName(args[3], args[1]) }; // 最终结果输出路径
	 * 
	 * System.out.println(otherArgs[3]); // System.exit(1); Configuration conf =
	 * new Configuration(); // 第一个job的配置 Job job1 = new Job(conf, "清洗作业");
	 * job1.setJarByClass(StoreJobLauncher.class); // Mapper<LongWritable, Text,
	 * Text, Text> job1.setMapperClass(InToStoreMapper.class); // Reducer<Text,
	 * Text, Text, LongWritable> job1.setReducerClass(InToStoreReducer.class);
	 * 
	 * job1.setMapOutputKeyClass(Text.class);// map阶段的输出的key
	 * job1.setMapOutputValueClass(Text.class);// map阶段的输出的value
	 * 
	 * job1.setOutputKeyClass(Text.class);// reduce阶段的输出的key
	 * job1.setOutputValueClass(LongWritable.class);// reduce阶段的输出的value
	 * 
	 * // // 加入控制容器 // ControlledJob ctrljob1 = new ControlledJob(conf); //
	 * ctrljob1.setJob(job1); // 先判断 job1 输出目录是否存在 Path outputPath = new
	 * Path(otherArgs[1]); outputPath.getFileSystem(conf).delete(outputPath,
	 * true); // job1的输入输出文件路径 FileInputFormat.addInputPath(job1, new
	 * Path(otherArgs[0])); FileOutputFormat.setOutputPath(job1, new
	 * Path(otherArgs[1])); // // // 第二个作业的配置 入店人数的统计 // Job job2 = new
	 * Job(conf, "in to store"); // job2.setJarByClass(StoreJobLauncher.class);
	 * // // job2.setMapperClass(InToStoreCountMapper.class); // //
	 * job2.setMapOutputKeyClass(Text.class);// map阶段的输出的key //
	 * job2.setMapOutputValueClass(IntWritable.class);// map阶段的输出的value // // //
	 * job2.setOutputKeyClass(Text.class);//reduce阶段的输出的key // //
	 * job2.setOutputValueClass(IntWritable.class);//reduce阶段的输出的value // // //
	 * 作业2加入控制容器 // ControlledJob ctrljob2 = new ControlledJob(conf); //
	 * ctrljob2.setJob(job2); // // // 设置多个作业直接的依赖关系 // // 如下所写： // //
	 * 意思为job2的启动，依赖于job1作业的完成 // // ctrljob2.addDependingJob(ctrljob1); // //
	 * // 输入路径是上一个作业的输出路径，因此这里填args[1],要和上面对应好 //
	 * FileInputFormat.addInputPath(job2, new Path(otherArgs[1])); // Path
	 * outputPath2 = new Path(otherArgs[2]); //
	 * outputPath.getFileSystem(conf).delete(outputPath2, true); //
	 * FileOutputFormat.setOutputPath(job2, new Path(otherArgs[2])); // // //
	 * 主的控制容器，控制上面的总的两个子作业 // JobControl jobCtrl = new
	 * JobControl("ru dian liang ctrl"); // // // 添加到总的JobControl里，进行控制 //
	 * jobCtrl.addJob(ctrljob1); // jobCtrl.addJob(ctrljob2);
	 * 
	 * try { if(job1.waitForCompletion(true)){ InTheStore data1 = new
	 * InTheStore(args[1], args[0], "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.TotalRecorder).
	 * getValue(), "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.FastGetOutRecorder)
	 * .getValue(), "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.MidVisitRecorder).
	 * getValue(), "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.DeepVisitRecorder).
	 * getValue(), "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.KeLiuLiang).
	 * getValue()); // System.out.println(data); // System.out.println(data2);
	 * HdfsUtil.updateToHDFS(otherArgs[3], JsonUtil.creatJsonString(data1));
	 * //System.out.println(jobCtrl.getSuccessfulJobList()); } } catch
	 * (ClassNotFoundException e) {
	 * 
	 * e.printStackTrace(); } catch (InterruptedException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * // // 在线程启动 // Thread t = new Thread(jobCtrl); // t.start(); // while
	 * (true) { // if (jobCtrl.allFinished()) { // // 如果作业成功完成，就打印成功作业的信息 // //
	 * InTheStore data1 = new InTheStore(args[1], args[0], // "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.TotalRecorder).
	 * getValue(), // "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.FastGetOutRecorder)
	 * .getValue(), // "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.MidVisitRecorder).
	 * getValue(), // "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.DeepVisitRecorder).
	 * getValue(), // "" +
	 * job1.getCounters().findCounter(VisitLivenessRecorder2.KeLiuLiang).
	 * getValue()); // // System.out.println(data); // //
	 * System.out.println(data2); // HdfsUtil.updateToHDFS(otherArgs[3],
	 * JsonUtil.creatJsonString(data1)); //
	 * System.out.println(jobCtrl.getSuccessfulJobList()); // jobCtrl.stop(); //
	 * break; // } // }
	 * 
	 * }
	 */

}
