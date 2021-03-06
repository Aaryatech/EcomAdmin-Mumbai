package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.annotation.SessionScope;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.GetTableFields;
import com.ats.ecomadmin.model.GetTableNames;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@SessionScope
public class CopyTableController {

	
	
	

	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 23-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :- copy table
	@RequestMapping(value = "/showCopyTable", method = RequestMethod.GET)
	public String showCopyTable(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		HttpSession session = request.getSession();
 		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showCopyTable", "showCopyTable", "1", "0", "0", "0", newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
		model.addAttribute("title", "Copy Table ");
		mav = "masters/copyTable";
		int compId = (int) session.getAttribute("companyId");

		CompMaster userObj = (CompMaster) session.getAttribute("company");
		String tblName = null;


		if (userObj.getParentCompId() > 0) {
			try {

				List<GetTableFields> tblList1 = new ArrayList<>();
				List<String> fieldList = new ArrayList<>();

				try {
					tblName = request.getParameter("tableId");
				} catch (Exception e1) {
					tblName = null;
				}
				model.addAttribute("tblName", tblName);
				/*
				 * try { tblId = Integer.parseInt(request.getParameter("tableId")); } catch
				 * (Exception e) { tblId = 0; } System.err.println("tblId" + tblId);
				 */
				GetTableNames[] tagArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllTables",
						GetTableNames[].class);
				List<GetTableNames> tblList = new ArrayList<GetTableNames>(Arrays.asList(tagArr));

				model.addAttribute("tblList", tblList);

				if (tblName != null) {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

					map.add("tbl_name", tblName);

					GetTableFields[] tblFieldrr = Constants.getRestTemplate().postForObject(Constants.url + "getTableData",
							map, GetTableFields[].class);
					tblList1 = new ArrayList<GetTableFields>(Arrays.asList(tblFieldrr));


					map = new LinkedMultiValueMap<>();
					map.add("tbl_name", tblName);

					String[] fieldArr = Constants.getRestTemplate().postForObject(Constants.url + "getCopyTbl", map,
							String[].class);
					fieldList = new ArrayList<String>(Arrays.asList(fieldArr));
					fieldList.remove(0);


				}
				model.addAttribute("datalist", tblList1);
				model.addAttribute("fieldList", fieldList);

				model.addAttribute("temp", fieldList.size());

			} catch (Exception e) {
				System.out.println("Execption in /showUomList : " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			session.setAttribute("errMsg", "Copy Table Functionality Not Applicable For Parent Company");
		}
		}
		return mav;
	}
	
	
	/*--------------------------------------------------------------------------------*/
	// Created By :- Harsha Patil
	// Created On :- 23-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Descriprion :-submit copy table

	@RequestMapping(value = "/submitCopyTable", method = RequestMethod.POST)
	public String submitCopyTable(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
	 
		String curDateTime = CommonUtility.getCurrentYMDDateTime();
		User userObj = (User) session.getAttribute("userObj");
		String tblName=null;
		String mav = new String();
		
		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showCopyTable", "showCopyTable", "1", "0", "0", "0", newModuleList);

		if (view.isError() == true) {

			mav = "accessDenied";

		} else {
		try {
			  tblName = request.getParameter("tblName");
			  
			  
			  mav="redirect:/showCopyTable?tableId="+tblName;
			StringBuilder sb = new StringBuilder();
			int compId = (int) session.getAttribute("companyId");
			String[] primId = request.getParameterValues("primId");


			for (int i = 0; i < primId.length; i++) {
				sb = sb.append(primId[i] + ",");
			}

			String items = sb.toString();

			items = items.substring(0, items.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("tbl_name", tblName);
			map.add("primaryIds", items);
			map.add("compId", compId);
			map.add("curDateTime", curDateTime);
			map.add("userId", userObj.getUserId());

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/insertCopyTableRec", map, Info.class);
			if (!info.isError()) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return mav;

	}

}
