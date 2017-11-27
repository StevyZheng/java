package com.roycom.linux.storage.controller;

import java.util.ArrayList;

import com.roycom.linux.LinuxCommon;
import com.roycom.linux.storage.disk.Disk;
import com.roycom.linux.storage.disk.DiskFromLsiSas2;

public class LsiMegaRaidController implements Controller {
	private String model;
	private int index;
	private String vendor;
	private String fw;
	private String cacheSize;
	private ArrayList<Disk> disks;
	
	public LsiMegaRaidController(int index, String model) throws Exception {
		// TODO Auto-generated constructor stub
		if(model.matches("SAS[0-9]{4}"))
			setModel(model);
		else{
			throw new Exception("LsiMegaRaidController model string is not available.");
		}
		setVendor("LSI");
		setIndex(index);
	}

	@Override
	public void fillAttrs() throws Exception {
		// TODO Auto-generated method stub
		String megaRaidString = LinuxCommon.exeShell(String.format("storcli /c%s show", index));
		String fwStr = LinuxCommon.getMatchSubString(megaRaidString, "FW Version.*([0-9]+(\\.|-))+[0-9]*");
		String[] tmp = fwStr.split("=");
		String tmpStr = tmp[1].trim();
		if(!tmpStr.isEmpty()){
			setFw(tmpStr);
		}else{
			setFw("null");
		}
		ArrayList<String> diskSn = LinuxCommon.searchRegexString(megaRaidString, "^ +Serial No.*([a-z]|[A-Z]|[0-9])+", ":", 2);
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

	public String getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(String cacheSize) {
		this.cacheSize = cacheSize;
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
