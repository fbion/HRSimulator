package com.idsmanager.rp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class PropertyUtils {
	
	public static String getDeptAddUrl(){
		InputStream in = PropertyUtils.class.getResourceAsStream("/rp/hrToUdUrl.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		String value =  (String) p.get("hr_to_ud_dept_edit");
		
		return value;
	}
	
	public static String getDeptDeleteUrl(){
		InputStream in = PropertyUtils.class.getResourceAsStream("/rp/hrToUdUrl.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		String value =  (String) p.get("hr_to_ud_dept_delete");
		
		return value;
	}
	public static String getEmpAddUrl(){
		InputStream in = PropertyUtils.class.getResourceAsStream("/rp/hrToUdUrl.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		String value =  (String) p.get("hr_to_ud_emp_edit");
		
		return value;
	}
	public static String getEmptDeleteUrl(){
		InputStream in = PropertyUtils.class.getResourceAsStream("/rp/hrToUdUrl.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		String value =  (String) p.get("hr_to_ud_emp_delete");
		
		return value;
	}
	public static String getEmpBecomeUrl(){
		InputStream in = PropertyUtils.class.getResourceAsStream("/rp/hrToUdUrl.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		String value =  (String) p.get("hr_ud_emp_become");
		
		return value;
	}
}
