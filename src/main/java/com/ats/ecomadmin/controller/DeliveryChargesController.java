package com.ats.ecomadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.DeliveryCharges;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
public class DeliveryChargesController {
	
	
	
		// Delivery Charges
		@RequestMapping(value = "/showDeliveryChargesList", method = RequestMethod.GET)
		public ModelAndView showDeliveryChargesList(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			List<DeliveryCharges> chargeList = new ArrayList<DeliveryCharges>();
			try {
				
				HttpSession session = request.getSession();
				List<ModuleJson> newModuleList = (List<ModuleJson>)session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("showDeliveryChargesList", "showDeliveryChargesList", "1", "0", "0", "0",
						newModuleList);

				if (view.isError() == true) {

					model = new ModelAndView("accessDenied");
				} else {
					
					model = new ModelAndView("delvrChargList");
					model.addObject("title", "Delivery Charges List");

					//HttpSession session = request.getSession();
					User userDetail = (User) session.getAttribute("UserDetail");

//					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//					map.add("compId", userDetail.getUser().getCompanyId());

					DeliveryCharges[] chargeArr = Constants.getRestTemplate()
							.getForObject(Constants.url + "getAllDeliveryCharges", DeliveryCharges[].class);
					chargeList = new ArrayList<DeliveryCharges>(Arrays.asList(chargeArr));

					for (int i = 0; i < chargeList.size(); i++) {

						chargeList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(chargeList.get(i).getChId())));
					}
					model.addObject("chargeList", chargeList);
				}
				
			} catch (Exception e) {
				System.out.println("Execption in /showDeliveryChargesList : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}
		
		
		
		@RequestMapping(value = "/addDeliveryCharge", method = RequestMethod.GET)
		public ModelAndView addDeliveryCharge(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView model = null;
			try {
				model = new ModelAndView("addDelCharge");
				DeliveryCharges delCharge = new DeliveryCharges();

				model.addObject("delCharge", delCharge);

				model.addObject("title", "Add Delivery Charge");
			} catch (Exception e) {
				System.out.println("Execption in /addDeliveryCharge : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
			@RequestMapping(value = "/saveDeliveryCharge", method = RequestMethod.POST)
		public String saveDeliveryCharge(HttpServletRequest request, HttpServletResponse response) {
			try {
				HttpSession session = request.getSession();
				User userDetail = (User) session.getAttribute("UserDetail");

				DeliveryCharges delCharge = new DeliveryCharges();

				int chargeId = Integer.parseInt(request.getParameter("charge_id"));

				delCharge.setChId(chargeId);
				delCharge.setDelStatus(0);
				delCharge.setGroupName(request.getParameter("group_name"));
				delCharge.setMaxKm(Float.parseFloat(request.getParameter("max_km")));
				delCharge.setMinKm(Float.parseFloat(request.getParameter("min_km")));
				delCharge.setAmt1(Float.parseFloat(request.getParameter("amt1")));
				delCharge.setAmt2(Float.parseFloat(request.getParameter("amt2")));
				delCharge.setExInt1(0);
				delCharge.setExInt2(0);
				delCharge.setExVar1("NA");
				delCharge.setExVar2("NA");
				delCharge.setExFloat1(0);
				delCharge.setExFloat2(0);

				DeliveryCharges addDelCharge = Constants.getRestTemplate()
						.postForObject(Constants.url + "addNewDeliveryCharge", delCharge, DeliveryCharges.class);

				if (addDelCharge.getChId() > 0) {
					if (chargeId == 0)
						session.setAttribute("successMsg", "Delivery Charges Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Delivery Charges Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Delivery Charges");
				}
			} catch (Exception e) {
				System.out.println("Execption in /saveDeliveryCharge : " + e.getMessage());
				e.printStackTrace();
			}

			return "redirect:/showDeliveryChargesList";
		}
			
			
			
			
			@RequestMapping(value = "/editDeliveryCharge", method = RequestMethod.GET)
			public ModelAndView editDeliveryCharge(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;
				try {
					model = new ModelAndView("addDelCharge");

					HttpSession session = request.getSession();
					User userDetail = (User) session.getAttribute("UserDetail");

					String base64encodedString = request.getParameter("chargeId");
					String chargeId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("chargeId", Integer.parseInt(chargeId));

					DeliveryCharges delCharge = Constants.getRestTemplate().postForObject(Constants.url + "getDeliveryChargeById",
							map, DeliveryCharges.class);
					model.addObject("delCharge", delCharge);

					model.addObject("title", "Edit Delivery Charge");
				} catch (Exception e) {
					System.out.println("Execption in /editDeliveryCharge : " + e.getMessage());
					e.printStackTrace();
				}
				return model;
			}
			
			
			@RequestMapping(value = "/deleteDeliveryCharge", method = RequestMethod.GET)
			public String deleteDeliveryCharge(HttpServletRequest request, HttpServletResponse response) {

				HttpSession session = request.getSession();
				try {

					String base64encodedString = request.getParameter("chargeId");
					String chargeId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("chargeId", Integer.parseInt(chargeId));

					Info info = Constants.getRestTemplate().postForObject(Constants.url + "/deleteDeliverChargeById", map,
							Info.class);

					if (!info.isError()) {
						session.setAttribute("successMsg", info.getMsg());
					} else {
						session.setAttribute("errorMsg", info.getMsg());
					}

				} catch (Exception e) {
					System.out.println("Execption in /deleteDeliveryCharge : " + e.getMessage());
					e.printStackTrace();
				}
				return "redirect:/showDeliveryChargesList";
			}


	
	
		
		
		
	
	
	
	
	
	
	

}
