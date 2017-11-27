package com.roycom.linux.storage.phy;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roycom.linux.LinuxCommon;

public class Common {
	public static ArrayList<String> scanSysPhy(){
		ArrayList<String> sysPhys = LinuxCommon.listDirAllFiles("/sys/class/sas_phy");
		return sysPhys;
	}
	
	public static ArrayList<SysPhy> scanSysPhys() throws Exception{
		ArrayList<SysPhy> tmp = new ArrayList<SysPhy>();
		for(String str: scanSysPhy()){
			SysPhy p = new SysPhy(str);
			p.fillAttrs();
			tmp.add(p);
		}
		return tmp;
	}
	
	public static String phyErrorJson() throws Exception{
		ArrayList<SysPhy> phys = new ArrayList<SysPhy>();
		for(String str: scanSysPhy()){
			SysPhy p = new SysPhy(str);
			p.fillAttrs();
			if(Integer.parseInt(p.getInvalid_dword_count()) > 0 || Integer.parseInt(p.getLoss_of_dword_sync_count()) > 0 || Integer.parseInt(p.getPhy_reset_problem_count()) > 0 || Integer.parseInt(p.getRunning_disparity_error_count()) > 0)
				phys.add(p);
		}
		ObjectMapper mapper = new ObjectMapper();
		String physJsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(phys);
		return physJsonStr;
	}
	
	public static String physToJson() throws Exception{
		ArrayList<SysPhy> phys = scanSysPhys();
		ObjectMapper mapper = new ObjectMapper();
		String physJsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(phys);
		return physJsonStr;
	}
}
