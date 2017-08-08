package com.wifitz.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil 
{
	public static String convertInputStream2String(InputStream inStream) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		String data = null;
		boolean isFirst = true;
		while ((data = br.readLine()) != null) 
		{
			if (isFirst) 
			{
				sb.append(data);
				isFirst = false;
			}
			else 
			{
				sb.append("\n" + data);
			}
		}
		
		return sb.toString();
	}
	
	private StreamUtil() {}
}
