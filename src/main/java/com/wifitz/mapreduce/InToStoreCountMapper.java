package com.wifitz.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//  统计进店的量 以及  跳出率：进⼊店铺后很快离店的顾客及占比(占总体客流)
// 深访率：进⼊店铺深度访问的顾客及占⽐(占总体客流)
// 自定义阈值   很快就离开的 （3分钟） 中度（3分钟到10分钟）深度 （10分钟以上）
//  这个任务的输出依依赖于IntoStoreMapreduce的输出
public class InToStoreCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	// 访问店铺的情况  是匆匆就走的  还是 恋恋不舍的呢      total：总的进店人数     fast：很快就走    deep：深度逛街的人
    public static enum VisitLivenessRecorder{  
        TotalRecorder,
        FastGetOutRecorder,
        MidVisitRecorder,
        DeepVisitRecorder    
    }  
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//super.map(key, value, context);
		System.out.println(value.toString());
		String[] mValue = value.toString().split("\t");
		//String mKey = mValue[0]; // mac
		String time = mValue[1];
		if(mValue.length == 2) {
			if(Integer.parseInt(time) <= 300 ) {
				// 很快离开店铺的  
				context.getCounter(VisitLivenessRecorder.FastGetOutRecorder).increment(1);
			} else if (Integer.parseInt(time) > 300 && Integer.parseInt(time) <= 600){
				// 中度访问
				context.getCounter(VisitLivenessRecorder.MidVisitRecorder).increment(1);
			} else if(Integer.parseInt(time) > 600 && Integer.parseInt(time) < 10800){
				// 深度访问 10分钟到3个小时之内  （超过3个小时不符合常理）
				context.getCounter(VisitLivenessRecorder.DeepVisitRecorder).increment(1);
			}
		}
		context.getCounter(VisitLivenessRecorder.TotalRecorder).increment(1);
	}

}
