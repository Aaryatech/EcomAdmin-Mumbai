package com.ats.ecomadmin.controller;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import com.ats.ecomadmin.HomeController;
import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.CompanyContactInfo;
import com.ats.ecomadmin.model.ExportToExcel;
import com.ats.ecomadmin.model.GetOrderDetailDisplay;
import com.ats.ecomadmin.model.GetOrderHeaderDisplay;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.ProductMaster;
import com.ats.ecomadmin.model.Status;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
public class OrderController {

	List<Status> statusList = new ArrayList<Status>();

	// Created By :- Mahendra Singh
	// Created On :- 02-10-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Order List
	@RequestMapping(value = "/showOrderList", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showOrderList", "showOrderList", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "order/orderList";

				int compId = (int) session.getAttribute("companyId");
				model.addAttribute("compId", compId);
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Status[] statusArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllStatus",
						Status[].class);
				statusList.clear();
				statusList = new ArrayList<Status>(Arrays.asList(statusArr));
				model.addAttribute("statusList", statusList);

				model.addAttribute("imagePath", Constants.showDocSaveUrl);
				model.addAttribute("title", "Orders List");

				Info add = AccessControll.checkAccess("showOrderList", "showOrderList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showOrderList", "showOrderList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showOrderList", "showOrderList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					model.addAttribute("addAccess", 0);
				}
				if (edit.isError() == false) {
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					model.addAttribute("deleteAccess", 0);
				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /showOrderList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getStatusList", method = RequestMethod.GET)
	@ResponseBody
	public List<Status> getStatusList(HttpServletRequest request, HttpServletResponse response) {

		return statusList;
	}

	List<GetOrderHeaderDisplay> orderList = new ArrayList<>();

	@RequestMapping(value = "/getOrderListByDate", method = RequestMethod.GET)
	@ResponseBody
	public List<GetOrderHeaderDisplay> getOrderDataBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		String fromDate = "";
		String toDate = "";
		try {
			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");

			String dateStr = request.getParameter("dates");
			String[] strDate = dateStr.split("to");
			fromDate = strDate[0];
			toDate = strDate[1];

			String statusStr = request.getParameter("status");

			String statusListString = "";

			if (statusStr != null) {

				statusListString = statusStr.toString().substring(1, statusStr.toString().length() - 1);
				statusListString = statusListString.replaceAll("\"", "");
				statusListString = statusListString.replaceAll(" ", "");
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", CommonUtility.convertToYMD(fromDate));
			map.add("toDate", CommonUtility.convertToYMD(toDate));
			map.add("status", statusListString);
			map.add("compId", compId);
			map.add("dateType", Integer.parseInt(request.getParameter("datetype")));

			GetOrderHeaderDisplay[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getOrderListByDates", map, GetOrderHeaderDisplay[].class);

			orderList = new ArrayList<GetOrderHeaderDisplay>(Arrays.asList(orderRepArr));

			//System.out.println("Header Trail------------" + orderList.get(0).getOrderTrailList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// export To Excel
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr No");
		rowData.add("Order No");
		rowData.add("Order Date");
		rowData.add("Delivery Date");
		rowData.add("Customer");
		rowData.add("Franchise");
		rowData.add("Order Status");
		rowData.add("Payment Mode");
		rowData.add("Total Amt");

		float grandTotal = 0.0f;
		String orderStatus = "";
		String paymentMode = "";

		expoExcel.setRowData(rowData);
		int srno = 1;
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < orderList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add(" " + srno);
			rowData.add(" " + orderList.get(i).getOrderNo());
			rowData.add(" " + orderList.get(i).getOrderDateDisplay());
			rowData.add(" " + orderList.get(i).getDeliveryDateDisplay());
			rowData.add(" " + orderList.get(i).getCustName() + " " + orderList.get(i).getCustMobile());
			rowData.add(" " + orderList.get(i).getFrName());

			if (orderList.get(i).getOrderStatus() == 0) {
				orderStatus = "Park Order";
			} else if (orderList.get(i).getOrderStatus() == 1) {
				orderStatus = "Shop Confirmation Pending";
			} else if (orderList.get(i).getOrderStatus() == 2) {
				orderStatus = "Accept";
			} else if (orderList.get(i).getOrderStatus() == 3) {
				orderStatus = "Processing";
			} else if (orderList.get(i).getOrderStatus() == 4) {
				orderStatus = "Delivery Pending";
			} else if (orderList.get(i).getOrderStatus() == 5) {
				orderStatus = "Delivered";
			} else if (orderList.get(i).getOrderStatus() == 6) {
				orderStatus = "Rejected by Shop";
			} else if (orderList.get(i).getOrderStatus() == 7) {
				orderStatus = "Return Order";
			} else if (orderList.get(i).getOrderStatus() == 8) {
				orderStatus = "Cancelled Order";
			}

			if (orderList.get(i).getPaymentMethod() == 1) {
				paymentMode = "Cash";
			} else if (orderList.get(i).getPaymentMethod() == 2) {
				paymentMode = "Card";
			} else {
				paymentMode = "E-Pay";
			}

			rowData.add(" " + orderStatus);
			rowData.add(" " + paymentMode);
			rowData.add(" " + orderList.get(i).getTotalAmt());

			grandTotal = grandTotal + roundUp(orderList.get(i).getTotalAmt());
			srno = srno + 1;

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("Total");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("" + Long.toString((long) (grandTotal)));

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "Orders");
		session.setAttribute("reportNameNew", "Order List");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$L$1");
		session.setAttribute("mergeUpto2", "$A$2:$L$2");

		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Orders");

		// System.err.println("OUTPUT Order Date Wise Detail= " + orderList);

		return orderList;
	}

	@RequestMapping(value = "/getOrderDetailByBillId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetOrderHeaderDisplay> getOrderDetailByBillId(HttpServletRequest request,
			HttpServletResponse response) {
		return orderList;

	}
	
	@RequestMapping(value = "pdf/getOrderListPdf/{compId}/{status}/{fromDate}/{toDate}/{datetype}/{showHead}", method = RequestMethod.GET)
	public ModelAndView getOrdrListPdf(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int compId, @PathVariable String status, @PathVariable String fromDate, @PathVariable String toDate, @PathVariable int datetype,
			@PathVariable int showHead) throws FileNotFoundException {
		ModelAndView model = null;
		try {
			model = new ModelAndView("order/orderListPdf");			

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", CommonUtility.convertToYMD(fromDate));
			map.add("toDate", CommonUtility.convertToYMD(toDate));
			map.add("status", status);
			map.add("compId", compId);
			map.add("dateType", datetype);

			GetOrderHeaderDisplay[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getOrderListByDates", map, GetOrderHeaderDisplay[].class);

			orderList = new ArrayList<GetOrderHeaderDisplay>(Arrays.asList(orderRepArr));
			model.addObject("orderList",  orderList);
			
			CompanyContactInfo dtl = HomeController.getCompName(compId);
			if(showHead==1) {
				model.addObject("compName", dtl.getCompanyName());
				model.addObject("compAddress", dtl.getCompAddress());
				model.addObject("compContact", dtl.getCompContactNo());	
			}
			model.addObject("fromDate", fromDate);	
			model.addObject("toDate", toDate);	
			if(datetype==1) 
				model.addObject("reportName", "Delivery Date Wise Order List");	
			else
				model.addObject("reportName", "Production Date Wise Order List");	
			}catch (Exception e) {
				System.out.println("Excep in /getOrdrComFrmGrpByPdf "+e.getMessage());
			}
		
		return model;

	}

	@RequestMapping(value = "/getOrderDetailList/{orderNo}", method = RequestMethod.GET)
	public String getOrderDetailList(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable String orderNo) {

		List<GetOrderDetailDisplay> orderDetail = new ArrayList<GetOrderDetailDisplay>();
		try {
			HttpSession session = request.getSession();

			int compId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("orderNo", orderNo);
			map.add("compId", compId);

			GetOrderDetailDisplay[] orderRepArr = Constants.getRestTemplate()
					.postForObject(Constants.url + "/getOrderDetailByOrderNo", map, GetOrderDetailDisplay[].class);

			orderDetail = new ArrayList<GetOrderDetailDisplay>(Arrays.asList(orderRepArr));
		//	System.out.println("Order Trail Detail List------------->" + orderDetail);
			model.addAttribute("orderDetail", orderDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "order/orderDetailList";

	}

	@RequestMapping(value = "/getItemImagesByItemId", method = RequestMethod.GET)
	@ResponseBody
	public Info getItemImagesByItemId(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		try {
		int productId = Integer.parseInt(request.getParameter("itemId"));
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("productId", productId);

		ProductMaster productImgs = Constants.getRestTemplate().postForObject(Constants.url + "getProductByProductId",
				map, ProductMaster.class);
		
		if(productImgs!=null) {
		info.setMsg(productImgs.getProductImages());
		info.setError(false);
		}else {
			info.setMsg(null);
			info.setError(true);
		}
	//	System.out.println("Images-----------"+info);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return info;

	}

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
		
	private Dimension format = PD4Constants.A4;
	private boolean landscapeValue = false;
	private int topValue = 8;
	private int leftValue = 0;
	private int rightValue = 0;
	private int bottomValue = 8;
	private String unitsValue = "m";
	private String proxyHost = "";
	private int proxyPort = 0;

	private int userSpaceWidth = 750;
	private static int BUFFER_SIZE = 1024;

	@RequestMapping(value = "/pdfReport", method = RequestMethod.GET)
	public void showPDF(HttpServletRequest request, HttpServletResponse response) {

		String url = request.getParameter("url");
		System.out.println("URL " + url);
		
		// File f = new File("/home/ats-12/bill.pdf");
		File f = new File(Constants.REPORT_PATH);		

		System.out.println("I am here " + f.toString());
		try {
			
			runConverter(Constants.ReportURL + url, f, request, response);
			System.out.println("Come on lets get ");
		} catch (IOException e) {		
			System.out.println("Pdf conversion exception " + e.getMessage());
			e.printStackTrace();
		}

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");
		String filename = "ordermemo221.pdf";
		// String filePath = "/home/ats-12/bill.pdf";
		String filePath = Constants.REPORT_PATH;
		// String filePath =
		// "/Users/MIRACLEINFOTAINMENT/ATS/uplaods/reports/ordermemo221.pdf";

		// construct the complete absolute path of the file
		String fullPath = appPath + filePath;
		File downloadFile = new File(filePath);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(downloadFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/pdf";
			}
			System.out.println("MIME type: " + mimeType);

			String headerKey = "Content-Disposition";

			// response.addHeader("Content-Disposition", "attachment;filename=report.pdf");
			response.setContentType("application/pdf");

			// get output stream of the response
			OutputStream outStream;

			outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runConverter(String urlstring, File output, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (urlstring.length() > 0) {
			if (!urlstring.startsWith("http://") && !urlstring.startsWith("file:")) {
				urlstring = "http://" + urlstring;
			}
			System.out.println("PDF URL " + urlstring);
			java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

			PD4ML pd4ml = new PD4ML();
			pd4ml.setPageSize(format);
			pd4ml.enableSmartTableBreaks(true);
			try {
				SimpleDateFormat pdfSdf = new SimpleDateFormat("dd-MM-yyyy");
				Date pdfDate = new Date();
				
				PD4PageMark footer = new PD4PageMark();
				footer.setPageNumberTemplate("page $[page] of $[total]");
				footer.setTitleTemplate(pdfSdf.format(pdfDate));
				footer.setTitleAlignment(PD4PageMark.CENTER_ALIGN);
				footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);
				footer.setInitialPageNumber(1);
				footer.setFontSize(8);
				footer.setAreaHeight(15);

				pd4ml.setPageFooter(footer);						

			} catch (Exception e) {
				System.out.println("Pdf conversion method excep " + e.getMessage());
			}
			try {
				pd4ml.setPageSize(landscapeValue ? pd4ml.changePageOrientation(format) : format);
			} catch (Exception e) {
				System.out.println("Pdf conversion ethod excep " + e.getMessage());
			}

			if (unitsValue.equals("mm")) {
				pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));
			} else {
				pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue, rightValue));
			}

			pd4ml.setHtmlWidth(userSpaceWidth);
			pd4ml.render(urlstring, fos);
		}
	}

}
