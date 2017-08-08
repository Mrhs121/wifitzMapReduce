package com.wifitz.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**
 * 用于清洗log日志 合并mac地址
 * @author 黄晟
 *
 */
public class MacMergedMapper extends  Mapper<LongWritable, Text, Text, IntWritable> {
	private IntWritable one = new IntWritable(1);
	//private Text k = new Text();
	private Text word = new Text();

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String[] spilt = value.toString().split(",");
		// System.out.println("log--------------" + a);
		if (spilt.length >= 2) {
			word.set(spilt[1]);
			context.write(word, one);
		}
	}
}
