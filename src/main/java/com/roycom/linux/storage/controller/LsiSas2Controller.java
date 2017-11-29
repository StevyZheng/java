package com.roycom.linux.storage.controller;

import java.util.ArrayList;

import com.roycom.linux.LinuxCommon;
import com.roycom.linux.storage.disk.Disk;
import com.roycom.linux.storage.disk.DiskFromLsiSas2;

public class LsiSas2Controller implements Controller {
	private String model;
	private int index;
	private String vendor;
	private String fw;
	private ArrayList<Disk> disks;
	
	public LsiSas2Controller(int index, String model) throws Exception {
		// TODO Auto-generated constructor stub
		if(model.matches("SAS[0-9]{4}"))
			setModel(model);
		else{
			throw new Exception("LsiSas2Controller model string is not available.");
		}
		setVendor("LSI");
		setIndex(index);
	}

	@Override
	public void fillAttrs() throws Exception {
		// TODO Auto-generated method stub
		String sas2ircuString = LinuxCommon.exeShell(String.format("sas2ircu %s display", index));
		String fwStr = LinuxCommon.getMatchSubString(sas2ircuString, "Firmware.*([0-9]+\\.)+[0-9]*");
		String[] tmp = fwStr.split(":");
		String tmpStr = tmp[1].trim();
		if(!tmpStr.isEmpty()){
			setFw(tmpStr);
		}else{
			setFw("null");
		}
		ArrayList<String> diskSn = LinuxCommon.searchRegexString(sas2ircuString, "^ +Serial No.*([a-z]|[A-Z]|[0-9])+", ":", 2);
		for(String s: diskSn){
			disks.add(new DiskFromLsiSas2(s));
		}
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	@Override
	public String getFw() {
		return fw;
	}

	public void setFw(String fw) {
		this.fw = fw;
	}

	@Override
	public ArrayList<Disk> getDisks() {
		return disks;
	}

	public void setDisks(ArrayList<Disk> disks) {
		this.disks = disks;
	}

	@Override
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
