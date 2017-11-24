package com.roycom;

import java.util.ArrayList;

import com.roycom.linux.storage.controller.Common;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			ArrayList<String> str = Common.scanController();
			System.out.print(str.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}