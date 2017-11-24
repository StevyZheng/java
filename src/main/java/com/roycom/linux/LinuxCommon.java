package com.roycom.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinuxCommon {
	/**
	 * exeShell static函数，运行shell命令，返回执行结果
	 * @param cmd shell命名字符串
	 * @return 返回执行结果
	 * @throws IOException 可能出现的IO error
	 * @throws InterruptedException 异常
	 * @throws NullPointerException if an element of the command list is null
	 * @throws SecurityException 权限异常
	 */
	public static String exeShell(String cmd) throws Exception {
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		p.waitFor();
		InputStream is =  p.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        StringBuilder esb = new StringBuilder();
        InputStream eis = p.getErrorStream();
        InputStreamReader eisr = new InputStreamReader(eis);
        BufferedReader ebr = new BufferedReader(eisr);
        while(true){
            String line = br.readLine();
            if(line == null){
                break;
            }
            
            if(line.trim().length()>0){
                sb.append(line);
                sb.append("\n");
            }
        }
        br.close();
        isr.close();
        is.close();
        
        while(true){
            String eline = ebr.readLine();
            if(eline==null){
                break;
            }
            if(eline.trim().length()>0){
                esb.append(eline);
                esb.append("\n");
            }
        }
        ebr.close();
        eisr.close();
        eis.close();
        
        String ssb = sb.toString().trim();
        String sesb = esb.toString().trim();
        if(0 == sesb.length()){
        	return ssb;
        }else{
        	return sesb;
        }
	}
	
	/**
	 * searchRegexString static函数，在目标字符串中按行搜索子串后，分割取其中一列。
	 * @param srcStr 待查找字符串
	 * @param regexStr 正则字符串
	 * @param splitString 分割字符子串，一般是使用" *"来分割多个空格
	 * @param column 将使用正则表达式查询后的字符串分割后的第column列赋值给一个ArrayList的对象
	 * @return 返回查询并分割后第column列的字符串数组。
	 * @throws IllegalArgumentException 正则表达式字符串不合法
	 */
	public static ArrayList<String> searchRegexString(String srcStr, String regexStr, String splitString, int column) throws IllegalArgumentException {
		ArrayList<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regexStr, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(srcStr);
		while(matcher.find()){
			String tmp = matcher.group().trim();
			String[] listA = tmp.split(String.format("%s*", splitString));
			result.add(listA[column].trim());
		}
		return result;
	}
	
	/**
	 * 获取第一个匹配正则表达式的子串
	 * @param srcStr 源字符串
	 * @param regexStr 正则表达式字符串
	 * @return 返回匹配的子串
	 */
	public static String getMatchSubString(String srcStr, String regexStr){
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(srcStr);
		if(matcher.find()){
			return matcher.group(1);
		}else{
			return null;
		}
	}
	
	/**
	 * 按行匹配，获取每行的第一个匹配正则表达式的子串
	 * @param srcStr 源字符串
	 * @param regexStr 正则表达式字符串
	 * @return 返回每行的第一个匹配正则表达式的子串
	 */
	public static ArrayList<String> getMatchSubStrings(String srcStr, String regexStr){
		ArrayList<String> subStrs = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regexStr, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(srcStr);
		while(matcher.find()){
			String tmp = matcher.group().trim();
			subStrs.add(tmp);
		}
		return subStrs;
	}
	
	/**
	 * 静态函数，字符串secStr是否包含正则表达式regStr所涵盖的字串
	 * @param srcStr 源字符串
	 * @param regStr 正则字符串，字串
	 * @return 返回boolean型
	 * @throws IllegalArgumentException 正则表达式字符串不合法
	 */
	public static boolean matches(String srcStr, String regStr) throws IllegalArgumentException{
		Pattern pattern = Pattern.compile(regStr, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(srcStr);
		if(matcher.find()){
			return true;
		}else{
			return false;
		}
	}
	
	public static String RemoveStringBlank(String str){
		return str.replaceAll(" ", "");
	}
	
	/**
	 * 检测linux系统中用到的工具是否齐全
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NullPointerException 
	 */
	public static boolean checkEnvironmentTools(ArrayList<String> tools) throws Exception{
		String cmd = "which";
		boolean flag = true;
		String result = "Tools status:\n";
		for(String tool: tools){
			cmd = String.format("%s %s", cmd, tool);
		}
		String[] re = LinuxCommon.exeShell(cmd).trim().split("\n");
		for(String i: re){
			if(LinuxCommon.matches(i, " no ")){
				flag = false;
				result = String.format("%s\n%s",result, i);
			}
		}
		if(!flag){
			System.out.print(result);
		}
		return flag;
	}
	
	/**
	 * static函数，读文件
	 * @param path 文件路径
	 * @return 返回字符串结果
	 * @throws IOException 出现IO error或者FileInputStream中文件不存在
	 * @throws NullPointerException File构造函数的参数path为null，出错
	 * @throws SecurityException 系统存在安全限制，没有读取权限
	 */
	public static String readFileByChar(String path) throws Exception{
		String buf = null;
		byte[] data = null;
		File file = new File(path);
		InputStream in = null;
		try{
		in = new FileInputStream(file);
		data = new byte[1024];
		in.read(data);
		}catch(IOException|SecurityException e){
			throw e;
		}finally {
			in.close();
		}
		buf = new String(data).trim();
		return buf;
	}
	
	/**
	 * static函数，遍历根文件夹所有文件
	 * @param path 文件夹路径
	 * @return 返回ArrayList类型根文件夹内任所有文件
	 * @throws SecurityException listFiles函数没有读取权限，存在安全限制
	 */
	public static ArrayList<String> listDirAllFiles(String path) throws SecurityException{
		ArrayList<String> fileList = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		for(File f: files){
			fileList.add(f.getName());
		}
		return fileList;
	}
	
	/**
	 * static函数，遍历根文件夹除目录外的所有文件
	 * @param path 文件夹路径
	 * @return 返回ArrayList类型根文件夹内任所有非目录文件
	 * @throws SecurityException listFiles或isDirectory函数没有读取权限，存在安全限制
	 */
	public static ArrayList<String> listDirNomalFiles(String path) throws SecurityException{
		ArrayList<String> fileList = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		for(File f: files){
			if(f.isDirectory())
				fileList.add(f.getName());
		}
		return fileList;
	}
	
	public static String pathJoin(String path, String nameStr){
		return String.format("%s%s%s", path, java.io.File.separator, nameStr);
	}
	
}
