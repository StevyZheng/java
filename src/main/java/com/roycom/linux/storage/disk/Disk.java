package com.roycom.linux.storage.disk;

public interface Disk {
	void fillAttrs() throws Exception;
	String toJson();
}
