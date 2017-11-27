package com.roycom.linux.storage.phy;

import com.roycom.linux.LinuxCommon;

public class SysPhy implements Phy {
	private String phyName;
	private String invalid_dword_count;
	private String loss_of_dword_sync_count;
	private String phy_reset_problem_count;
	private String running_disparity_error_count;
	private String sas_address;

	public SysPhy(String phyName){
		this.setPhyName(phyName);
	}
	
	@Override
	public void fillAttrs() throws Exception {
		// TODO Auto-generated method stub
		String invalidStr = String.format("/sys/class/sas_phy/%s/invalid_dword_count", getPhyName());
		String lossStr = String.format("/sys/class/sas_phy/%s/loss_of_dword_sync_count", getPhyName());
		String resetStr = String.format("/sys/class/sas_phy/%s/phy_reset_problem_count", getPhyName());
		String disparityStr = String.format("/sys/class/sas_phy/%s/running_disparity_error_count", getPhyName());
		String sasAddrStr = String.format("/sys/class/sas_phy/%s/sas_address", getPhyName());
		invalid_dword_count = LinuxCommon.readFileByChar(invalidStr);
		loss_of_dword_sync_count = LinuxCommon.readFileByChar(lossStr);
		phy_reset_problem_count = LinuxCommon.readFileByChar(resetStr);
		running_disparity_error_count = LinuxCommon.readFileByChar(disparityStr);
		sas_address = LinuxCommon.readFileByChar(sasAddrStr);
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

	public String getInvalid_dword_count() {
		return invalid_dword_count;
	}

	public void setInvalid_dword_count(String invalid_dword_count) {
		this.invalid_dword_count = invalid_dword_count;
	}

	public String getLoss_of_dword_sync_count() {
		return loss_of_dword_sync_count;
	}

	public void setLoss_of_dword_sync_count(String loss_of_dword_sync_count) {
		this.loss_of_dword_sync_count = loss_of_dword_sync_count;
	}

	public String getPhy_reset_problem_count() {
		return phy_reset_problem_count;
	}

	public void setPhy_reset_problem_count(String phy_reset_problem_count) {
		this.phy_reset_problem_count = phy_reset_problem_count;
	}

	public String getRunning_disparity_error_count() {
		return running_disparity_error_count;
	}

	public void setRunning_disparity_error_count(String running_disparity_error_count) {
		this.running_disparity_error_count = running_disparity_error_count;
	}

	public String getSas_address() {
		return sas_address;
	}

	public void setSas_address(String sas_address) {
		this.sas_address = sas_address;
	}

}
