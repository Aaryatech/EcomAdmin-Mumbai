package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.HomeController;
import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.City;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.CompanyContactInfo;
import com.ats.ecomadmin.model.DeliverySlots;
import com.ats.ecomadmin.model.ExportToExcel;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;


//Akhilesh 2020-12-31
@Controller
@Scope("session")
public class DeliverySlotController {
	
	
	DeliverySlots editdelSlot=new DeliverySlots();
	
	@RequestMapping(value="/showDelSlotList",method=RequestMethod.GET)
	public ModelAndView showDelSlotList(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model=new ModelAndView("DeliverySlotList");
		
		List<DeliverySlots> delSlotList=new ArrayList<>();
		HttpSession session = request.getSession();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showDelSlotList", "showDelSlotList", "1", "0", "0", "0", newModuleList);
			
			if (view.isError() == true) {

				model = new ModelAndView( "accessDenied");

			}else {
				int compId = (int) session.getAttribute("companyId");
				model.addObject("compId", compId);
				
				DeliverySlots[] delSlotArr=Constants.getRestTemplate().getForObject(Constants.url+"getAllDeliverySlots", DeliverySlots[].class);
				delSlotList=new ArrayList<>(Arrays.asList(delSlotArr));
				model.addObject("delSlotList", delSlotList);
				
				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr No.");													
				rowData.add("Delivery Slot Name");		
				rowData.add("From Time");
				rowData.add("To Time");
				rowData.add("Status");
				expoExcel.setRowData(rowData);
				
				exportToExcelList.add(expoExcel);
				int srno = 1;
				for (int i = 0; i < delSlotList.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					
					rowData.add(" "+srno);
					rowData.add(" " + delSlotList.get(i).getDeliverySlotName());	
					rowData.add(" " + delSlotList.get(i).getFromTime());
					rowData.add(" " + delSlotList.get(i).getToTime());
					rowData.add(delSlotList.get(i).getIsActive() == 1 ? "Active" : "In-Active");					
					srno = srno + 1;
					
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}
				session.setAttribute("exportExcelListNew", exportToExcelList);
				session.setAttribute("excelNameNew", "Delivery Slot");
				session.setAttribute("reportNameNew", "Delivery Slot List");
				session.setAttribute("searchByNew", " NA");
				session.setAttribute("mergeUpto1", "$A$1:$L$1");
				session.setAttribute("mergeUpto2", "$A$2:$L$2");

				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "Delivery Slot Excel");
				
				
				
				Info add = AccessControll.checkAccess("showDelSlotList", "showDelSlotList", "0", "1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showDelSlotList", "showDelSlotList", "0", "0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showDelSlotList", "showDelSlotList", "0", "0", "0", "1",
						newModuleList);
				
				
				if (add.isError() == false) {
					model.addObject("addAccess", 1);
				}
				if (edit.isError() == false) {
					model.addObject("editAccess", 1);
				}
				if (delete.isError() == false) {
					model.addObject("deleteAccess", 1);
				}
				
			}
			
			} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered In /showDelSlotList");
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	
	@RequestMapping(value="/AdddeliverySlot",method=RequestMethod.GET)
	public ModelAndView AdddeliverySlot() {
		ModelAndView model=new ModelAndView("addDeliverySlot");
		model.addObject("title", "Add  Delivery Slot ");
		try {
			
			DeliverySlots delSlot=new DeliverySlots();
			delSlot.setDeliSlotId(0);
			int flag=0;
			model.addObject("delSlot", delSlot);
			model.addObject("flag", flag);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered In /AdddeliverySlot");
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	
	@RequestMapping(value="/submitAddDelSlot",method=RequestMethod.POST)
	public ModelAndView submitAddDelSlot(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model=new ModelAndView("redirect:/showDelSlotList");
		HttpSession session=request.getSession();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		DeliverySlots dSlot=new DeliverySlots();
		
		try {
			
			User userObj = (User) session.getAttribute("userObj");
			CompMaster company = (CompMaster) session.getAttribute("company");

			int delSlotId=Integer.parseInt(request.getParameter("deliSlotId"));
			String  delslotName=request.getParameter("delslotName");
			String fromTime=request.getParameter("fromTime");
			String toTime=request.getParameter("toTime");
			int  isActive= Integer.parseInt(request.getParameter("radioConfig"));
			
			System.err.println(sf.format(dt));
			System.err.println("User Detail"+userObj+"isActive=="+isActive);
			System.err.println("Company Detail"+company);
			
			
			
			if (delSlotId==0) {
				System.err.println("New Del Slot");
				dSlot.setDeliverySlotName(delslotName);
				dSlot.setFromTime(fromTime);
				dSlot.setToTime(toTime);
				dSlot.setCompanyId(company.getCompanyId());
				dSlot.setMakerUserId(userObj.getUserId());
				dSlot.setDelStatus(1);
				dSlot.setIsActive(isActive);
				dSlot.setInsertDttime(sf.format(dt));
				dSlot.setUpdtDttime(sf.format(dt));
				
				DeliverySlots res=Constants.getRestTemplate().postForObject(Constants.url+"addDeliverySlots", dSlot, DeliverySlots.class);
				
			}else {
				System.err.println("Old Del Slot");
				editdelSlot.setDeliverySlotName(delslotName);
				editdelSlot.setFromTime(fromTime);
				editdelSlot.setToTime(toTime);
				editdelSlot.setCompanyId(company.getCompanyId());
				editdelSlot.setMakerUserId(userObj.getUserId());
				editdelSlot.setDelStatus(1);
				editdelSlot.setIsActive(isActive);
				editdelSlot.setUpdtDttime(sf.format(dt));
				
				DeliverySlots res=Constants.getRestTemplate().postForObject(Constants.url+"addDeliverySlots", editdelSlot, DeliverySlots.class);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered In /submitAddDelSlot");
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	
	
	@RequestMapping(value="/editdeliverySlot",method=RequestMethod.GET)
	public ModelAndView editdeliverySlot(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model=new ModelAndView("addDeliverySlot");
		model.addObject("title", "Edit  Delivery Slot ");
		MultiValueMap<String, Object> map =new LinkedMultiValueMap<>();
 		int flag=1;
		try {
			int delSlotId=Integer.parseInt(request.getParameter("delSlotId"));
			map.add("delSlotId", delSlotId);
			editdelSlot=Constants.getRestTemplate().postForObject(Constants.url+"getDeliverySlotById", map, DeliverySlots.class);
			model.addObject("delSlot", editdelSlot);
			model.addObject("flag", flag);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered In /editdeliverySlot");
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	
	@RequestMapping(value="/deletedeliverySlot",method=RequestMethod.GET)
	public ModelAndView deletedeliverySlot(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model=new ModelAndView("redirect:/showDelSlotList");
		Info info=new Info();
		MultiValueMap<String, Object> map =new LinkedMultiValueMap<>();
 		int flag=1;
		try {
			int delSlotId=Integer.parseInt(request.getParameter("delSlotId"));
			map.add("delSlotId", delSlotId);
			info=Constants.getRestTemplate().postForObject(Constants.url+"DelteDeliSlots", map, Info.class);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered In /deletedeliverySlot");
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping(value = "pdf/getDelvrSlotListPdf/{compId}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getProductConfigPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable int compId, @PathVariable int showHead) {
		ModelAndView model = new ModelAndView("pdfs/delvrSlotPdf");
		try {
			DeliverySlots[] delSlotArr=Constants.getRestTemplate().getForObject(Constants.url+"getAllDeliverySlots", DeliverySlots[].class);
			List<DeliverySlots> delSlotList=new ArrayList<>(Arrays.asList(delSlotArr));
			
			model.addObject("delSlotList", delSlotList);
			CompanyContactInfo dtl = HomeController.getCompName(compId);
			if(showHead==1) {
				model.addObject("compName", dtl.getCompanyName());
				model.addObject("compAddress", dtl.getCompAddress());
				model.addObject("compContact", dtl.getCompContactNo());	
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
		
	}

	

}
