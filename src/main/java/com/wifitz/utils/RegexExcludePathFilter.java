package com.wifitz.utils;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * 获取需要的文件   例如 文件中必须包含  2017_5_24.12 字段的文件
 * @author 黄晟
 *
 */
class RegexExcludePathFilter implements PathFilter{
    private final String regex;
    /**
     * 
     * @param regex 必须包含的字段
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