package com.roycom.linux.storage.phy;


public class SysPhy implements Phy {
	private String phyName;

	public SysPhy(String phyName){
		this.setPhyName(phyName);
	}
	
	@Override
	public void fillAttrs() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPhyName() {
		return phyName;
	}

	public void setPhyName(String phyName) {
		this.phyName = phyName;
	}

}
