package com.idsmanager.rp.util;
import com.idsmanager.rp.domain.Department;




public class FileUploadUtils {
	public static Department getDeptrByString(String str){
		String[] strs = str.split(",");
		Department u = new Department();
		u.setText(strs[0]);
		u.setLeader(strs[1]);
		u.setLeaderCode(strs[2]);
		u.setTelephone(strs[3]);
		u.setFax(strs[4]);
		return u;
	}
}
