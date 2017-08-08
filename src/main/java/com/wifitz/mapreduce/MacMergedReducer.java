package com.wifitz.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

                                           
public class MacMergedReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
    public static enum FileRecorder{  
        TotalRecorder  
    }  
		@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;

			for (IntWritable value : values) {
				sum += value.get();
			}
			context.getCounter(FileRecorder.TotalRecorder).increment(1);
			context.write(key, new IntWritable(sum));
	}
	

	
}
