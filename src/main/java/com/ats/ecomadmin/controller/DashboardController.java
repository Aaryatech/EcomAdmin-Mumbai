// Created By :- Mahendra Singh
// Created On :- 06-10-2020
// Modified By :- NA
// Modified On :- NA
// Description :- Dashboard

package com.ats.ecomadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.GetDashPieStatusCnt;
import com.ats.ecomadmin.model.GetOrderHeaderDisplay;

@Controller
@Scope("session")
public class DashboardController {
	Date date = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	String fromDate = "";
	String toDate = "";
	List<GetDashPieStatusCnt> dashDataList = new ArrayList<GetDashPieStatusCnt>();

	@RequestMapping(value = "/orderDashboard", method = RequestMethod.GET)
	public String orderDashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			
			map.add("fromDate", sf.format(date));
			map.add("toDate", sf.format(date));
			map.add("compId", compId);

			dashDataList.clear();

			GetDashPieStatusCnt[] dashDataArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllStatusCount", map, GetDashPieStatusCnt[].class);
			dashDataList = new ArrayList<GetDashPieStatusCnt>(Arrays.asList(dashDataArr));

			fromDate = CommonUtility.convertToDMY(sf.format(date));
			toDate = CommonUtility.convertToDMY(sf.format(date));
			
			for (int i = 0; i < dashDataList.size(); i++) {
				dashDataList.get(i).setFromDate(fromDate);
				dashDataList.get(i).setToDate(toDate);
			}
			
			System.out.println("DashBoard Data----------->" + dashDataList);

			model.addAttribute("statusCnt", dashDataList);
		} catch (Exception e) {
			System.out.println("Exc in /orderDashboard : " + e.getMessage());
			e.printStackTrace();
		}
		return "orderDash";
	}

	@RequestMapping(value = "/getDashCountData", method = RequestMethod.GET)
	public String getAllSubCategoryAjax(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<GetDashPieStatusCnt> dashDataList = new ArrayList<GetDashPieStatusCnt>();
		try {

			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			int radVal = Integer.parseInt(request.getParameter("calRadio"));			

			if (radVal == 1) {

				String currDate = sf.format(date);
				map.add("fromDate", currDate);
				map.add("toDate", currDate);
				map.add("compId", compId);

				fromDate = currDate;
				toDate = currDate;

				// System.err.println("Day------------>" + fromDate+" "+toDate);
			} else if (radVal == 2) {

				Calendar calendar = Calendar.getInstance();
				while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					calendar.add(Calendar.DATE, -1);
				}
				Date d = calendar.getTime();
				fromDate = sf.format(d);

				Calendar calendar2 = Calendar.getInstance();
				while (calendar2.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					calendar2.add(Calendar.DATE, 1);
				}
				Date d2 = calendar2.getTime();
				toDate = sf.format(d2);

				// System.err.println("Week------------>" + fromDate+" "+toDate);
				map.add("fromDate", fromDate);
				map.add("toDate", toDate);
				map.add("compId", compId);

			} else if (radVal == 3) {

				Date begining, end;

				Calendar calendar = getCalendarForNow();
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
				setTimeToBeginningOfDay(calendar);
				begining = calendar.getTime();
				fromDate = sf.format(begining);

				Calendar calendar1 = getCalendarForNow();
				calendar1.set(Calendar.DAY_OF_MONTH, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
				setTimeToEndofDay(calendar1);
				end = calendar1.getTime();
				toDate = sf.format(end);

				// System.err.println("Month------------>" + fromDate+" "+toDate);
				map.add("fromDate", fromDate);
				map.add("toDate", toDate);
				map.add("compId", compId);

			} else {
				String value = request.getParameter("dates");
				String dateStr = value;
				String[] strDate = dateStr.split("to");
				fromDate = strDate[0];
				toDate = strDate[1];

				// System.err.println("Customise------------>" + fromDate+" "+toDate);
				map.add("fromDate", CommonUtility.convertToYMD(fromDate));
				map.add("toDate", CommonUtility.convertToYMD(toDate));
				map.add("compId", compId);
				
				model.addAttribute("dateVal", fromDate+" to "+toDate);
				model.addAttribute("showDate", 1);
			}
			dashDataList.clear();
			GetDashPieStatusCnt[] dashDataArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAllStatusCount", map, GetDashPieStatusCnt[].class);
			dashDataList = new ArrayList<GetDashPieStatusCnt>(Arrays.asList(dashDataArr));
			
			model.addAttribute("fromDate", fromDate);
			model.addAttribute("toDate", toDate);

			System.out.println("------------>"+dashDataList);
			model.addAttribute("statusCnt", dashDataList);
			model.addAttribute("radVal", radVal);
			
		} catch (Exception e) {
			System.out.println("Exc in /getDashCountData : " + e.getMessage());
		}
		return "orderDash";
	}
	
	@RequestMapping(value = "/getOrderListByStatus", method = RequestMethod.GET)
	public @ResponseBody List<GetOrderHeaderDisplay> getOrderListByStatus(HttpServletRequest request,
			HttpServletResponse response) {
		
		List<GetOrderHeaderDisplay> orderList = new ArrayList<>();			
		try {			
			System.err.println("In Here");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			String status = request.getParameter("statusId");			
			
			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");
			
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			
			System.err.println("Customise------------>" + fromDate+" "+toDate);
			
			map.add("fromDate", CommonUtility.convertToYMD(fromDate));
			map.add("toDate", CommonUtility.convertToYMD(toDate));
			map.add("status", status);
			map.add("compId", compId);

			GetOrderHeaderDisplay[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getOrderHeaderListBy", map, GetOrderHeaderDisplay[].class);

			orderList = new ArrayList<GetOrderHeaderDisplay>(Arrays.asList(orderRepArr));			
			System.out.println("Order List--->"+orderList);
		}catch (Exception e) {
			System.out.println("Exc in /getOrdersByStatus : "+e.getMessage());
			e.printStackTrace();
		}
				return orderList;
		
	}

	private static Calendar getCalendarForNow() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}

	private static void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndofDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

}
