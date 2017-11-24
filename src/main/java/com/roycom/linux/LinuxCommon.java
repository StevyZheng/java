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
	 * exeShell static����������shell�������ִ�н��
	 * @param cmd shell�����ַ���
	 * @return ����ִ�н��
	 * @throws IOException ���ܳ��ֵ�IO error
	 * @throws InterruptedException �쳣
	 * @throws NullPointerException if an element of the command list is null
	 * @throws SecurityException Ȩ���쳣
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
	 * searchRegexString static��������Ŀ���ַ����а��������Ӵ��󣬷ָ�ȡ����һ�С�
	 * @param srcStr �������ַ���
	 * @param regexStr �����ַ���
	 * @param splitString �ָ��ַ��Ӵ���һ����ʹ��" *"���ָ����ո�
	 * @param column ��ʹ��������ʽ��ѯ����ַ����ָ��ĵ�column�и�ֵ��һ��ArrayList�Ķ���
	 * @return ���ز�ѯ���ָ���column�е��ַ������顣
	 * @throws IllegalArgumentException ������ʽ�ַ������Ϸ�
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
	 * ��ȡ��һ��ƥ��������ʽ���Ӵ�
	 * @param srcStr Դ�ַ���
	 * @param regexStr ������ʽ�ַ���
	 * @return ����ƥ����Ӵ�
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
	 * ����ƥ�䣬��ȡÿ�еĵ�һ��ƥ��������ʽ���Ӵ�
	 * @param srcStr Դ�ַ���
	 * @param regexStr ������ʽ�ַ���
	 * @return ����ÿ�еĵ�һ��ƥ��������ʽ���Ӵ�
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
	 * ��̬�������ַ���secStr�Ƿ����������ʽregStr�����ǵ��ִ�
	 * @param srcStr Դ�ַ���
	 * @param regStr �����ַ������ִ�
	 * @return ����boolean��
	 * @throws IllegalArgumentException ������ʽ�ַ������Ϸ�
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
	 * ���linuxϵͳ���õ��Ĺ����Ƿ���ȫ
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
	 * static���������ļ�
	 * @param path �ļ�·��
	 * @return �����ַ������
	 * @throws IOException ����IO error����FileInputStream���ļ�������
	 * @throws NullPointerException File���캯���Ĳ���pathΪnull������
	 * @throws SecurityException ϵͳ���ڰ�ȫ���ƣ�û�ж�ȡȨ��
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
	 * static�������������ļ��������ļ�
	 * @param path �ļ���·��
	 * @return ����ArrayList���͸��ļ������������ļ�
	 * @throws SecurityException listFiles����û�ж�ȡȨ�ޣ����ڰ�ȫ����
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
	 * static�������������ļ��г�Ŀ¼��������ļ�
	 * @param path �ļ���·��
	 * @return ����ArrayList���͸��ļ����������з�Ŀ¼�ļ�
	 * @throws SecurityException listFiles��isDirectory����û�ж�ȡȨ�ޣ����ڰ�ȫ����
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
