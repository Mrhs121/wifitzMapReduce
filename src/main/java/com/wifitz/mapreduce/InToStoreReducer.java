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
 * ͳ�� ��� �ж�����  �����keyΪmac valueΪʱ��ļ���
 * @author ����
 *
 */
public class InToStoreReducer extends Reducer<Text, Text, Text, LongWritable>{
	// ���ʵ��̵����  �ǴҴҾ��ߵ�  ���� �����������      total���ܵĽ�������     fast���ܿ����    deep����ȹ�ֵ���
    public static enum VisitLivenessRecorder2{  
        TotalRecorder,
        FastGetOutRecorder,
        MidVisitRecorder,
        DeepVisitRecorder,
        KeLiuLiang
    }  
	// ʱ���ʽ  2017-05-20 05:12:28,39:A0:1B:8F:02:64,18,9
	private HashMap<String, Date> starttime = new HashMap<>(); // ���˽����ʱ��
	private HashMap<String, Date> endtime = new HashMap<>();  // �����뿪���̵�ʱ��
    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, LongWritable>.Context context)
    		throws IOException, InterruptedException {
  
    	context.getCounter(VisitLivenessRecorder2.KeLiuLiang).increment(1);
    	
    	for(Text mValue : values) {
    		
    		if (  starttime.get(key.toString())==null || endtime.get(key.toString())==null    ) {
    			// ��ʼ��ʱ��
    			starttime.put(key.toString(), changeToDate(mValue.toString()));
    			endtime.put(key.toString(), changeToDate(mValue.toString()));
			}
    		String time = mValue.toString();
    		// mapper ������value��һ������  �����кö��ʱ�� 
    		// ��ʼ�� ����ʱ�� �� ���ʱ��
    		if(  changeToDate(time).before(starttime.get(key.toString()))  ) {
    			starttime.replace(key.toString(),changeToDate(time) );
    		}
    		if(  changeToDate(time).after(endtime.get(key.toString()))  ) {
    			endtime.replace(key.toString(),changeToDate(time) );
    		}
    	}
    	// key��mac��     value (����ʱ��  ���ʱ��   ��·������Χ��ͣ�����˶�����)
    	LongWritable hangtime = howLongStayInstore(  starttime.get(key.toString()), 
                endtime.get(key.toString()));
    	
    	// ���ָ�꣺������ͣ��ʱ�䳬��3���Ӳ�������Ч�ľ������֮��
    	if(Integer.parseInt(hangtime.toString()) >= 180) {
    		System.out.println("������ڵ�����˺ܾõ�ʱ��  ������3����"+ key.toString() +" ʱ��Ϊ��"+ hangtime.toString());
    		context.write(key, howLongStayInstore(  starttime.get(key.toString()), 
                    endtime.get(key.toString())) );
    		context.getCounter(VisitLivenessRecorder2.TotalRecorder).increment(1);
    		if(Integer.parseInt(hangtime.toString()) <= 300 ) {
				// �ܿ��뿪���̵�  
				context.getCounter(VisitLivenessRecorder2.FastGetOutRecorder).increment(1);
			} else if (Integer.parseInt(hangtime.toString()) > 300 && Integer.parseInt(hangtime.toString()) <= 600){
				// �жȷ���
				context.getCounter(VisitLivenessRecorder2.MidVisitRecorder).increment(1);
			} else if(Integer.parseInt(hangtime.toString()) > 600 && Integer.parseInt(hangtime.toString()) < 10800){
				// ��ȷ��� 10���ӵ�3��Сʱ֮��  ������3��Сʱ�����ϳ���
				context.getCounter(VisitLivenessRecorder2.DeepVisitRecorder).increment(1);
			}
    		
    		
    	}
    	
    		  	
    }
	/**
	 * ���ַ���ת���� Date����
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
	 * @param start �����ʱ��
	 * @param end ��ȥ��ʱ��
	 * @return ���� �ٵ�����ʱ��
	 */
	public LongWritable howLongStayInstore(Date start , Date end) {
		LongWritable time = new LongWritable();
	    time.set((end.getTime()-start.getTime())/1000);	  
		return time;
	}
		 
	
	

}
