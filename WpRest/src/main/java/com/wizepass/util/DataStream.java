package com.wizepass.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataStream {
	/**
	 *TODO: java doc
	 **/
	public String readInputStream(InputStream jsonObj) throws IOException{
		final BufferedReader reader = new BufferedReader(new InputStreamReader(jsonObj, "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();    
	    while ((line = reader.readLine()) != null) {	    	       	
	        sb.append(line).append("\n");         	        
	    }  
	    reader.close();
	    return sb.toString();
	}
}
