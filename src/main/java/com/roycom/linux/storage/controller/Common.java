package com.roycom.linux.storage.controller;

import java.util.ArrayList;

import com.roycom.linux.LinuxCommon;

public class Common {
	public static ArrayList<String> scanController() throws Exception{
		String lspci = LinuxCommon.exeShell("lspci|grep LSI");
		ArrayList<String> controllerSubStrs = LinuxCommon.getMatchSubStrings(lspci, "SAS *[0-8]{4}");
		for(int i=0;i<controllerSubStrs.size();i++){
			String tmp = controllerSubStrs.get(i).replaceAll(" ", "");
			controllerSubStrs.remove(i);
			controllerSubStrs.add(i, tmp);
		}
		return controllerSubStrs;
	}
}
