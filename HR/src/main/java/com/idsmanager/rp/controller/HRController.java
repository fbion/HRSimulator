package com.idsmanager.rp.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.idsmanager.rp.dao.HRMongoDao;
import com.idsmanager.rp.domain.Department;
import com.idsmanager.rp.domain.EmpHR;
import com.idsmanager.rp.util.ExportExcel;
import com.idsmanager.rp.util.FileUploadUtils;
import com.idsmanager.rp.util.HttpRequestMethodUtils;
import com.idsmanager.rp.util.PropertyUtils;
import com.idsmanager.rp.util.bean.ProcessResult;

@Controller("HRController")
public class HRController {
	@Autowired
	private HRMongoDao hrDao;

	/**
	 * 添加机构thinkpad dushaofei
	 * 
	 * @param user
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value = "/hr/user/add", method = RequestMethod.POST)
	@ResponseBody
	public ProcessResult<String> addOauthUser(@RequestBody Department dept) throws ClientProtocolException, IOException {
		ProcessResult<String> pr = new ProcessResult<String>();
		hrDao.saveDept(dept);
		boolean when  = pushUD(dept);
		if(!when){
			pr.setSuccess(false);
			pr.setDetail("编辑成功,但向UD推送失败");
			return pr;
		}
		pr.setDetail("编辑成功,并且已推送到UD");
		return pr;
	}
	/**
	 *向UD推送数据
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean pushUD(Department dept) throws ClientProtocolException, IOException{
		ProcessResult<Boolean> pr = new ProcessResult<Boolean>();
		String requstUrl = PropertyUtils.getDeptAddUrl();
		HttpUriRequest request = new HttpPost(requstUrl);
		JSONObject obj = new JSONObject();
		obj.accumulate("text", dept.getText());
		obj.accumulate("leader", dept.getLeader());
		obj.accumulate("leaderCode", dept.getLeaderCode());
		obj.accumulate("fax", dept.getFax());
		obj.accumulate("id", dept.getId());
		obj.accumulate("telephone", dept.getTelephone());
		StringEntity entity = new StringEntity(obj.toString(),HTTP.UTF_8);
		System.out.println(obj.toString());
		((HttpPost)request).setEntity(entity);
		request.addHeader("content-type","application/json;charset=utf-8");
		JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(request);
		pr = (ProcessResult<Boolean>) jsonObj.toBean(jsonObj, pr.getClass());
		return pr.getSuccess();
	}
	
	/**
	 * 查询全部机构
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hr/user/all")
	@ResponseBody
	public Map<String, Object> getAllApplication(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Department> list = null;
		try {
			list = hrDao.getUserList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("aaData", list);
		return map;
	}
	/**
	 * 部门删除
	 * 
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value = "/hr/user/delete/{deptId}", method = RequestMethod.GET)
	@ResponseBody
	public ProcessResult<String> userDelete(@PathVariable String[] deptId) throws ClientProtocolException, IOException {
		ProcessResult<String> pr = new ProcessResult<String>();
		for (String string : deptId) {
			hrDao.deleteUser(string);
		}
		boolean when = pushToUDdelete(deptId);
		//推送到UD
		if(!when){
			pr.setDetail("删除失败");
		}
		pr.setDetail("删除成功,已推送到UD");
		return pr;
	}
	/**
	 * 将删除信息推送到UD
	 * @param deptId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Boolean pushToUDdelete(String[] deptId) throws ClientProtocolException, IOException{
		ProcessResult<Boolean> pr= new ProcessResult<Boolean>();
		String requstUrl = PropertyUtils.getDeptDeleteUrl();
		HttpUriRequest request = new HttpPost(requstUrl);
		JSONObject obj = new JSONObject();
		obj.accumulate("deleteIds", deptId);
		StringEntity entity = new StringEntity(obj.toString(),HTTP.UTF_8);
		System.out.println(obj.toString());
		((HttpPost)request).setEntity(entity);
		request.addHeader("content-type","application/json;charset=utf-8");
		JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(request);
		pr = (ProcessResult<Boolean>) jsonObj.toBean(jsonObj, pr.getClass());
		return pr.getSuccess();
	}
	/**
	 * 编辑机构
	 * @param deptId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/hr/user/toEdit/{deptId}", method = RequestMethod.GET)
	public ModelAndView userdetail(@PathVariable String deptId) throws ClientProtocolException, IOException {
		ModelAndView view = new ModelAndView();
		view.setViewName("deptEdit.jsp");
		Department dept = hrDao.getUserByDeptID(deptId);
		view.addObject("dept", dept);
		return view;
	}
	/**
	 * 添加用户
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/hr/public/user/add", method = RequestMethod.GET)
	public ModelAndView userAdd() throws ClientProtocolException, IOException {
		ModelAndView view = new ModelAndView();
		List<Department> dept = hrDao.getUserList();
		view.addObject("dept", dept);
		view.setViewName("empAdd.jsp");
		return view;
	}
	
	/**
	 * 添加员工
	 * 
	 * @param user
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value = "/hr/user/push/add", method = RequestMethod.POST)
	@ResponseBody
	public ProcessResult<String> addpushUser(@RequestBody EmpHR emp) throws ClientProtocolException, IOException {
		ProcessResult<String> pr = new ProcessResult<String>();
		hrDao.saveEmp(emp);//TODO
		if(emp.getIspush() == 0){
			boolean when  = pushAddUser(emp);
			if(!when){
				pr.setSuccess(false);
				pr.setDetail("编辑成功,但向UD推送失败");
				return pr;
			}
		}
		pr.setDetail("编辑成功,并且已推送到UD,您的测试账号是员工姓名,密码是Idsmanager2015");
		return pr;
	}
	public boolean pushAddUser(EmpHR emp) throws ClientProtocolException, IOException{
		ProcessResult<Boolean> pr = new ProcessResult<Boolean>();
		String requstUrl = PropertyUtils.getEmpAddUrl();
		HttpUriRequest request = new HttpPost(requstUrl);
		JSONObject obj = new JSONObject();
		obj.accumulate("id", emp.getId());
		obj.accumulate("username", emp.getUsername());
		obj.accumulate("dept", emp.getDept());
		obj.accumulate("enumber", emp.getEnumber());
		obj.accumulate("state", emp.getState());
		obj.accumulate("edate", emp.getEdate());
		StringEntity entity = new StringEntity(obj.toString(),HTTP.UTF_8);
		System.out.println(obj.toString());
		((HttpPost)request).setEntity(entity);
		request.addHeader("content-type","application/json;charset=utf-8");
		JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(request);
		pr = (ProcessResult<Boolean>) jsonObj.toBean(jsonObj, pr.getClass());
		return pr.getSuccess();
	}
	/**
	 * 查询全部用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hr/user/emp/all")
	@ResponseBody
	public Map<String, Object> getAlluser(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<EmpHR> list = null;
		try {
			list = hrDao.getEmpList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("aaData", list);
		return map;
	}
	/**
	 * 用户离职
	 * 
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value = "/hr/user/emp/delete/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ProcessResult<String> userEmpDelete(@PathVariable String[] userId) throws ClientProtocolException, IOException {
		ProcessResult<String> pr = new ProcessResult<String>();
		for (String string : userId) {
			hrDao.deleteEmp(string);
		}//TODO
		boolean when = pushEmpTOUD(userId);
		//推送到UD
		if(!when){
			pr.setDetail("离职失败");
		}
		pr.setDetail("离职成功,已推送到UD");
		return pr;
	}
	/**
	 * 编辑用户
	 * @param deptId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/hr/user/emp/toEdit/{userId}", method = RequestMethod.GET)
	public ModelAndView userEmpdetail(@PathVariable String userId) throws ClientProtocolException, IOException {
		ModelAndView view = new ModelAndView();
		view.setViewName("empEdit.jsp");
		EmpHR emp = hrDao.getEmpByID(userId);
		List<Department> dept = hrDao.getUserList();
		view.addObject("dept", dept);
		view.addObject("emp", emp);
		return view;
	}
	/**
	 * 将离职信息推送到UD
	 * @param deptId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Boolean pushEmpTOUD(String[] userId) throws ClientProtocolException, IOException{
		ProcessResult<Boolean> pr= new ProcessResult<Boolean>();
		String requstUrl = PropertyUtils.getEmptDeleteUrl();
		HttpUriRequest request = new HttpPost(requstUrl);
		JSONObject obj = new JSONObject();
		obj.accumulate("deleteIds", userId);
		StringEntity entity = new StringEntity(obj.toString(),HTTP.UTF_8);
		System.out.println(obj.toString());
		((HttpPost)request).setEntity(entity);
		request.addHeader("content-type","application/json;charset=utf-8");
		JSONObject jsonObj = HttpRequestMethodUtils.getJsonObject(request);
		pr = (ProcessResult<Boolean>) jsonObj.toBean(jsonObj, pr.getClass());
		return pr.getSuccess();
	}
	/**
	 * 导出excel
	 * @param response
	 * author dushaofei
	 * @return
	 */
	@RequestMapping(value = "/manager/v1/employee/user/excel", method = RequestMethod.POST)
	@ResponseBody
	public ProcessResult<String> excelToUser(HttpServletResponse response) {
		 response.setContentType("application/x-msdownload");
		ProcessResult<String> pr = new ProcessResult<String>();
		
       ExportExcel<Department> ex = new ExportExcel<Department>();
        String[] headers =  
        { "编号", "机构名称", "机构负责人", "电话", "负责人工号" ,"传真"};  
        List<Department> dataset = hrDao.getUserList(); 
        try  
        {  
        	FileSystemView fsv = FileSystemView.getFileSystemView();
        	Date date = new Date();
        	DateFormat da = new SimpleDateFormat("yyyyMMddHHmmss");
            OutputStream out = new FileOutputStream(fsv.getHomeDirectory()+"//机构列表"+da.format(date)+".xls");  
            ex.exportExcel(headers, dataset, out);  
            out.close();  
//            JOptionPane.showMessageDialog(null, "导出成功,已存储电脑桌面!");  
            System.out.println("excel导出成功！");  
        }  
        catch (FileNotFoundException e)  
        {  
            pr.setErrorMessage("导出数据异常,请核对数据是否正常!");
        }  
        catch (IOException e)  
        {  
        	pr.setErrorMessage("导出数据异常,请核对数据是否正常!");
            e.printStackTrace();  
        }  
		return pr;
	}
	
