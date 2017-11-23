package com.roycom.linux.storage.disk;

public class DiskFromLsiSas2 implements Disk {
	private String model;
	private String vendor;
	private String fw;
	private String sn;
	private String smart;
	
	@Override
	public void fillAttrs() {
		// TODO Auto-generated method stub

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

}
