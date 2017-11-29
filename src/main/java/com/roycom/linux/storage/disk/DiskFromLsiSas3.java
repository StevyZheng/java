package com.roycom.linux.storage.disk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.roycom.linux.LinuxCommon;

public class DiskFromLsiSas3 implements Disk {
	private String model;
	private String vendor;
	private String fw;
	private String sn;
	private String devName;
	private String smart;
	private String type;
	private Map<String, Map<String, String>> smartAttr;
	

	public DiskFromLsiSas3(String sn, String name) {
		// TODO Auto-generated constructor stub
		setSn(sn);
		setDevName(name);
	}
	
	@Override
	public void fillAttrs() throws Exception {
		// TODO Auto-generated method stub
		String smartStr = LinuxCommon.exeShell(String.format("smartctl -a /dev/%s", devName));
		String smartxStr = LinuxCommon.exeShell(String.format("smartctl -x /dev/%s", devName));
		setSmart(smartxStr);
		fw = LinuxCommon.searchRegexString(smart, "^(Firmware|Revision).+", ":", 1).get(0).trim();
		vendor = LinuxCommon.searchRegexString(smart, "^(ATA|Vendor).+", ":", 1).get(0).trim();
		sn = LinuxCommon.searchRegexString(smartStr, "^Serial (N|n)umber.+", ":", 1).get(0).trim();
		if("ATA" == vendor){
			ArrayList<String> smartStrArr = LinuxCommon.getMatchSubStrings(smartStr, "^( |[0-9])+.+[0-9]+ .+0x.+(In_the_past|-|FAILING_NOW) +[0-9]+");
			for(String str: smartStrArr){
				String[] tmp = str.split(" +");
				Map<String, String> = new HashMap<String, String>();
			}
		}
		
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getFw() {
		return fw;
	}

	public void setFw(String fw) {
		this.fw = fw;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSmart() {
		return smart;
	}

	public void setSmart(String smart) {
		this.smart = smart;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
