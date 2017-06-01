package com.wizepass.util;

import java.util.List;
import java.util.Map;

import com.wizepass.controller.DataController;

public class test {

	public static void main(String[] args) {
		DataController controller = new DataController();
		List<Map<String,Object>> users= controller.getUsers();
		System.out.println(users);
	

	}

}
