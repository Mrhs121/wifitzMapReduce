package com.wifitz.joblaunchers;

import com.wifitz.utils.Configuration;
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
import com.wifitz.utils.TimeStrUtil;

/**
 * һ����ҩ �������� ����Ӧ���� ��һ���û������� �ڶ���ʱ�� �����û� chen ʱ����밴�����ָ�ʽ���� 2017_5_24.16
 * 
 * @author ����
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
		// args[0] + Configuration.UNDERLINE + args[1] �ļ���
		// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ��
		// args[0] + Configuration.UNDERLINE + args[1] �ļ���
		// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ��SomeText.getInputFilesName(args[3], args[1])
		String[] otherArgs = { Configuration.INPUT_ROOT_PATH + Configuration.getInputFilesName(args[3], args[1]), // ��Ҫ�������ļ���·��
		//String[] otherArgs = { args[4], 		
				Configuration.KELIULIANG_MAC_ROOT_PATH + args[0] + "/" + Configuration.getTimeThresholdPath(args[2])
				+ TimeStrUtil.changeTime2Path(args[1], args[2]) + Configuration.getFileName(args[3], args[1]) , // �����õ���mac��Ϣ�����·��
				
				Configuration.KELIULIANG_TMP_PATH, // ��ʱ�ļ�
				
				Configuration.KELIULIANG_PEOPLE_ROOT_PATH+ args[0] + "/" + Configuration.getTimeThresholdPath(args[2])
						+ TimeStrUtil.changeTime2Path(args[1], args[2]) + Configuration.getFileName(args[3], args[1]) };

		org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
		// ��һ��job������
		Job job1 = new Job(conf, "��ϴ��ҵ");
		job1.setJarByClass(KeLiuLiangJobLauncher.class);

		job1.setMapperClass(MacMergedMapper.class);
		job1.setReducerClass(MacMergedReducer.class);

		job1.setMapOutputKeyClass(Text.class);// map�׶ε������key
		job1.setMapOutputValueClass(IntWritable.class);// map�׶ε������value

		job1.setOutputKeyClass(Text.class);// reduce�׶ε������key
		job1.setOutputValueClass(IntWritable.class);// reduce�׶ε������value

		// �����������
		ControlledJob ctrljob1 = new ControlledJob(conf);
		ctrljob1.setJob(job1);
		// ���ж� job1 ���Ŀ¼�Ƿ����
		Path outputPath = new Path(otherArgs[1]);
		outputPath.getFileSystem(conf).delete(outputPath, true);
		// job1����������ļ�·��
		FileInputFormat.addInputPath(job1, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job1, new Path(otherArgs[1]));

		if(job1.waitForCompletion(true)){
			// �����ҵ�ɹ���ɣ��ʹ�ӡ�ɹ���ҵ����Ϣ
			
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
		// args[0] + Configuration.UNDERLINE + args[1] �ļ���f
		// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ��
		// args[0] + Configuration.UNDERLINE + args[1] �ļ���
		// ����Ĳ���Ӧ���� ��һ���û������� �ڶ���ʱ��SomeText.getInputFilesName(args[3], args[1])
		String[] otherArgs = { Configuration.INPUT_ROOT_PATH + Configuration.getInputFilesName(args[3], args[1]), // ��Ҫ�������ļ���·��
				Configuration.KELIULIANG_MAC_ROOT_PATH + Configuration.getUserPath(args[0])
						+ Configuration.getTimeThresholdPath(args[2]) + TimeStrUtil.changeTime2Path(args[1], args[2])
						+ Configuration.getFileName(args[3], args[1]), // �����õ���mac��Ϣ�����·��
				Configuration.KELIULIANG_TMP_PATH, // ��ʱ�ļ�
				Configuration.KELIULIANG_PEOPLE_ROOT_PATH+ args[0] + "/" + Configuration.getTimeThresholdPath(args[2])
						+ TimeStrUtil.changeTime2Path(args[1], args[2]) + Configuration.getFileName(args[3], args[1]) };

		Configuration conf = new Configuration();
		// ��һ��job������
		Job job1 = new Job(conf, "��ϴ��ҵ");
		job1.setJarByClass(KeLiuLiangJobLauncher.class);

		job1.setMapperClass(MacMergedMapper.class);
		job1.setReducerClass(MacMergedReducer.class);

		job1.setMapOutputKeyClass(Text.class);// map�׶ε������key
		job1.setMapOutputValueClass(IntWritable.class);// map�׶ε������value

		job1.setOutputKeyClass(Text.class);// reduce�׶ε������key
		job1.setOutputValueClass(IntWritable.class);// reduce�׶ε������value

		// �����������
		ControlledJob ctrljob1 = new ControlledJob(conf);
		ctrljob1.setJob(job1);
		// ���ж� job1 ���Ŀ¼�Ƿ����
		Path outputPath = new Path(otherArgs[1]);
		outputPath.getFileSystem(conf).delete(outputPath, true);
		// job1����������ļ�·��
		FileInputFormat.addInputPath(job1, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job1, new Path(otherArgs[1]));

		// �ڶ�����ҵ������
		Job job2 = new Job(conf, "������ҵ");
		job2.setJarByClass(KeLiuLiangJobLauncher.class);

		job2.setMapperClass(KeLiuLiangCountMapper.class);
		// job2.setReducerClass(Reduce_Second.class);

		job2.setMapOutputKeyClass(LongWritable.class);// map�׶ε������key
		job2.setMapOutputValueClass(IntWritable.class);// map�׶ε������value

		// job2.setOutputKeyClass(Text.class);//reduce�׶ε������key
		// job2.setOutputValueClass(IntWritable.class);//reduce�׶ε������value

		// ��ҵ2�����������
		ControlledJob ctrljob2 = new ControlledJob(conf);
		ctrljob2.setJob(job2);

		// ���ö����ҵֱ�ӵ�������ϵ
		// ������д��
		// ��˼Ϊjob2��������������job1��ҵ�����

		ctrljob2.addDependingJob(ctrljob1);
		// ���ж� job1 ���Ŀ¼�Ƿ����
		Path outputPath2 = new Path(otherArgs[2]);
		outputPath.getFileSystem(conf).delete(outputPath2, true);
		// ����·������һ����ҵ�����·�������������args[1],Ҫ�������Ӧ��
		FileInputFormat.addInputPath(job2, new Path(otherArgs[1]));

		FileOutputFormat.setOutputPath(job2, new Path(otherArgs[2]));

		// ���Ŀ�������������������ܵ���������ҵ
		JobControl jobCtrl = new JobControl("myctrl");

		// ��ӵ��ܵ�JobControl����п���
		jobCtrl.addJob(ctrljob1);
		jobCtrl.addJob(ctrljob2);

		// ���߳�����
		Thread t = new Thread(jobCtrl);
		t.start();

		while (true) {
			if (jobCtrl.allFinished()) {
				// �����ҵ�ɹ���ɣ��ʹ�ӡ�ɹ���ҵ����Ϣ
				System.out
						.println("Total num:" + job2.getCounters().findCounter(FileRecorder.TotalRecorder).getValue());
				System.out.println("reduce ����ĺǺǺ� num:" + job1.getCounters().findCounter(test.hehe).getValue());
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
