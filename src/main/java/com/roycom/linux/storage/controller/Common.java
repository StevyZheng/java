package com.roycom.linux.storage.controller;

import java.util.ArrayList;
import com.roycom.linux.LinuxCommon;

public class Common {
	public static ArrayList<Controller> scanController() throws Exception{
		String sas2Con = LinuxCommon.exeShell("sas2ircu list|grep -P 'SAS[0-9]{4}'");
		String sas3Con = LinuxCommon.exeShell("sas3ircu list|grep -P 'SAS[0-9]{4}'");
		String megaRaidCon = LinuxCommon.exeShell("storcli show|grep LSI");
		
		ArrayList<Controller> cons = new ArrayList<Controller>();
		ArrayList<String> indexs = null;
		ArrayList<String> conStr = null;
		if(!sas2Con.isEmpty()){
			indexs = LinuxCommon.searchRegexString(sas2Con, ".*SAS[0-9]{4}.*", " ", 0);
			conStr = LinuxCommon.getMatchSubStrings(sas2Con, "SAS[0-9]{4}");
			for(int i=0;i<indexs.size();i++){
				LsiSas2Controller c = new LsiSas2Controller(Integer.parseInt(indexs.get(i)), conStr.get(i));
				cons.add(c);
			}
		}
		if(!sas3Con.isEmpty()){
			indexs = LinuxCommon.searchRegexString(sas3Con, ".*SAS[0-9]{4}.*", " ", 0);
			conStr = LinuxCommon.getMatchSubStrings(sas3Con, "SAS[0-9]{4}");
			for(int i=0;i<indexs.size();i++){
				//ArrayList<String> tmp = new ArrayList<String>();
				//tmp.add(indexs.get(i));
				//tmp.add(conStr.get(i));
				//result.add(tmp);
				LsiSas3Controller c = new LsiSas3Controller(Integer.parseInt(indexs.get(i)), conStr.get(i));
				cons.add(c);
			}
		}
		if(!megaRaidCon.isEmpty()){
			indexs = LinuxCommon.searchRegexString(megaRaidCon, ".*SAS[0-9]{4}.*", " ", 0);
			conStr = LinuxCommon.getMatchSubStrings(megaRaidCon, "SAS[0-9]{4}");
			for(int i=0;i<indexs.size();i++){
				LsiMegaRaidController c = new LsiMegaRaidController(Integer.parseInt(indexs.get(i)), conStr.get(i));
				cons.add(c);
			}
		}
		return cons;
	}
	
	public static ArrayList<ArrayList<String>> scanSystemFromSas23DiskNameAndSn() throws Exception{
		ArrayList<ArrayList<String>> disks = new ArrayList<ArrayList<String>>();
		String tmp = LinuxCommon.exeShell("lsblk -o NAME,SERIAL,VENDOR");
		ArrayList<String> names = LinuxCommon.searchRegexString(tmp, "^sd[a-z]+ +([a-z]|[A-Z]|[0-9]| )+", " ", 0);
		ArrayList<String> sns = LinuxCommon.searchRegexString(tmp, "^sd[a-z]+ +([a-z]|[A-Z]|[0-9]| )+", " ", 1);
		ArrayList<String> vendors = LinuxCommon.searchRegexString(tmp, "^sd[a-z]+ +([a-z]|[A-Z]|[0-9]| )+", " ", 2);
		for(int i=0;i<names.size();i++){
			ArrayList<String> attr = new ArrayList<String>();
			attr.add(names.get(i));
			attr.add(sns.get(i));
			attr.add(vendors.get(i));
			disks.add(attr);
		}
		return disks;
	}
	
}
