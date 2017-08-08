package com.wifitz.mapreduce;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 统计 入店 有多少人
 * @author 黄晟
 *
 */
public class InToStoreMapper extends Mapper<LongWritable, Text, Text, Text>{
	private Text mkey = new Text();
	private Text mValue = new Text();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// 2017-05-21 17:04:27,da:a1:19:c4:2e:90,17.782795,-84
		 String[] spilt = value.toString().split(",");
		 if(spilt.length>=2) {
			 String time = spilt[0];
			 String mac = spilt[1]; 
			 mkey.set(mac);
			 mValue.set(time);
			  // key是mac value是时间戳    
			 context.write(mkey, mValue);
		 } 
	}
	


}
