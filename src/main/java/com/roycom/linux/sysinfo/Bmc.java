package com.roycom.linux.sysinfo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.roycom.linux.LinuxCommon;

public class Bmc {
	private String bmcIp;
	private String bmcMac;
	private String sel;
	
	public void fillAttrs() throws Exception{
		String ipmiStr = LinuxCommon.exeShell("ipmicfg -m");
		setBmcIp(LinuxCommon.searchRegexString(ipmiStr, "^IP=([0-9]|\\.)*", "=", 1).get(0));
		setBmcMac(LinuxCommon.searchRegexString(ipmiStr, "^MAC=.*", "=", 1).get(0));
		String selStr = LinuxCommon.exeShell("ipmicfg -sel list");
		ArrayList<ArrayList<String>> selArr = new ArrayList<ArrayList<String>>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(selStr.getBytes(Charset.forName("UTF-8"))), Charset.forName("UTF-8")));
		String line;
		int i = 0;
		ArrayList<String> arr = null;
		while((line = br.readLine()) != null){
			arr = new ArrayList<String>();
			arr.add(line);
			if(i % 2 == 0){
				selArr.add(arr);
			}
			i++;
		}
	}
	
	public String getBmcIp() {
		return bmcIp;
	}
	public void setBmcIp(String bmcIp) {
		this.bmcIp = bmcIp;
	}
	public String getBmcMac() {
		return bmcMac;
	}
	public void setBmcMac(String bmcMac) {
		this.bmcMac = bmcMac;
	}
	public String getSel() {
		return sel;
	}
	public void setSel(String sel) {
		this.sel = sel;
	}
	
	
}
