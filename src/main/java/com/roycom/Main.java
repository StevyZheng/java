package com.roycom;

import java.util.ArrayList;

import com.roycom.linux.LinuxCommon;
import com.roycom.linux.storage.controller.Common;
import com.roycom.linux.storage.controller.Controller;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			ArrayList<Controller> str = Common.scanController();
			for(int i=0;i<str.size();i++){
				LinuxCommon.println(String.format("%s  %s", str.get(i).getModel(), str.get(i).getIndex()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}