package com.wifitz.joblaunchers;

import java.io.IOException;

import com.wifitz.utils.Configuration;
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
import com.wifitz.utils.TimeStrUtil;

public class StoreJobLauncher implements IJob{
	// private static Configuration configuration = new Configuration();
	// private static Logger logger = Logger.getLogger(StoreJobLauncher.class);

	// �꣺2017 ��2017_6 ��2017_5_21 ʱ2017_5_21.17
	// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ�� ������Ϊʱ���У�������ʱ����2017 2017_5 2017_5_21 2017_5_21.11��
	// ���ĸ�����Ϊ�豸��id
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
		
		
		// args[0] + Configuration.UNDERLINE + args[1] �ļ���
		// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ��Configuration.getInputFilesName(args[3], args[1])
		String[] otherArgs = { Configuration.INPUT_ROOT_PATH + Configuration.getInputFilesName(args[3], args[1]), // ��Ҫ�������ļ���·��
		//String[] otherArgs = { args[4], 			
				Configuration.RUDIANLIANG_MAC_ROOT_PATH + args[0] + "/" + Configuration.getTimeThresholdPath(args[2])
				+ TimeStrUtil.changeTime2Path(args[1], args[2]) + Configuration.getFileName(args[3], args[1]), // �����õ���mac��Ϣ�����·��
				
				Configuration.RUDIANLIANG_TMP_PATH, // ��ʱ�ļ�
				
				Configuration.RUDIANLIANG_PEOPLE_ROOT_PATH + args[0] + "/" + Configuration.getTimeThresholdPath(args[2])
						+ TimeStrUtil.changeTime2Path(args[1], args[2]) + Configuration.getFileName(args[3], args[1]) }; // ���ս�����·��


		org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();

		// ��һ��job������
		Job job1 = new Job(conf, "��ϴ��ҵ");
		JobConf jobConf = new JobConf();
		jobConf.setNumMapTasks(2);
		jobConf.setNumReduceTasks(2);
		job1.setJarByClass(StoreJobLauncher.class);
		// Mapper<LongWritable, Text, Text, Text>
		job1.setMapperClass(InToStoreMapper.class);
		// Reducer<Text, Text, Text, LongWritable>
		job1.setReducerClass(InToStoreReducer.class);

		job1.setMapOutputKeyClass(Text.class);// map�׶ε������key
		job1.setMapOutputValueClass(Text.class);// map�׶ε������value

		job1.setOutputKeyClass(Text.class);// reduce�׶ε������key
		job1.setOutputValueClass(LongWritable.class);// reduce�׶ε������value


		Path outputPath = new Path(otherArgs[1]);
		outputPath.getFileSystem(conf).delete(outputPath, true);
		// job1����������ļ�·��
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

		// // ���߳�����
		// Thread t = new Thread(jobCtrl);
		// t.start();
		// while (true) {
		// if (jobCtrl.allFinished()) {
		// // �����ҵ�ɹ���ɣ��ʹ�ӡ�ɹ���ҵ����Ϣ
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
	 * System.exit(1); return; } // args[0] + Configuration.UNDERLINE + args[1] �ļ���
	 * // ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ��Configuration.getInputFilesName(args[3], args[1])
	 * String[] otherArgs = { Configuration.INPUT_ROOT_PATH +
	 * Configuration.getInputFilesName(args[3], args[1]), // ��Ҫ�������ļ���·��
	 * Configuration.RUDIANLIANG_MAC_ROOT_PATH + Configuration.getUserPath(args[0]) +
	 * Configuration.getTimeThresholdPath(args[2]) +
	 * TimeStrUtil.changeTime2Path(args[1], args[2]) +
	 * Configuration.getFileName(args[3], args[1]), // �����õ���mac��Ϣ�����·��
	 * Configuration.RUDIANLIANG_TMP_PATH, // ��ʱ�ļ�
	 * Configuration.RUDIANLIANG_PEOPLE_ROOT_PATH + args[0] + "/" +
	 * Configuration.getTimeThresholdPath(args[2]) +
	 * TimeStrUtil.changeTime2Path(args[1], args[2]) +
	 * Configuration.getFileName(args[3], args[1]) }; // ���ս�����·��
	 * 
	 * System.out.println(otherArgs[3]); // System.exit(1); Configuration conf =
	 * new Configuration(); // ��һ��job������ Job job1 = new Job(conf, "��ϴ��ҵ");
	 * job1.setJarByClass(StoreJobLauncher.class); // Mapper<LongWritable, Text,
	 * Text, Text> job1.setMapperClass(InToStoreMapper.class); // Reducer<Text,
	 * Text, Text, LongWritable> job1.setReducerClass(InToStoreReducer.class);
	 * 
	 * job1.setMapOutputKeyClass(Text.class);// map�׶ε������key
	 * job1.setMapOutputValueClass(Text.class);// map�׶ε������value
	 * 
	 * job1.setOutputKeyClass(Text.class);// reduce�׶ε������key
	 * job1.setOutputValueClass(LongWritable.class);// reduce�׶ε������value
	 * 
	 * // // ����������� // ControlledJob ctrljob1 = new ControlledJob(conf); //
	 * ctrljob1.setJob(job1); // ���ж� job1 ���Ŀ¼�Ƿ���� Path outputPath = new
	 * Path(otherArgs[1]); outputPath.getFileSystem(conf).delete(outputPath,
	 * true); // job1����������ļ�·�� FileInputFormat.addInputPath(job1, new
	 * Path(otherArgs[0])); FileOutputFormat.setOutputPath(job1, new
	 * Path(otherArgs[1])); // // // �ڶ�����ҵ������ ���������ͳ�� // Job job2 = new
	 * Job(conf, "in to store"); // job2.setJarByClass(StoreJobLauncher.class);
	 * // // job2.setMapperClass(InToStoreCountMapper.class); // //
	 * job2.setMapOutputKeyClass(Text.class);// map�׶ε������key //
	 * job2.setMapOutputValueClass(IntWritable.class);// map�׶ε������value // // //
	 * job2.setOutputKeyClass(Text.class);//reduce�׶ε������key // //
	 * job2.setOutputValueClass(IntWritable.class);//reduce�׶ε������value // // //
	 * ��ҵ2����������� // ControlledJob ctrljob2 = new ControlledJob(conf); //
	 * ctrljob2.setJob(job2); // // // ���ö����ҵֱ�ӵ�������ϵ // // ������д�� // //
	 * ��˼Ϊjob2��������������job1��ҵ����� // // ctrljob2.addDependingJob(ctrljob1); // //
	 * // ����·������һ����ҵ�����·�������������args[1],Ҫ�������Ӧ�� //
	 * FileInputFormat.addInputPath(job2, new Path(otherArgs[1])); // Path
	 * outputPath2 = new Path(otherArgs[2]); //
	 * outputPath.getFileSystem(conf).delete(outputPath2, true); //
	 * FileOutputFormat.setOutputPath(job2, new Path(otherArgs[2])); // // //
	 * ���Ŀ�������������������ܵ���������ҵ // JobControl jobCtrl = new
	 * JobControl("ru dian liang ctrl"); // // // ��ӵ��ܵ�JobControl����п��� //
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
	 * // // ���߳����� // Thread t = new Thread(jobCtrl); // t.start(); // while
	 * (true) { // if (jobCtrl.allFinished()) { // // �����ҵ�ɹ���ɣ��ʹ�ӡ�ɹ���ҵ����Ϣ // //
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
