package com.ats.ecomadmin.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.ExportToExcel;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.User;
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
	@RequestMapping(value = "pdf/getDateWiseBillPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView getDateWiseBillPdf(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("frId") int frId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = null;
		
		try {
			model = new ModelAndView("/reports/reportPdf/dateBillPdf");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("fromDate", CommonUtility.convertToYMD(fromDate));
			map.add("toDate", CommonUtility.convertToYMD(toDate));
			map.add("frId", frId);

			GetDateWiseBillReport[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getDateWiseBillsReport", map, GetDateWiseBillReport[].class);

			dateBillRep = new ArrayList<GetDateWiseBillReport>(Arrays.asList(orderRepArr));

			model.addObject("dateBillRep", dateBillRep);
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
}
