package com.roycom.linux.storage.controller;

import java.util.ArrayList;

public class LsiMegaRaidController implements Controller {
	private String model;
	private String fw;
	private String sn;
	private ArrayList<String> disks;

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

	public ArrayList<String> getDisks() {
		return disks;
	}

	public void setDisks(ArrayList<String> disks) {
		this.disks = disks;
	}

}