	/**
	 * 去上传页面
	 * @param userId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/hr/file/tofile", method = RequestMethod.GET)
	public ModelAndView tofile() throws ClientProtocolException, IOException {
		ModelAndView view = new ModelAndView();
		view.setViewName("file.jsp");
		return view;
	}
	/**
	 * 导入数据
	 * thinkpad dushaofei
	 * @param file
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/fileUpload/fileToIdp")
	public String fileToIdp(@RequestParam(value="file",required=false)MultipartFile file,Model model,HttpServletRequest request) throws Exception{
		// String path = request.getSession().getServletContext().getRealPath("fileToIdp");  
		 request.setCharacterEncoding("UTF-8");
		 if(file.getOriginalFilename()!=null){
			 InputStream is= file.getInputStream();
			 int i = 0;
             InputStreamReader isr = new InputStreamReader(is,"UTF-8");
             
              BufferedReader br=new BufferedReader(isr); 

              String linemes;
              while((linemes=br.readLine())!=null ){
            	  
            	 if(i==0){
            		  i++;
            	  }else{
            		  System.out.println(linemes);
            		  Department dept = FileUploadUtils.getDeptrByString(linemes);
            		  if(null!=dept){
            			  Department de =  hrDao.getDeptByLeaderCode(dept.getLeaderCode());
            			  if(de==null){
            				  hrDao.saveDept(dept);
            			  }
            		  }
            	  }
              }
             br.close();
             isr.close();
             is.close();
		 }
		return "redirect:/deptList.jsp";
	}
	/**
	 * 给UD提供调用获取Emp的接口
	 * thinkpad dushaofei
	 * @return
	 */
	@RequestMapping(value="/hr/to/ud",method=RequestMethod.POST)
	@ResponseBody
	public 	ProcessResult<List<EmpHR>> getUserListToUD(){
		ProcessResult<List<EmpHR>> pr = new ProcessResult<List<EmpHR>>();
		List<EmpHR> listHr = hrDao.getEmpList();
		pr.setDetail(listHr);
		return pr;
	}
	
	/**
	 * 员工转正push到UD
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value = "/hr/public/user/become/{enumber}")
	@ResponseBody
	public ProcessResult<String> userBecome(@PathVariable String enumber){
		ProcessResult<String> pr = new ProcessResult<String>();
		ProcessResult<Boolean> come = new ProcessResult<Boolean>();
		String requstUrl = PropertyUtils.getEmpBecomeUrl() + "/"+enumber;
//		String requestUrl = "http://localhost/UserDirectory/manger/v1/emp/emptype/"+enumber;
		HttpGet get = new HttpGet(requstUrl);
		try {
			JSONObject obj = HttpRequestMethodUtils.getJsonObject(get);
			come = (ProcessResult<Boolean>) obj.toBean(obj,pr.getClass());
			if(come.getSuccess()){
				pr.setDetail("转正成功,一同步到UD");
				return pr;
			}
		} catch ( IOException e) {
			e.printStackTrace();
			pr.setSuccess(false);
			pr.setDetail("转正异常！");
			return pr;
		}
		pr.setDetail("没有这个员工.");
		return pr;
	}
}
















