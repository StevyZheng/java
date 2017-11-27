package com.roycom.linux.storage.disk;

import com.roycom.linux.LinuxCommon;

public class DiskFromLsiSas3 implements Disk {
	private String model;
	private String vendor;
	private String fw;
	private String sn;
	private String devName;
	private String smart;

	public DiskFromLsiSas3(String sn, String name) {
		// TODO Auto-generated constructor stub
		setSn(sn);
		setDevName(name);
	}
	
	@Override
	public void fillAttrs() throws Exception {
		// TODO Auto-generated method stub
		String smartStr = LinuxCommon.exeShell(String.format("smartctl -x /dev/%s", devName));
		setSmart(smartStr);
		fw = LinuxCommon.searchRegexString(smart, "^(Firmware|Revision).+", ":", 1).get(0).trim();
		vendor = LinuxCommon.searchRegexString(smart, "^(ATA|Vendor).+", ":", 1).get(0).trim();
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

}
