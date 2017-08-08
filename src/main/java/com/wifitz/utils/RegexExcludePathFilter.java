package com.wifitz.utils;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * ��ȡ��Ҫ���ļ�   ���� �ļ��б������  2017_5_24.12 �ֶε��ļ�
 * @author ����
 *
 */
class RegexExcludePathFilter implements PathFilter{
    private final String regex;
    /**
     * 
     * @param regex ����������ֶ�
     */
    public RegexExcludePathFilter(String regex) {
        this.regex = regex;
    }
    
    @Override
    public boolean accept(Path path) {
        boolean res = false;  
        if(path.toString().indexOf(regex) != -1){  
            res = true;  
        }     
        return res;    	
    }
}