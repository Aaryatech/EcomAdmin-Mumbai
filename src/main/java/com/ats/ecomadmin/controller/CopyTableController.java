package com.ats.ecomadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.context.annotation.SessionScope;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.GetTableFields;
import com.ats.ecomadmin.model.GetTableNames;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@SessionScope
public class CopyTableController {

	@RequestMapping(value = "/showCopyTable", method = RequestMethod.GET)
	public String showCopyTable(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();

			model.addAttribute("title", "Copy Table ");
			mav = "masters/copyTable";
			int compId = (int) session.getAttribute("companyId");
			String tblName = null;

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

				GetTableFields[] tblArr = Constants.getRestTemplate().postForObject(Constants.url + "getTableData",map,
						GetTableFields[].class);
				tblList1 = new ArrayList<GetTableFields>(Arrays.asList(tblArr));

				System.err.println("tblName" + tblName);

				map = new LinkedMultiValueMap<>();
				map.add("tbl_name", tblName);

				String[] fieldArr = Constants.getRestTemplate().postForObject(Constants.url + "getCopyTbl", map,
						String[].class);
				fieldList = new ArrayList<String>(Arrays.asList(fieldArr));
				System.err.println("fieldList" + fieldList.toString());

			}
			model.addAttribute("datalist", tblList1);
			model.addAttribute("fieldList", fieldList);
			
			model.addAttribute("temp", fieldList.size());

		} catch (Exception e) {
			System.out.println("Execption in /showUomList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

}
