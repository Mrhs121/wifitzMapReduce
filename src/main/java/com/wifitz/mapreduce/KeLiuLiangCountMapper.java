package com.wifitz.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**
 * 用于统计mac的数量也就是客流量
 * @author 黄晟
 *
 */
public class KeLiuLiangCountMapper extends Mapper<LongWritable, Text, LongWritable, IntWritable>{
	
    public static enum FileRecorder2{  
        TotalRecorder  
    }  
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, LongWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//super.map(key, value, context);
		context.getCounter(FileRecorder2.TotalRecorder).increment(1);
	}

}
