package com.wifitz.mapreduce;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.wifitz.mapreduce.InToStoreCountMapper.VisitLivenessRecorder;

/**
 * 统计 入店 有多少人  输入的key为mac value为时间的集合
 * @author 黄晟
 *
 */
public class InToStoreReducer extends Reducer<Text, Text, Text, LongWritable>{
	// 访问店铺的情况  是匆匆就走的  还是 恋恋不舍的呢      total：总的进店人数     fast：很快就走    deep：深度逛街的人
    public static enum VisitLivenessRecorder2{  
        TotalRecorder,
        FastGetOutRecorder,
        MidVisitRecorder,
        DeepVisitRecorder,
        KeLiuLiang
    }  
	// 时间格式  2017-05-20 05:12:28,39:A0:1B:8F:02:64,18,9
	private HashMap<String, Date> starttime = new HashMap<>(); // 客人进店的时间
	private HashMap<String, Date> endtime = new HashMap<>();  // 客人离开店铺的时间
    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, LongWritable>.Context context)
    		throws IOException, InterruptedException {
  
    	context.getCounter(VisitLivenessRecorder2.KeLiuLiang).increment(1);
    	
    	for(Text mValue : values) {
    		
    		if (  starttime.get(key.toString())==null || endtime.get(key.toString())==null    ) {
    			// 初始化时间
    			starttime.put(key.toString(), changeToDate(mValue.toString()));
    			endtime.put(key.toString(), changeToDate(mValue.toString()));
			}
    		String time = mValue.toString();
    		// mapper 传进的value是一个集合  里面有好多的时间 
    		// 初始化 进店时间 和 离店时间
    		if(  changeToDate(time).before(starttime.get(key.toString()))  ) {
    			starttime.replace(key.toString(),changeToDate(time) );
    		}
    		if(  changeToDate(time).after(endtime.get(key.toString()))  ) {
    			endtime.replace(key.toString(),changeToDate(time) );
    		}
    	}
    	// key（mac）     value (进店时间  离店时间   在路由器范围内停留了了多少秒)
    	LongWritable hangtime = howLongStayInstore(  starttime.get(key.toString()), 
                endtime.get(key.toString()));
    	
    	// 入店指标：在这里停留时间超过3分钟并且在有效的距离距离之内
    	if(Integer.parseInt(hangtime.toString()) >= 180) {
    		System.out.println("这个人在店里带了很久的时间  超过了3分钟"+ key.toString() +" 时长为："+ hangtime.toString());
    		context.write(key, howLongStayInstore(  starttime.get(key.toString()), 
                    endtime.get(key.toString())) );
    		context.getCounter(VisitLivenessRecorder2.TotalRecorder).increment(1);
    		if(Integer.parseInt(hangtime.toString()) <= 300 ) {
				// 很快离开店铺的  
				context.getCounter(VisitLivenessRecorder2.FastGetOutRecorder).increment(1);
			} else if (Integer.parseInt(hangtime.toString()) > 300 && Integer.parseInt(hangtime.toString()) <= 600){
				// 中度访问
				context.getCounter(VisitLivenessRecorder2.MidVisitRecorder).increment(1);
			} else if(Integer.parseInt(hangtime.toString()) > 600 && Integer.parseInt(hangtime.toString()) < 10800){
				// 深度访问 10分钟到3个小时之内  （超过3个小时不符合常理）
				context.getCounter(VisitLivenessRecorder2.DeepVisitRecorder).increment(1);
			}
    		
    		
    	}
    	
    		  	
    }
	/**
	 * 把字符串转换成 Date对象
	 * @param time
	 * @return
	 */
	public Date changeToDate(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			 date =  format.parse(time);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 
	 * @param start 进店的时间
	 * @param end 出去的时间
	 * @return 返回 再点店里的时长
	 */
	public LongWritable howLongStayInstore(Date start , Date end) {
		LongWritable time = new LongWritable();
	    time.set((end.getTime()-start.getTime())/1000);	  
		return time;
	}
		 
	
	

}
