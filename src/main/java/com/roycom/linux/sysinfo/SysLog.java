package com.roycom.linux.sysinfo;

import java.util.ArrayList;

import com.roycom.linux.LinuxCommon;

public class SysLog {
	public static ArrayList<String> getDmesgError() throws Exception{
		String regStr = ".*(ECC|MCE).*";
		//String txt = FileUtils.readFileToString(new File(args[0]));
		String srcStr = LinuxCommon.exeShell("dmesg");
		ArrayList<String> dmesgError = new ArrayList<String>();
		dmesgError = LinuxCommon.searchRegexStringFromFile(srcStr, regStr);
		return dmesgError;
	}
	
	public static ArrayList<String> getMessagesError() throws Exception{
		String regStr = ".*(ECC|MCE).*";
		ArrayList<String> dmesgError = new ArrayList<String>();
		dmesgError = LinuxCommon.searchRegexStringFromFile("/var/log/messages", regStr);
		return dmesgError;
	}
}
