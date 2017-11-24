package com.roycom.linux.storage.controller;

import java.util.ArrayList;

import com.roycom.linux.storage.disk.Disk;

public interface Controller {
	void fillAttrs() throws Exception;
	String toJson();
	String getModel();
	ArrayList<Disk> getDisks();
	int getIndex();
	String getFw();
	String getVendor();
}
