package com.ats.ecomadmin.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.HomeController;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.CompanyContactInfo;
import com.ats.ecomadmin.model.ExportToExcel;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.report.GetCustomerWisReport;
import com.ats.ecomadmin.model.report.GetDateWiseBillReport;
import com.ats.ecomadmin.model.report.GetSellBillHeader;

@Controller
@Scope("session")
public class ReportsController {
	String todaysDate;

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/*-----------------------------------------------------------------------------------*/

	@RequestMapping(value = "/showDateWiseBillReport", method = RequestMethod.GET)
	public ModelAndView showDateWiseBillReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		model = new ModelAndView("reports/dateWiseReport");

		try {
			User userObj = (User) session.getAttribute("userObj");
			ZoneId z = ZoneId.of("Asia/Calcutta");

			LocalDate date = LocalDate.now(z);
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
			todaysDate = date.format(formatters);
			model.addObject("todaysDate", todaysDate);

			model.addObject("compId", userObj.getCompanyId());
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", userObj.getCompanyId());

			Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
					Franchise[].class);
			List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));
			model.addObject("frList", frList);

		} catch (Exception e) {

			System.out.println("Excep in /showDateWiseBillReport  " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getFranchiseListForRep", method = RequestMethod.GET)
	@ResponseBody
	public List<Franchise> getStatusList(HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		HttpSession session = request.getSession();

		User userObj = (User) session.getAttribute("userObj");
		map.add("compId", userObj.getCompanyId());

		Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
				Franchise[].class);
		List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));
		return frList;
	}

	List<GetDateWiseBillReport> dateBillRep = new ArrayList<GetDateWiseBillReport>();

	@RequestMapping(value = "/getDateWiseillsReport", method = RequestMethod.GET)
	public @ResponseBody List<GetDateWiseBillReport> getDateWiseillsReport(HttpServletRequest request,
			HttpServletResponse response) {
		String fromDate = "";
		String toDate = "";
		HttpSession session = request.getSession();

		try {
			User userObj = (User) session.getAttribute("userObj");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			int compId = userObj.getCompanyId();

			String dateStr = request.getParameter("dates");
			String[] strDate = dateStr.split("to");
			fromDate = strDate[0];
			toDate = strDate[1];

			String frIdStr = request.getParameter("frId");

			String frIdListString = "";

			if (frIdStr != null) {

				frIdListString = frIdStr.toString().substring(1, frIdStr.toString().length() - 1);
				frIdListString = frIdListString.replaceAll("\"", "");
				frIdListString = frIdListString.replaceAll(" ", "");
			}
			System.err.println("FrIds----" + frIdListString);
			map.add("fromDate", CommonUtility.convertToYMD(fromDate));
			map.add("toDate", CommonUtility.convertToYMD(toDate));
			map.add("frId", frIdListString);

			GetDateWiseBillReport[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getDateWiseBillsReport", map, GetDateWiseBillReport[].class);

			dateBillRep = new ArrayList<GetDateWiseBillReport>(Arrays.asList(orderRepArr));

		} catch (

		Exception e) {
			System.out.println("Excep in /getDateWiseillsReport " + e.getMessage());
			e.printStackTrace();

		}

		// exportToExcel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr No");
		rowData.add("Bill Date");
		rowData.add("No. Of Bills");
		rowData.add("Total Amt.");
		rowData.add("COD");
		rowData.add("Cash");
		rowData.add("Card");
		float grandTotal = 0.0f;

		expoExcel.setRowData(rowData);
		int srno = 1;
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < dateBillRep.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add(" " + srno);
			rowData.add(dateBillRep.get(i).getBillDate());
			rowData.add(" " + dateBillRep.get(i).getTotalBills());
			rowData.add(" " + roundUp(dateBillRep.get(i).getTotalAmt()));
			rowData.add(" " + dateBillRep.get(i).getCod());
			rowData.add(" " + dateBillRep.get(i).getCard());
			rowData.add(" " + dateBillRep.get(i).getEpay());
			grandTotal = grandTotal + roundUp(dateBillRep.get(i).getTotalAmt());

			srno = srno + 1;

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("Total");
		rowData.add("");
		rowData.add("" + Long.toString((long) (grandTotal)));
		rowData.add("");
		rowData.add("");
		rowData.add("");
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "DateWiseBillReport");
		session.setAttribute("reportNameNew", "Customer Purchase Order Detail Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$L$1");
		session.setAttribute("mergeUpto2", "$A$2:$L$2");

		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "DateWiseBillReport");

		return dateBillRep;
	}
	@RequestMapping(value = "pdf/getDateWiseBillPdf/{fromDate}/{toDate}/{frId}/{compId}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getDateWiseBillPdf(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("frId") String frId, @PathVariable int compId, @PathVariable int showHead,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		
		try {
			model = new ModelAndView("/reports/pdfs/dateWiseReportPdf");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("fromDate", CommonUtility.convertToYMD(fromDate));
			map.add("toDate", CommonUtility.convertToYMD(toDate));
			map.add("frId", frId);

			GetDateWiseBillReport[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getDateWiseBillsReport", map, GetDateWiseBillReport[].class);

			dateBillRep = new ArrayList<GetDateWiseBillReport>(Arrays.asList(orderRepArr));

			model.addObject("dateBillRep", dateBillRep);
			
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			CompanyContactInfo dtl = HomeController.getCompName(compId);
			if(showHead==1) {
				model.addObject("compName", dtl.getCompanyName());
				model.addObject("compAddress", dtl.getCompAddress());
				model.addObject("compContact", dtl.getCompContactNo());	
			}
		} catch (

		Exception e) {
			System.out.println("Excep in /getDateWiseBillPdf " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}
	
	List<GetSellBillHeader> orderDateCustDtl = new ArrayList<GetSellBillHeader>();
	@RequestMapping(value = "/getDateWiseCustDtlReport", method = RequestMethod.GET)
	public @ResponseBody List<GetSellBillHeader> getDateWiseCustDtlReport(HttpServletRequest request,
			HttpServletResponse response) {
		String fromDate = "";
		String toDate = "";
		HttpSession ses = request.getSession();		
		try {
			User userObj = (User) ses.getAttribute("userObj");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			String frIdStr = request.getParameter("frId");

			String frIdListString = "";

			if (frIdStr != null) {

				frIdListString = frIdStr.toString().substring(1, frIdStr.toString().length() - 1);
				frIdListString = frIdListString.replaceAll("\"", "");
				frIdListString = frIdListString.replaceAll(" ", "");
			}
			
			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");

			map.add("fromDate", CommonUtility.convertToYMD(fromDate));
			map.add("toDate", CommonUtility.convertToYMD(toDate));				
			map.add("compId", userObj.getCompanyId());
			map.add("frId", frIdListString);

			GetSellBillHeader[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getOrderDateWiseCustReport", map, GetSellBillHeader[].class);

			orderDateCustDtl = new ArrayList<GetSellBillHeader>(Arrays.asList(orderRepArr));
			
		} catch (

		Exception e) {
			System.out.println("Excep in /getDateWiseCustDtlReport " + e.getMessage());
			e.printStackTrace();

		}

		// exportToExcel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr No");
		rowData.add("Bill No.");
		rowData.add("Bill Date");
		rowData.add("Bill Amt.");
		rowData.add("Franchise");
		rowData.add("Payment Mode");
		rowData.add("Delivery Boy");
		float grandTotal = 0.0f;

		expoExcel.setRowData(rowData);
		int srno = 1;
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < orderDateCustDtl.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add(" " + srno);
			rowData.add(orderDateCustDtl.get(i).getInvoiceNo());
			rowData.add(" " + orderDateCustDtl.get(i).getBillDate());
			rowData.add(" " + roundUp(orderDateCustDtl.get(i).getGrandTotal()));
			rowData.add(" " + orderDateCustDtl.get(i).getFrName());
			rowData.add(orderDateCustDtl.get(i).getPaymentMode() == 1 ? "Cash"
					: orderDateCustDtl.get(i).getPaymentMode() == 2 ? "Card" : "E-Pay");
			rowData.add(" " + orderDateCustDtl.get(i).getDelvrBoyName());
			grandTotal = grandTotal + roundUp(orderDateCustDtl.get(i).getGrandTotal());

			srno = srno + 1;

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");		
		rowData.add("Total");
		rowData.add("");
		rowData.add("" + Long.toString((long) (grandTotal)));
		rowData.add("");
		rowData.add("");
		rowData.add("");
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListDummy", exportToExcelList);
		session.setAttribute("excelNameNew", "OrderDateWiseCustBillReport");
		session.setAttribute("reportNameNew", "Order Date Customer Bill Detail Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$L$1");
		session.setAttribute("mergeUpto2", "$A$2:$L$2");

		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "OrderDateWiseCustBillReport");

		return orderDateCustDtl;
	}
	
	/*---------------------------------------------------------------------------------------------*/
	
	@RequestMapping(value = "/monthWiseOrderReport", method = RequestMethod.GET)
	public ModelAndView monthWiseOrderReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		model = new ModelAndView("reports/monthWiseOrderReport");

		try {
			User userObj = (User) session.getAttribute("userObj");
			SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");

			Calendar cal = Calendar.getInstance();
			String toDate = sdf.format(cal.getTimeInMillis());

			cal.set(Calendar.DAY_OF_MONTH, 1);
			String fromDate = sdf.format(cal.getTimeInMillis());

			model.addObject("frommonth",fromDate);
			model.addObject("tomonth", toDate);
			
			model.addObject("compId", userObj.getCompanyId());
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", userObj.getCompanyId());

			Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
					Franchise[].class);
			List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));
			model.addObject("frList", frList);		

		} catch (Exception e) {

			System.out.println("Exc in /showCustWiseReportBetDate  " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}
	
		
		@RequestMapping(value = "/getMonthlyBillsReport", method = RequestMethod.GET)
		public @ResponseBody List<GetDateWiseBillReport> getMonthlyBillsReport(HttpServletRequest request,
				HttpServletResponse response) {
			String fromDate = "";
			String toDate = "";
			HttpSession ses = request.getSession();
			List<GetDateWiseBillReport> monthlyBillRep = new ArrayList<GetDateWiseBillReport>();
			try {
				System.err.println("In Here");
				User userObj = (User) ses.getAttribute("userObj");
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				String frIdStr = request.getParameter("frId");

				String frIdListString = "";

				if (frIdStr != null) {

					frIdListString = frIdStr.toString().substring(1, frIdStr.toString().length() - 1);
					frIdListString = frIdListString.replaceAll("\"", "");
					frIdListString = frIdListString.replaceAll(" ", "");
				}
				String multiDate = request.getParameter("dates");
				String[] splitDate = multiDate.split("to");
				fromDate = splitDate[0];
				toDate = splitDate[1];					
				
				String[] dateStr = splitDate[1].split("-");
			
				String endDate = CommonUtility.getLastDayOfMonth(Integer.parseInt(dateStr[2]),
						Integer.parseInt(dateStr[1]));		
				

				map.add("fromDate", CommonUtility.convertToYMD(fromDate));
				map.add("toDate", endDate);				
				map.add("frId", frIdListString);

				GetDateWiseBillReport[] orderRepArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getDateWiseBillsReport", map, GetDateWiseBillReport[].class);

				monthlyBillRep = new ArrayList<GetDateWiseBillReport>(Arrays.asList(orderRepArr));
				System.out.println("monthlyBillRep----"+monthlyBillRep);
			} catch (

			Exception e) {
				System.out.println("Excep in /getDateWiseillsReport " + e.getMessage());
				e.printStackTrace();

			}

			// exportToExcel

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No");
			rowData.add("Month");
			rowData.add("Year");
			rowData.add("No. Of Bills");
			rowData.add("Total Amt.");
			rowData.add("COD");
			rowData.add("Cash");
			rowData.add("Card");
			float grandTotal = 0.0f;

			expoExcel.setRowData(rowData);
			int srno = 1;
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < monthlyBillRep.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add(" " + srno);
				rowData.add(monthlyBillRep.get(i).getMonthName());
				rowData.add(monthlyBillRep.get(i).getOrderYear());
				rowData.add(" " + monthlyBillRep.get(i).getTotalBills());
				rowData.add(" " + roundUp(monthlyBillRep.get(i).getTotalAmt()));	
				rowData.add(" " + monthlyBillRep.get(i).getCod());	
				rowData.add(" " + monthlyBillRep.get(i).getCard());	
				rowData.add(" " + monthlyBillRep.get(i).getEpay());	
				grandTotal = grandTotal + roundUp(monthlyBillRep.get(i).getTotalAmt());

				srno = srno + 1;

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
	
			rowData.add("");		
			rowData.add("Total");
			rowData.add("");
			rowData.add("" + Long.toString((long) (grandTotal)));
			rowData.add("");
			rowData.add("");
			rowData.add("");
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "MonthWiseBillReport");
			session.setAttribute("reportNameNew", "Month Wiser Bill Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "MonthWiseBillReport");

			return monthlyBillRep;
		}
		
		@RequestMapping(value = "pdf/getMonthWiseBillPdf/{fromDate}/{toDate}/{frId}/{compId}/{showHead}", method = RequestMethod.GET)
		public ModelAndView getMonthWiseBillPdf(@PathVariable("fromDate") String fromDate,
				@PathVariable("toDate") String toDate, @PathVariable("frId") String frId,@PathVariable int compId, @PathVariable int showHead,
				HttpServletRequest request, HttpServletResponse response) {
			ModelAndView model = null;
			
			try {
				model = new ModelAndView("/reports/pdfs/monthlyBillPdf");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				
				String[] dateStr = toDate.split("-");			
				String[] frmDateStr = fromDate.split("-");	
				
				String endDate = CommonUtility.getLastDayOfMonth(Integer.parseInt(dateStr[2]),
						Integer.parseInt(dateStr[1]));	
				
				map.add("fromDate", CommonUtility.convertToYMD(fromDate));
				map.add("toDate", endDate);
				map.add("frId", frId);

				GetDateWiseBillReport[] orderRepArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getDateWiseBillsReport", map, GetDateWiseBillReport[].class);

				List<GetDateWiseBillReport> monthBillRep = new ArrayList<GetDateWiseBillReport>(Arrays.asList(orderRepArr));

				model.addObject("monthBillRep", monthBillRep);
				
				model.addObject("fromMonth", frmDateStr[1]+"-"+frmDateStr[2]);
				model.addObject("toMonth", dateStr[1]+"-"+dateStr[2]);
				
				CompanyContactInfo dtl = HomeController.getCompName(compId);
				if(showHead==1) {
					model.addObject("compName", dtl.getCompanyName());
					model.addObject("compAddress", dtl.getCompAddress());
					model.addObject("compContact", dtl.getCompContactNo());	
				}
			} catch (

			Exception e) {
				System.out.println("Excep in /getDateWiseBillPdf " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
		
		@RequestMapping(value = "/showCustWiseReportBetDate", method = RequestMethod.GET)
		public ModelAndView showCustWiseReportBetDate(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			HttpSession session = request.getSession();

			model = new ModelAndView("reports/custWiseReport");

			try {
				User userObj = (User) session.getAttribute("userObj");
				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				todaysDate = date.format(formatters);
				HttpSession ses = request.getSession();
				
				model.addObject("compId", userObj.getCompanyId());
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userObj.getCompanyId());

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));
				model.addObject("frList", frList);	
				
				model.addObject("todaysDate", todaysDate);

			} catch (Exception e) {

				System.out.println("Exc in /showCustWiseReportBetDate  " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}
		
		
		@RequestMapping(value = "/getCustPrchsRepBetDate", method = RequestMethod.GET)
		public @ResponseBody List<GetCustomerWisReport> getCustPrchsRepBetDate(HttpServletRequest request,
				HttpServletResponse response) {
			String fromDate = "";
			String toDate = "";
			HttpSession ses = request.getSession();
			List<GetCustomerWisReport> custRepList = new ArrayList<GetCustomerWisReport>();
			try {
				System.err.println("In Here");
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				String frIdStr = request.getParameter("frId");

				String frIdListString = "";

				if (frIdStr != null) {

					frIdListString = frIdStr.toString().substring(1, frIdStr.toString().length() - 1);
					frIdListString = frIdListString.replaceAll("\"", "");
					frIdListString = frIdListString.replaceAll(" ", "");
				}
				String multiDate = request.getParameter("dates");
				String[] splitDate = multiDate.split("to");
				fromDate = splitDate[0];
				toDate = splitDate[1];	

				map.add("fromDate", CommonUtility.convertToYMD(fromDate));
				map.add("toDate", CommonUtility.convertToYMD(toDate));
				// map.add("compId", compId);
				map.add("frId", frIdListString);

				GetCustomerWisReport[] orderRepArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getCustPurchaseRepByFrId", map, GetCustomerWisReport[].class);

				custRepList = new ArrayList<GetCustomerWisReport>(Arrays.asList(orderRepArr));
				
			} catch (

			Exception e) {
				System.out.println("Exep in getCustPrchsRepBetDate " + e.getMessage());
				e.printStackTrace();

			}

			// exportToExcel

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No");
			rowData.add("Customer Name");
			rowData.add("Mobile No.");
			rowData.add("Date Of Birth");
			rowData.add("Total Purchase");
			float grandTotal = 0.0f;

			expoExcel.setRowData(rowData);
			int srno = 1;
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < custRepList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add(" " + srno);
				rowData.add(custRepList.get(i).getCustName());
				rowData.add(" " + custRepList.get(i).getCustMobileNo());
				rowData.add(" " + custRepList.get(i).getDateOfBirth());
				rowData.add(" " + roundUp(custRepList.get(i).getGrandTotal()));

				grandTotal = grandTotal + roundUp(custRepList.get(i).getGrandTotal());

				srno = srno + 1;

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("");
			rowData.add("");
			rowData.add("Total");
			rowData.add("");
			rowData.add("" + Long.toString((long) (grandTotal)));
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "OrderWiseReport");
			session.setAttribute("reportNameNew", "Order Wise Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "OrderWiseReport");

			return custRepList;
		}
		
		List<GetSellBillHeader> custPrchDtlList = new ArrayList<GetSellBillHeader>();
		@RequestMapping(value = "/getCustPrchsReportDetail", method = RequestMethod.GET)
		public @ResponseBody List<GetSellBillHeader> getCustPrchsReportDetail(HttpServletRequest request,
				HttpServletResponse response) {
			String fromDate = "";
			String toDate = "";
			HttpSession ses = request.getSession();
			try {
				User userObj = (User) ses.getAttribute("userObj");
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				String frIdStr = request.getParameter("frId");

				String frIdListString = "";

				if (frIdStr != null) {

					frIdListString = frIdStr.toString().substring(1, frIdStr.toString().length() - 1);
					frIdListString = frIdListString.replaceAll("\"", "");
					frIdListString = frIdListString.replaceAll(" ", "");
				}
				String multiDate = request.getParameter("dates");
				String[] splitDate = multiDate.split("to");
				fromDate = splitDate[0];
				toDate = splitDate[1];	

				map.add("fromDate", CommonUtility.convertToYMD(fromDate));
				map.add("toDate", CommonUtility.convertToYMD(toDate));
				map.add("custId", Integer.parseInt(request.getParameter("custId")));
				map.add("compId", userObj.getCompanyId());
				map.add("frId", frIdListString);

				GetSellBillHeader[] orderRepArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getCustPurchaseDetailReport", map, GetSellBillHeader[].class);

				custPrchDtlList = new ArrayList<GetSellBillHeader>(Arrays.asList(orderRepArr));
				
			} catch (

			Exception e) {
				System.out.println("get sale Report hsn Wise " + e.getMessage());
				e.printStackTrace();

			}

			// exportToExcel

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No");
			rowData.add("Bill No.");
			rowData.add("Bill Date");
			rowData.add("Bill Amt.");
			rowData.add("Franchise");
			rowData.add("Payment Mode");
			rowData.add("Delivery Boy");
			float grandTotal = 0.0f;

			expoExcel.setRowData(rowData);
			int srno = 1;
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < custPrchDtlList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add(" " + srno);
				rowData.add(custPrchDtlList.get(i).getInvoiceNo());
				rowData.add(" " + custPrchDtlList.get(i).getBillDate());
				rowData.add(" " + roundUp(custPrchDtlList.get(i).getGrandTotal()));
				rowData.add(" " +custPrchDtlList.get(i).getFrName());
				rowData.add(custPrchDtlList.get(i).getPaymentMode() == 1 ? "Cash"
						: custPrchDtlList.get(i).getPaymentMode() == 2 ? "Card" : "E-Pay");
				rowData.add(" " + custPrchDtlList.get(i).getDelvrBoyName());
				grandTotal = grandTotal + roundUp(custPrchDtlList.get(i).getGrandTotal());

				srno = srno + 1;

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

//			expoExcel = new ExportToExcel();
//			rowData = new ArrayList<String>();
	//
//			rowData.add("");		
//			rowData.add("Total");
//			rowData.add("");
//			rowData.add("" + Long.toString((long) (grandTotal)));
//			expoExcel.setRowData(rowData);
//			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListDummy", exportToExcelList);
			session.setAttribute("excelNameNew", "CustPurchaseOrderDtlReport");
			session.setAttribute("reportNameNew", "Customer Purchase Order Detail Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "CustPurchaseOrderDtlReport");
			
			return custPrchDtlList;
		}

		@RequestMapping(value = "pdf/getCustWiseReportPdf/{frId}/{fromDate}/{toDate}/{compId}/{showHead}", method = RequestMethod.GET)
		public ModelAndView getCustWiseBillPdf( HttpServletRequest request,
				HttpServletResponse response, @PathVariable("frId") String frId, @PathVariable("fromDate") String fromDate,
				@PathVariable("toDate") String toDate,@PathVariable int compId, @PathVariable int showHead) {
			ModelAndView model = null;
			
			try {
				model = new ModelAndView("/reports/pdfs/custWiseReportPdf");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map.add("fromDate", CommonUtility.convertToYMD(fromDate));
				map.add("toDate", CommonUtility.convertToYMD(toDate));
				map.add("frId", frId);

				GetCustomerWisReport[] orderRepArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "/getCustPurchaseRepByFrId", map, GetCustomerWisReport[].class);

				List<GetCustomerWisReport> custRepList = new ArrayList<GetCustomerWisReport>(Arrays.asList(orderRepArr));				

				model.addObject("custRepList", custRepList);
				
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				
				CompanyContactInfo dtl = HomeController.getCompName(compId);
				if(showHead==1) {
					model.addObject("compName", dtl.getCompanyName());
					model.addObject("compAddress", dtl.getCompAddress());
					model.addObject("compContact", dtl.getCompContactNo());	
				}
				
			} catch (

			Exception e) {
				System.out.println("Excep in /getCustWiseReportPdf " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
}
