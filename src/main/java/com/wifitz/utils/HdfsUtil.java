package com.wifitz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;





public class HdfsUtil {
	
	private static Configuration configuration = new Configuration();
	private static Logger logger = Logger.getLogger(HdfsUtil.class);
	
    /**
     * 
     * @param date 时间 chen_2017_5_12
     * @return
     * @throws IOException
     */
	public static boolean MergeSmallFilesToBigFiles(String date) throws IOException
	{
		
			System.out.println("the date is:"+date);
		    Configuration conf = new Configuration();
	        FileSystem fs = FileSystem.get(conf);
	        FSDataOutputStream out = null;
	        FSDataInputStream in = null;

	        //PathFilter是过滤布符合置顶表达式的路径，下列就是把以匹配的 2017_5_24.11 的文件
	        FileStatus[] status = fs.globStatus(new Path("hdfs://119.29.91.155:8020/wifitzlogs/usr/wifitzlogs/*"),
	        									new RegexExcludePathFilter(date)); // 根据日期来过滤文件

	        // input path 
	        Path[] listedPaths = FileUtil.stat2Paths(status);
	        if (listedPaths == null) {
				return false;
			}
	        // output path
	        Path block  = new Path("/input/"+date+".log.all");
	        out = fs.create(block);
	        for (Path p : listedPaths) {
	        	System.out.println(p);
	        	in = fs.open(p);
	        	IOUtils.copyBytes(in, out, 4096, false);
	        	in.close();
	        }
		return true;
	}
	
	public static void updateToHDFS(String filePath, String resultStr) {
		System.out.println("************************"+filePath);
		OutputStream out = null;

		try 
		{
			FileSystem fs = FileSystem.get(configuration);
			Path path = new Path(filePath);
			out  = fs.create(path, true);	
			out.write(resultStr.getBytes());
		} 
		catch (Exception e) 
		{
			//put to log
			logger.error("write data " + resultStr + " to file " + filePath + " exception.", e);
		}
		finally
		{
			if(null != out)
			{
				try
				{
					out.close();
				}
				catch(Exception e)
				{
					//put to log
					logger.error("close file path " + filePath + " exception.", e);
				}
			}
		}
	}
	public static void appendToHDFS(String filePath, String resultStr) {
		System.out.println("path:"+filePath+"    string:"+resultStr);
		OutputStream out = null;

		try {
			FileSystem fs = FileSystem.get(configuration);
			Path path = new Path(filePath);

			if (fs.exists(path)) {
				out = fs.append(path);
			} else {
				out = fs.create(path);
			}

			out.write(resultStr.getBytes());
			out.write("\n".getBytes());
		} catch (Exception e) {
			// put to log

		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (Exception e) {
					// put to log

				}
			}
		}
	}
	
	public static String getFileContent(String filePath)
	{
		InputStream in = null;
		String fileDataStr = "";
		
		try
		{
			FileSystem fs = FileSystem.get(configuration);
			Path path = new Path(filePath);
			
			if(!fs.exists(path))
			{
				return fileDataStr;
			}
			
			in = fs.open(path);
			fileDataStr = StreamUtil.convertInputStream2String(in);
		} 
		catch (Exception e) 
		{
			//put to log
			logger.error("read file " + filePath + " exception.", e);
		}
		finally
		{
			if(null != in)
			{
				try
				{
					in.close();
				}
				catch(Exception e)
				{
					//put to log
					logger.error("close file path " + filePath + " exception.", e);
				}
				
			}
		}
		
		return fileDataStr;
	}
}
