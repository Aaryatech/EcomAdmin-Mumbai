package com.ats.ecomadmin.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Area;
import com.ats.ecomadmin.model.AreaCityList;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.City;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.DeliveryInstruction;
import com.ats.ecomadmin.model.FilterTypes;
import com.ats.ecomadmin.model.Franchise;
import com.ats.ecomadmin.model.GrievencesInstruction;
import com.ats.ecomadmin.model.GrievencesTypeInstructn;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.Language;
import com.ats.ecomadmin.model.MFilter;
import com.ats.ecomadmin.model.SpDayHomePage;
import com.ats.ecomadmin.model.Tax;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.UserTypeMaster;
import com.ats.ecomadmin.model.acrights.ModuleJson;

@Controller
@Scope("session")
public class MasterController {

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show UOM List
	@RequestMapping(value = "/showUomList", method = RequestMethod.GET)
	public String showUomList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		List<Uom> uomList = new ArrayList<Uom>();
		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showUomList", "showUomList", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				session.setAttribute("compId", 1);

				int compId = (int) session.getAttribute("companyId");
				mav = "masters/uomList";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Uom[] tagArr = Constants.getRestTemplate().postForObject(Constants.url + "getUoms", map, Uom[].class);
				uomList = new ArrayList<Uom>(Arrays.asList(tagArr));

				for (int i = 0; i < uomList.size(); i++) {

					uomList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(uomList.get(i).getUomId())));
				}
				model.addAttribute("uomList", uomList);

				model.addAttribute("title", "UOM List");

				Info add = AccessControll.checkAccess("showUomList", "showUomList", "0", "1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showUomList", "showUomList", "0", "0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showUomList", "showUomList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /showUomList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add UOM JSP Page
	@RequestMapping(value = "/newUom", method = RequestMethod.GET)
	public String newUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newUom", "showUomList", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addUom";
				Uom uom = new Uom();
				model.addAttribute("uom", uom);
				model.addAttribute("title", "Add UOM");
			}
		} catch (Exception e) {
			System.out.println("Execption in /newUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert UOM in database
	@RequestMapping(value = "/insertUom", method = RequestMethod.POST)
	public String insertUom(HttpServletRequest request, HttpServletResponse response) {

		try {
			Uom uom = new Uom();
			HttpSession session = request.getSession();

			int uomId = Integer.parseInt(request.getParameter("uomId"));
			int compId = (int) session.getAttribute("companyId");

			uom.setCompanyId(compId);
			uom.setDelStatus(1);
			uom.setExInt1(0);
			uom.setExInt2(0);
			uom.setExInt3(0);
			uom.setExVar1("NA");
			uom.setExVar2("NA");
			uom.setExVar3("NA");
			uom.setExVar4("NA");
			uom.setIsParent(Integer.parseInt(request.getParameter("isParent")));
			uom.setAllowToCopy(Integer.parseInt(request.getParameter("allowCopy")));
			uom.setIsActive(Integer.parseInt(request.getParameter("active_uom")));
			uom.setSortNo(0);
			uom.setUomDesc(request.getParameter("description"));
			uom.setUomId(uomId);
			uom.setUomName(request.getParameter("uom_name"));
			uom.setUomShowName(request.getParameter("show_name"));

			Uom res = Constants.getRestTemplate().postForObject(Constants.url + "saveUom", uom, Uom.class);

			if (res.getUomId() > 0) {
				if (uomId == 0)
					session.setAttribute("successMsg", "UOM Saved Sucessfully");
				else
					session.setAttribute("successMsg", "UOM  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save UOM");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertUom : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showUomList";

	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Update UOM
	@RequestMapping(value = "/editUom", method = RequestMethod.GET)
	public String editUom(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newUom", "showUomList", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addUom";

				String base64encodedString = request.getParameter("uomId");
				String uomId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("uomId", uomId);

				Uom uom = Constants.getRestTemplate().postForObject(Constants.url + "getUomById", map, Uom.class);
				model.addAttribute("uom", uom);
				model.addAttribute("title", "Edit UOM");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editUom : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 11-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete UOM
	@RequestMapping(value = "/deleteUom", method = RequestMethod.GET)
	public String deleteUom(HttpServletRequest request, HttpServletResponse response) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteUom", "showUomList", "0", "0", "0", "1", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				String base64encodedString = request.getParameter("uomId");
				String uomId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("uomId", Integer.parseInt(uomId));

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteUomById", map, Info.class);

				if (!info.isError()) {
					session.setAttribute("successMsg", info.getMsg());
				} else {
					session.setAttribute("errorMsg", info.getMsg());
				}
				mav = "redirect:/showUomList";
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUom : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*-------------------------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Tax List
	@RequestMapping(value = "/showTaxList", method = RequestMethod.GET)
	public String showTaxList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		List<Tax> taxList = new ArrayList<Tax>();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showTaxList", "showTaxList", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				session.setAttribute("compId", 1);

				int compId = (int) session.getAttribute("companyId");
				mav = "masters/taxList";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				Tax[] tagArr = Constants.getRestTemplate().postForObject(Constants.url + "getTaxes", map, Tax[].class);
				taxList = new ArrayList<Tax>(Arrays.asList(tagArr));

				for (int i = 0; i < taxList.size(); i++) {

					taxList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(taxList.get(i).getTaxId())));
				}
				model.addAttribute("taxList", taxList);

				model.addAttribute("title", "Tax List");
				Info add = AccessControll.checkAccess("showTaxList", "showTaxList", "0", "1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showTaxList", "showTaxList", "0", "0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showTaxList", "showTaxList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /showTaxList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add Tax JSP Page
	@RequestMapping(value = "/newTax", method = RequestMethod.GET)
	public String newTax(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newTax", "showTaxList", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addTax";
				Tax tax = new Tax();
				model.addAttribute("tax", tax);
				model.addAttribute("title", "Add Tax");
			}
		} catch (Exception e) {
			System.out.println("Execption in /newTax : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert Tax
	@RequestMapping(value = "/insertTax", method = RequestMethod.POST)
	public String insertTax(HttpServletRequest request, HttpServletResponse response) {

		try {
			Tax tax = new Tax();
			HttpSession session = request.getSession();

			int taxId = Integer.parseInt(request.getParameter("taxId"));
			int compId = (int) session.getAttribute("companyId");

			tax.setTaxId(taxId);
			tax.setTaxName(request.getParameter("taxName"));
			tax.setTaxDesc(request.getParameter("description"));
			tax.setHsnCode(request.getParameter("hsnCode"));
			tax.setSgstPer(Float.parseFloat(request.getParameter("sgstPer")));
			tax.setCgstPer(Float.parseFloat(request.getParameter("cgstPer")));
			tax.setIgstPer(Float.parseFloat(request.getParameter("igstPer")));
			tax.setCessPer(Float.parseFloat(request.getParameter("cessPer")));
			tax.setTotalTaxPer(Float.parseFloat(request.getParameter("totalTaxPer")));
			tax.setIsParent(Integer.parseInt(request.getParameter("isParent")));
			tax.setAllowToCopy(Integer.parseInt(request.getParameter("allowCopy")));
			tax.setCompanyId(compId);
			tax.setDelStatus(1);
			tax.setExInt1(0);
			tax.setExInt2(0);
			tax.setExInt3(0);
			tax.setExVar1("NA");
			tax.setExVar2("NA");
			tax.setExVar3("NA");
			tax.setIsActive(Integer.parseInt(request.getParameter("active_tax")));
			tax.setSortNo(0);

			Tax res = Constants.getRestTemplate().postForObject(Constants.url + "saveTax", tax, Tax.class);

			if (res.getTaxId() > 0) {
				if (taxId == 0)
					session.setAttribute("successMsg", "Tax Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Tax  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Tax");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertTax : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showTaxList";

	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Update Tax
	@RequestMapping(value = "/editTax", method = RequestMethod.GET)
	public String editTax(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editTax", "showTaxList", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addTax";

				String base64encodedString = request.getParameter("taxId");
				String taxId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("taxId", taxId);

				Tax tax = Constants.getRestTemplate().postForObject(Constants.url + "getTaxById", map, Tax.class);
				model.addAttribute("tax", tax);
				model.addAttribute("title", "Edit Tax");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editTax : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Tax
	@RequestMapping(value = "/deleteTax", method = RequestMethod.GET)
	public String deleteTax(HttpServletRequest request, HttpServletResponse response) {

		String mav = new String();
		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteTax", "showTaxList", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				String base64encodedString = request.getParameter("taxId");
				String taxId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("taxId", Integer.parseInt(taxId));

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "deleteTaxById", map, Info.class);

				if (!info.isError()) {
					session.setAttribute("successMsg", info.getMsg());
				} else {
					session.setAttribute("errorMsg", info.getMsg());
				}

				mav = "redirect:/showTaxList";
			}

		} catch (Exception e) {
			System.out.println("Execption in /deleteTax : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*-------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show All Users
	@RequestMapping(value = "/showUsers", method = RequestMethod.GET)
	public String showMnUsers(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showUsers", "showUsers", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				int compId = (int) session.getAttribute("companyId");

				mav = "masters/userList";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				User[] userArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllUsers", map,
						User[].class);
				List<User> userList = new ArrayList<User>(Arrays.asList(userArr));

				for (int i = 0; i < userList.size(); i++) {

					userList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getUserId())));
				}
				model.addAttribute("userList", userList);
				model.addAttribute("title", "Users List");

				Info add = AccessControll.checkAccess("showUsers", "showUsers", "0", "1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showUsers", "showUsers", "0", "0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showUsers", "showUsers", "0", "0", "0", "1", newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /showUsers : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect To Add User page
	@RequestMapping(value = "/addNewUser", method = RequestMethod.GET)
	public String addNewUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		User user = new User();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("addNewUser", "showUsers", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addUser";

				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				UserTypeMaster[] userTypeArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllUserTypes", UserTypeMaster[].class);
				List<UserTypeMaster> usrTypList = new ArrayList<UserTypeMaster>(Arrays.asList(userTypeArr));

				model.addAttribute("userTypeList", usrTypList);

				model.addAttribute("user", user);
				model.addAttribute("title", "Add User");
			}
		} catch (Exception e) {
			System.out.println("Execption in /addNewUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Add User
	@RequestMapping(value = "/insertNewUser", method = RequestMethod.POST)
	public String insertNewUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy ");
			String profileImage = null;

			int companyId = (int) session.getAttribute("companyId");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				System.err.println("In If ");

				profileImage = sf.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				System.err.println("In else ");
				profileImage = request.getParameter("editImg");

			}

			String pass = request.getParameter("pass");
			String password = pass;
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			User user = new User();

			int userId = Integer.parseInt(request.getParameter("user_id"));

			if (userId > 0) {
				user.setUpdtDttime(sf.format(date));

			} else {
				user.setInsertDttime(sf.format(date));
			}
			user.setUserId(userId);
			user.setCompanyId(companyId);
			user.setIsActive(Integer.parseInt(request.getParameter("user")));

			user.setPassword(hashtext);
			user.setProfilePic(profileImage);
			user.setRegDate(request.getParameter("reg_date"));
			user.setUserAddress(request.getParameter("address"));

			user.setUserEmail(request.getParameter("email"));
			user.setUserMobileNo(request.getParameter("mob_no"));
			user.setUserName(request.getParameter("user_name"));
			user.setUserTypeId(Integer.parseInt(request.getParameter("user_type")));

			user.setMakerUserId(userObj.getUserId());

			user.setRoleId(Integer.parseInt(request.getParameter("roleId")));
			user.setIsEnrolled(Integer.parseInt(request.getParameter("isEnrolled")));

			user.setExDate1(sfd.format(date));
			user.setExDate2(sfd.format(date));
			user.setBirthDate(request.getParameter("dob"));
			user.setDeptId(Integer.parseInt(request.getParameter("department")));

			user.setDelStatus(1);

			user.setExFloat1(0);
			user.setExFloat2(0);
			user.setExFloat3(0);

			user.setExInt1(0);
			user.setExInt2(0);
			user.setExInt3(0);

			user.setExVar1("NA");
			user.setExVar2("NA");
			user.setExVar3("NA");
			user.setExVar4("NA");

			User res = Constants.getRestTemplate().postForObject(Constants.url + "addUser", user, User.class);

			if (res.getUserId() > 0) {
				if (userId == 0)
					session.setAttribute("successMsg", "User Saved Sucessfully");
				else
					session.setAttribute("successMsg", "User Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save User");
			}
		} catch (Exception e) {
			System.out.println("Execption in /insertNewUser : " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showUsers";

	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Update User
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public String editUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editUser", "showUsers", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addUser";

				int companyId = (int) session.getAttribute("companyId");

				UserTypeMaster[] userTypeArr = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllUserTypes", UserTypeMaster[].class);
				List<UserTypeMaster> usrTypList = new ArrayList<UserTypeMaster>(Arrays.asList(userTypeArr));
				model.addAttribute("userTypeList", usrTypList);

				String base64encodedString = request.getParameter("userId");
				String userId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userId", Integer.parseInt(userId));
				map.add("compId", companyId);

				User user = Constants.getRestTemplate().postForObject(Constants.url + "getUserById", map, User.class);

				model.addAttribute("user", user);

				model.addAttribute("imgPath", Constants.showDocSaveUrl);
				model.addAttribute("title", "Edit User");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete User
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteUser", "showUsers", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "redirect:/showUsers";
				String base64encodedString = request.getParameter("userId");
				String userId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userId", Integer.parseInt(userId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteUserById", map, Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Validation for unique user by mobile No.
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public Info getUserInfo(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			int userId = 0;
			try {
				userId = Integer.parseInt(request.getParameter("userId"));
			} catch (Exception e) {
				userId = 0;
				e.printStackTrace();
			}
			String mobNo = request.getParameter("mobNo");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("mobNo", mobNo);
			map.add("userId", userId);

			User res = Constants.getRestTemplate().postForObject(Constants.url + "getUserByMobNo", map, User.class);
			System.out.println("userRes  ------  " + res);
			if (res != null) {
				info.setError(false);
				info.setMsg("User Found");
			} else {
				info.setError(true);
				info.setMsg("User Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getUserInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Validation for unique user by email.
	@RequestMapping(value = "/getUserInfoByEmail", method = RequestMethod.GET)
	@ResponseBody
	public Info getUserInfoByEmail(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			int userId = 0;
			try {
				userId = Integer.parseInt(request.getParameter("userId"));
			} catch (Exception e) {
				userId = 0;
				e.printStackTrace();
			}
			String email = request.getParameter("email");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("email", email);
			map.add("userId", userId);

			User res = Constants.getRestTemplate().postForObject(Constants.url + "getUserByEmail", map, User.class);

			if (res != null) {
				info.setError(false);
				info.setMsg("User Found");
			} else {
				info.setError(true);
				info.setMsg("User Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getUserInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	/*-------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show All Users Types
	@RequestMapping(value = "/showCategoryList", method = RequestMethod.GET)
	public String categoryList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showCategoryList", "showCategoryList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				int companyId = (int) session.getAttribute("companyId");

				mav = "masters/categoryList";

				List<Category> catList = new ArrayList<>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Category[] catArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCategories", map,
						Category[].class);
				catList = new ArrayList<Category>(Arrays.asList(catArr));

				for (int i = 0; i < catList.size(); i++) {

					catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
				}

				model.addAttribute("catList", catList);
				model.addAttribute("title", "Category List");
				// model.addAttribute("imageUrl", Constants.showDocSaveUrl);
				Info add = AccessControll.checkAccess("showCategoryList", "showCategoryList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showCategoryList", "showCategoryList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showCategoryList", "showCategoryList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showCategoryList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect To Add Category Page
	@RequestMapping(value = "/newCategory", method = RequestMethod.GET)
	public String newCategory(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newCategory", "showCategoryList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCategory";
				Category cat = new Category();

				model.addAttribute("cat", cat);
				model.addAttribute("title", "Add Category");
				model.addAttribute("imgPath", Constants.showDocSaveUrl);
			}
		} catch (Exception e) {
			System.out.println("Execption in /newCategory : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Add Category
	@RequestMapping(value = "/insertNewCategory", method = RequestMethod.POST)
	public String insertNewCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String profileImage = null;

			int companyId = (int) session.getAttribute("companyId");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				System.err.println("In If ");

				profileImage = sf.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				System.err.println("In else ");
				profileImage = request.getParameter("editImg");

			}

			Category cat = new Category();
			int catId = Integer.parseInt(request.getParameter("catId"));

			cat.setCatId(catId);
			cat.setAllowToCopy(Integer.parseInt(request.getParameter("allowCopy")));
			cat.setCatDesc(request.getParameter("description"));
			cat.setCatName(request.getParameter("catName"));
			cat.setCatPrefix(request.getParameter("prefix"));
			cat.setIsParent(Integer.parseInt(request.getParameter("isParent")));
			cat.setCompanyId(companyId);
			cat.setImageName(profileImage);
			cat.setIsActive(Integer.parseInt(request.getParameter("catActive")));
			cat.setSortNo(0);

			cat.setDelStatus(1);

			cat.setExInt1(0);
			cat.setExInt2(0);
			cat.setExInt3(0);

			cat.setExVar1("NA");
			cat.setExVar2("NA");
			cat.setExVar3("NA");

			Category res = Constants.getRestTemplate().postForObject(Constants.url + "saveCategory", cat,
					Category.class);

			if (res.getCatId() > 0) {
				if (catId == 0)
					session.setAttribute("successMsg", "Category Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Category Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Category");
			}
		} catch (Exception e) {
			System.out.println("Execption in /insertNewCategory : " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showCategoryList";

	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Update User
	@RequestMapping(value = "/editCategory", method = RequestMethod.GET)
	public String editCategory(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editCategory", "showCategoryList", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCategory";

				int companyId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("catId");
				String catId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("catId", Integer.parseInt(catId));

				Category cat = Constants.getRestTemplate().postForObject(Constants.url + "getCatById", map,
						Category.class);

				model.addAttribute("cat", cat);

				model.addAttribute("imgPath", Constants.showDocSaveUrl);
				model.addAttribute("title", "Edit Category");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editCategory : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Category
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	public String deleteCategory(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteCategory", "showCategoryList", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "redirect:/showCategoryList";
				String base64encodedString = request.getParameter("catId");
				String catId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("catId", Integer.parseInt(catId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteCategoryById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteCategory : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/chkUniqPrefix", method = RequestMethod.GET)
	@ResponseBody
	public Info chkUniqPrefix(HttpServletRequest request, HttpServletResponse response, Model model) {

		Info res = new Info();

		try {
			String prefix = request.getParameter("prefix");
			int catId = Integer.parseInt(request.getParameter("catId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("prefix", prefix);
			map.add("catId", catId);

			res = Constants.getRestTemplate().postForObject(Constants.url + "getCatByPrefix", map, Info.class);

		} catch (Exception e) {
			System.out.println("Execption in /chkUniqPrefix : " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	/*-------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show All Filter Types
	@RequestMapping(value = "/showFilterTypeList", method = RequestMethod.GET)
	public String showFilterTypeList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showFilterTypeList", "showFilterTypeList", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				int companyId = (int) session.getAttribute("companyId");

				mav = "masters/filterTypeList";

				List<FilterTypes> filterTypeList = new ArrayList<FilterTypes>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				FilterTypes[] filterTypeArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllFilterTypes", map, FilterTypes[].class);
				filterTypeList = new ArrayList<FilterTypes>(Arrays.asList(filterTypeArr));

				for (int i = 0; i < filterTypeList.size(); i++) {

					filterTypeList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(filterTypeList.get(i).getFilterTypeId())));
				}

				model.addAttribute("filterList", filterTypeList);
				model.addAttribute("title", "Filter Type List");
				// model.addAttribute("imageUrl", Constants.showDocSaveUrl);
				Info add = AccessControll.checkAccess("showFilterTypeList", "showFilterTypeList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showFilterTypeList", "showFilterTypeList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showFilterTypeList", "showFilterTypeList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showFilterTypeList : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect To Add Filter Type Page
	@RequestMapping(value = "/newFilterType", method = RequestMethod.GET)
	public String newFilterType(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newFilterType", "showFilterTypeList", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addFilterType";
				FilterTypes filterType = new FilterTypes();

				model.addAttribute("filterType", filterType);
				model.addAttribute("title", "Add Filter Type");
			}
		} catch (Exception e) {
			System.out.println("Execption in /newFilterType : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert FilterType database
	@RequestMapping(value = "/insertFilterType", method = RequestMethod.POST)
	public String insertFilteType(HttpServletRequest request, HttpServletResponse response) {

		try {

			HttpSession session = request.getSession();

			int filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			int compId = (int) session.getAttribute("companyId");

			FilterTypes filterType = new FilterTypes();

			filterType.setFilterTypeId(filterTypeId);
			filterType.setCompanyId(compId);
			filterType.setDelStatus(1);
			filterType.setExInt1(0);
			filterType.setExInt2(0);
			filterType.setExVar1("NA");
			filterType.setExVar2("NA");
			filterType.setIsActive(Integer.parseInt(request.getParameter("isActive")));
			filterType.setIsCostAffect(Integer.parseInt(request.getParameter("isCostAffect")));
			filterType.setIsUsedFilter(Integer.parseInt(request.getParameter("isUsedFilter")));
			filterType.setFilterTypeName(request.getParameter("filterTypeName"));
			filterType.setFilterTypeDesc(request.getParameter("description"));

			FilterTypes res = Constants.getRestTemplate().postForObject(Constants.url + "saveFilterType", filterType,
					FilterTypes.class);

			if (res.getFilterTypeId() > 0) {
				if (filterTypeId == 0)
					session.setAttribute("successMsg", "Filter Type Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Filter Type  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Filter Type");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertFilterType : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showFilterTypeList";

	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Update Filter Type
	@RequestMapping(value = "/editFilterType", method = RequestMethod.GET)
	public String editFilterType(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editFilterType", "showFilterTypeList", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addFilterType";

				int companyId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("filterTypeId");
				String filterTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("filterTypeId", Integer.parseInt(filterTypeId));

				FilterTypes filterType = Constants.getRestTemplate().postForObject(Constants.url + "getFilterTypeById",
						map, FilterTypes.class);

				model.addAttribute("filterType", filterType);

				model.addAttribute("imgPath", Constants.showDocSaveUrl);
				model.addAttribute("title", "Edit Filter Type");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editFilterType : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Filter Type
	@RequestMapping(value = "/deleteFilterType", method = RequestMethod.GET)
	public String deleteFilterType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteFilterType", "showFilterTypeList", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "redirect:/showFilterTypeList";
				String base64encodedString = request.getParameter("filterTypeId");
				String filterTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("filterTypeId", Integer.parseInt(filterTypeId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteFilterTypeById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteFilterType : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*-------------------------------------------------------------------------------*/

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Filter
	@RequestMapping(value = "/showFilter/{filterTypeId}", method = RequestMethod.GET)
	public String showFilter(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("filterTypeId") int filterTypeId, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showFilter/" + filterTypeId, "showFilter/" + filterTypeId, "1", "0",
					"0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				int companyId = (int) session.getAttribute("companyId");

				mav = "masters/filterList";

				List<MFilter> filterList = new ArrayList<MFilter>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map = new LinkedMultiValueMap<>();
				map.add("filterTypeId", filterTypeId);
				FilterTypes filterType = Constants.getRestTemplate().postForObject(Constants.url + "getFilterTypeById",
						map, FilterTypes.class);

				model.addAttribute("title", filterType.getFilterTypeName() + " List");
				model.addAttribute("filterType", filterType.getFilterTypeName());
				model.addAttribute("filterTypeId", filterTypeId);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				map.add("filterTypeId", filterTypeId);

				MFilter[] filterArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
				filterList = new ArrayList<MFilter>(Arrays.asList(filterArr));

				for (int i = 0; i < filterList.size(); i++) {

					filterList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(filterList.get(i).getFilterId())));
				}

				model.addAttribute("filterList", filterList);

				// model.addAttribute("imageUrl", Constants.showDocSaveUrl);
				Info add = AccessControll.checkAccess("showFilter/" + filterTypeId, "showFilter/" + filterTypeId, "0",
						"1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showFilter/" + filterTypeId, "showFilter/" + filterTypeId, "0",
						"0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showFilter/" + filterTypeId, "showFilter/" + filterTypeId,
						"0", "0", "0", "1", newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showFilter : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect To Add Filter Page
	@RequestMapping(value = "/newFilter/{filterTypeId}", method = RequestMethod.GET)
	public String newFilter(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("filterTypeId") int filterTypeId, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newFilter/" + filterTypeId, "showFilter/" + filterTypeId, "0", "1",
					"0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addFilter";

				MFilter filter = new MFilter();

				model.addAttribute("filter", filter);
				model.addAttribute("filterTypeId", filterTypeId);

				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map = new LinkedMultiValueMap<>();
				map.add("filterTypeId", filterTypeId);

				FilterTypes filterType = Constants.getRestTemplate().postForObject(Constants.url + "getFilterTypeById",
						map, FilterTypes.class);

				model.addAttribute("title", "Add " + filterType.getFilterTypeName());

				model.addAttribute("filterType", filterType.getFilterTypeName());
			}
		} catch (Exception e) {
			System.out.println("Execption in /newFilter : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert Filter database
	@RequestMapping(value = "/insertFilter", method = RequestMethod.POST)
	public String insertFilter(HttpServletRequest request, HttpServletResponse response) {
		int filterTypeId = 0;
		try {

			HttpSession session = request.getSession();

			int filterId = Integer.parseInt(request.getParameter("filterId"));
			filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			int compId = (int) session.getAttribute("companyId");

			MFilter filter = new MFilter();

			filter.setFilterId(filterId);
			filter.setFilterTypeId(filterTypeId);
			filter.setIsParent(Integer.parseInt(request.getParameter("isParent")));
			filter.setAllowToCopy(Integer.parseInt(request.getParameter("allowCopy")));
			filter.setIsActive(Integer.parseInt(request.getParameter("isActive")));
			filter.setCostAffect(Integer.parseInt(request.getParameter("isCostAffect")));
			filter.setUsedForFilter(Integer.parseInt(request.getParameter("isUsedFilter")));
			filter.setFilterName(request.getParameter("filterName"));
			filter.setFilterDesc(request.getParameter("description"));
			filter.setUsedForDescription(Integer.parseInt(request.getParameter("isUsedDesc")));
			filter.setSortNo(Integer.parseInt(request.getParameter("sortNo")));

			filter.setCompanyId(compId);
			filter.setDelStatus(1);
			filter.setExInt1(0);
			filter.setExInt2(0);
			filter.setExInt3(0);
			filter.setExVar1("NA");
			filter.setExVar2("NA");
			filter.setExVar3("NA");

			MFilter res = Constants.getRestTemplate().postForObject(Constants.url + "saveFilter", filter,
					MFilter.class);

			if (res.getFilterId() > 0) {
				if (filterId == 0)
					session.setAttribute("successMsg", "Filter Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Filter Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Filter Type");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertFilterType : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showFilter/" + filterTypeId;

	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Update Filter
	@RequestMapping(value = "/editFilter", method = RequestMethod.GET)
	public String editFilter(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			int filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editFilter", "showFilter/" + filterTypeId, "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addFilter";

				int companyId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("filterId");
				String filterId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map = new LinkedMultiValueMap<>();
				map.add("filterId", Integer.parseInt(filterId));

				MFilter filter = Constants.getRestTemplate().postForObject(Constants.url + "getFilterById", map,
						MFilter.class);

				model.addAttribute("filter", filter);
				model.addAttribute("filterTypeId", filterTypeId);

				map = new LinkedMultiValueMap<>();
				map.add("filterTypeId", filterTypeId);

				FilterTypes filterType = Constants.getRestTemplate().postForObject(Constants.url + "getFilterTypeById",
						map, FilterTypes.class);

				model.addAttribute("title", "Edit " + filterType.getFilterTypeName());

				model.addAttribute("filterType", filterType.getFilterTypeName());

			}
		} catch (Exception e) {
			System.out.println("Execption in /editFilter : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 14-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Filter
	@RequestMapping(value = "/deleteFilter", method = RequestMethod.GET)
	public String deleteFilter(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		int filterTypeId = 0;
		try {
			filterTypeId = Integer.parseInt(request.getParameter("filterTypeId"));

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteFilter", "showFilter/" + filterTypeId, "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "redirect:/showFilter/" + filterTypeId;
				String base64encodedString = request.getParameter("filterId");
				String filterId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("filterId", Integer.parseInt(filterId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteFilterById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteFilter : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Franchise
	@RequestMapping(value = "/showFranchises", method = RequestMethod.GET)
	public String showFranchises(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showFranchises", "showFranchises", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				int companyId = (int) session.getAttribute("companyId");

				mav = "masters/franchiseList";

				List<Franchise> frList = new ArrayList<Franchise>();

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				frList = new ArrayList<Franchise>(Arrays.asList(frArr));

				for (int i = 0; i < frList.size(); i++) {

					frList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(frList.get(i).getFrId())));
				}

				model.addAttribute("frList", frList);

				model.addAttribute("title", "Franchise List");

				// model.addAttribute("imageUrl", Constants.showDocSaveUrl);
				Info add = AccessControll.checkAccess("showFranchises", "showFranchises", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showFranchises", "showFranchises", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showFranchises", "showFranchises", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showFranchises : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add UOM JSP Page
	@RequestMapping(value = "/newFranchise/{id}", method = RequestMethod.GET)
	public String newFranchise(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable int id) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newFranchise/" + id, "showFranchises", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addFranchise";

				int companyId = (int) session.getAttribute("companyId");

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				City[] cityArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCitiesByCompId", map,
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));

				model.addAttribute("cityList", cityList);

				if (id > 0) {
					map = new LinkedMultiValueMap<>();
					map.add("frId", id);
					Franchise franchise = Constants.getRestTemplate().postForObject(Constants.url + "getFranchiseById",
							map, Franchise.class);

					model.addAttribute("franchise", franchise);

				} else {
					Franchise franchise = new Franchise();

					map = new LinkedMultiValueMap<>();
					map.add("compId", companyId);

					CompMaster comp = Constants.getRestTemplate().postForObject(Constants.url + "getCompById", map,
							CompMaster.class);

					String coPrefix = comp.getCompanyPrefix();

					int frIdCnt = 0;
					map = new LinkedMultiValueMap<>();
					map.add("coPrefix", coPrefix);
					map.add("compId", companyId);
					frIdCnt = Constants.getRestTemplate().postForObject(Constants.url + "getFrCnt", map, Integer.class);

					frIdCnt = frIdCnt + 1;

					String getFrCode = coPrefix + "00" + frIdCnt;

					franchise.setFrCode(getFrCode);

					model.addAttribute("franchise", franchise);
				}

				model.addAttribute("title", "Add Franchise");
				model.addAttribute("frId", id);
			}
		} catch (Exception e) {
			System.out.println("Execption in /newFranchise : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Insert Franchise in database
	@RequestMapping(value = "/insertFranchise", method = RequestMethod.POST)
	public String insertFranchise(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		int savedFrId = 0;
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd ");
			String profileImage = null;

			int companyId = (int) session.getAttribute("companyId");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				System.err.println("In If ");

				profileImage = sf.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				System.err.println("In else ");
				profileImage = request.getParameter("editImg");

			}

			String pass = request.getParameter("pass");
			String password = pass;
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			int frId = Integer.parseInt(request.getParameter("frId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("frId", frId);
			Franchise getFr = Constants.getRestTemplate().postForObject(Constants.url + "getFranchiseById", map,
					Franchise.class);

			Franchise franchise = new Franchise();

			franchise.setFrId(frId);
			if (frId > 0) {
				franchise.setEditDateTime(sf.format(date));
			} else {
				franchise.setAddDateTime(sf.format(date));
			}

			franchise.setFrAddress(request.getParameter("address"));
			franchise.setFrCity(Integer.parseInt(request.getParameter("city")));
			franchise.setState(request.getParameter("state"));
			franchise.setFrCode(request.getParameter("frCode"));
			franchise.setFrContactNo(request.getParameter("mobNo"));
			franchise.setFrEmailId(request.getParameter("email"));
			franchise.setFrImage(profileImage);
			franchise.setFrName(request.getParameter("frName"));
			franchise.setFrPassword(hashtext);
			franchise.setOpeningDate(request.getParameter("openDate"));
			franchise.setOwnersBirthDay(request.getParameter("ownerDob"));
			franchise.setPincode(request.getParameter("pincode"));

			franchise.setIsActive(1);
			franchise.setDelStatus(1);
			franchise.setCompanyId(companyId);
			franchise.setCity("NA");

			if (frId > 0) {
				// FDA& GST Detail
				franchise.setFdaLicenseDateExp(getFr.getFdaLicenseDateExp());
				franchise.setFdaNumber(getFr.getFdaNumber());
				franchise.setGstNumber(getFr.getGstNumber());
				franchise.setGstType(getFr.getGstType());
				franchise.setPincodeWeServed(getFr.getPincodeWeServed());

				try {
					franchise.setNoOfKmAreaCover(getFr.getNoOfKmAreaCover());
					franchise.setShopsLatitude(getFr.getShopsLatitude());
					franchise.setShopsLogitude(getFr.getShopsLogitude());

				} catch (Exception e) {
					franchise.setNoOfKmAreaCover(0);
					franchise.setShopsLatitude(0);
					franchise.setShopsLogitude(0);
					e.printStackTrace();
				}

				// Bank Details
				franchise.setPanNo(getFr.getPanNo());
				franchise.setCoBankAccNo(getFr.getCoBankAccNo());
				franchise.setCoBankBranchName(getFr.getCoBankBranchName());
				franchise.setCoBankIfscCode(getFr.getCoBankIfscCode());
				franchise.setCoBankName(getFr.getCoBankName());
				franchise.setPaymentGetwayLink(getFr.getPaymentGetwayLink());
				franchise.setPaymentGetwayLinkSameAsParent(getFr.getPaymentGetwayLinkSameAsParent());
			}

			franchise.setExDate1(sfd.format(date));
			franchise.setExDate2(sfd.format(date));
			franchise.setExFloat1(0);
			franchise.setExFloat2(0);
			franchise.setExFloat3(0);
			franchise.setExFloat4(0);
			franchise.setExFloat5(0);
			franchise.setExInt1(0);
			franchise.setExInt2(0);
			franchise.setExInt3(0);
			franchise.setExVar1("NA");
			franchise.setExVar2("NA");
			franchise.setExVar3("NA");
			franchise.setExVar4("NA");
			franchise.setExVar5("NA");
			franchise.setExVar6("NA");
			franchise.setExVar7("NA");

			franchise.setUserId(userObj.getUserId());

			try {
				franchise.setFrRating(Float.parseFloat(request.getParameter("frRating")));
			} catch (Exception e) {
				franchise.setFrRating(0);
				e.printStackTrace();
			}

			Franchise res = Constants.getRestTemplate().postForObject(Constants.url + "saveFranchise", franchise,
					Franchise.class);

			if (res.getFrId() > 0) {
				savedFrId = res.getFrId();
				if (frId == 0)
					session.setAttribute("successMsg", "Franchise Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Franchise  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Franchise");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertFranchise : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/newFranchise/" + savedFrId;

	}

	@RequestMapping(value = "/insertFrFdaAndGst", method = RequestMethod.POST)
	public String insertFrFdaAndGst(HttpServletRequest request, HttpServletResponse response) {
		int savedFrId = 0;
		try {
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			int companyId = (int) session.getAttribute("companyId");

			int frId = Integer.parseInt(request.getParameter("frId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("frId", frId);
			Franchise getFr = Constants.getRestTemplate().postForObject(Constants.url + "getFranchiseById", map,
					Franchise.class);

			Franchise franchise = new Franchise();
			// FDA & GST Details
			franchise.setFrId(frId);
			franchise.setFdaLicenseDateExp(request.getParameter("fdaExpDate"));
			franchise.setFdaNumber(request.getParameter("fdaNo"));
			franchise.setGstNumber(request.getParameter("gstNo"));
			franchise.setGstType(request.getParameter("gstType"));
			franchise.setPincodeWeServed(request.getParameter("servePincode"));
			franchise.setState(request.getParameter("state"));

			try {
				franchise.setNoOfKmAreaCover(Float.parseFloat(request.getParameter("kmCover")));
				franchise.setShopsLatitude(Float.parseFloat(request.getParameter("latitude")));
				franchise.setShopsLogitude(Float.parseFloat(request.getParameter("longitude")));

			} catch (Exception e) {
				franchise.setNoOfKmAreaCover(0);
				franchise.setShopsLatitude(0);
				franchise.setShopsLogitude(0);
				e.printStackTrace();
			}

			if (frId > 0) {
				// Franchise Basic Details
				if (frId > 0) {
					franchise.setEditDateTime(sf.format(date));
				} else {
					franchise.setAddDateTime(getFr.getAddDateTime());
				}

				franchise.setFrAddress(getFr.getFrAddress());
				franchise.setFrCity(getFr.getFrCity());
				franchise.setState(getFr.getState());
				franchise.setFrCode(getFr.getFrCode());
				franchise.setFrContactNo(getFr.getFrContactNo());
				franchise.setFrEmailId(getFr.getFrEmailId());
				franchise.setFrImage(getFr.getFrImage());
				franchise.setFrName(getFr.getFrName());
				franchise.setFrPassword(getFr.getFrPassword());
				franchise.setOpeningDate(getFr.getOpeningDate());
				franchise.setOwnersBirthDay(getFr.getOwnersBirthDay());
				franchise.setPincode(getFr.getPincode());
				franchise.setUserId(userObj.getUserId());

				franchise.setIsActive(1);
				franchise.setDelStatus(1);
				franchise.setCompanyId(companyId);
				franchise.setCity("NA");

				// Bank Details
				franchise.setPanNo(getFr.getPanNo());
				franchise.setCoBankAccNo(getFr.getCoBankAccNo());
				franchise.setCoBankBranchName(getFr.getCoBankBranchName());
				franchise.setCoBankIfscCode(getFr.getCoBankIfscCode());
				franchise.setCoBankName(getFr.getCoBankName());
				franchise.setPaymentGetwayLink(getFr.getPaymentGetwayLink());
				franchise.setPaymentGetwayLinkSameAsParent(getFr.getPaymentGetwayLinkSameAsParent());
			}
			Franchise res = Constants.getRestTemplate().postForObject(Constants.url + "saveFranchise", franchise,
					Franchise.class);

			if (res.getFrId() > 0) {
				savedFrId = res.getFrId();
				if (frId == 0)
					session.setAttribute("successMsg", "Franchise Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Franchise  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Franchise");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertFranchise : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/newFranchise/" + savedFrId;

	}

	@RequestMapping(value = "/submitBankDtl", method = RequestMethod.POST)
	public String submitBankDtl(HttpServletRequest request, HttpServletResponse response) {

		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			User userObj = (User) session.getAttribute("userObj");

			int frId = Integer.parseInt(request.getParameter("frId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("frId", frId);
			Franchise getFr = Constants.getRestTemplate().postForObject(Constants.url + "getFranchiseById", map,
					Franchise.class);

			Franchise franchise = new Franchise();
			franchise.setFrId(frId);
			franchise.setCoBankAccNo(request.getParameter("accNo"));
			franchise.setCoBankBranchName(request.getParameter("branchName"));
			franchise.setCoBankIfscCode(request.getParameter("ifscCode"));
			franchise.setCoBankName(request.getParameter("coBankName"));
			franchise.setPaymentGetwayLink(request.getParameter("paymentGateWay"));
			franchise.setPaymentGetwayLinkSameAsParent(request.getParameter("samePayGateWay"));
			franchise.setPanNo(request.getParameter("panNo"));

			if (frId > 0) {
				// Franchise Basic Details
				franchise.setFrId(frId);
				if (frId > 0) {
					franchise.setEditDateTime(sf.format(date));
				} else {
					franchise.setAddDateTime(getFr.getAddDateTime());
				}

				franchise.setFrAddress(getFr.getFrAddress());
				franchise.setFrCity(getFr.getFrCity());
				franchise.setState(getFr.getState());
				franchise.setFrCode(getFr.getFrCode());
				franchise.setFrContactNo(getFr.getFrContactNo());
				franchise.setFrEmailId(getFr.getFrEmailId());
				franchise.setFrImage(getFr.getFrImage());
				franchise.setFrName(getFr.getFrName());
				franchise.setFrPassword(getFr.getFrPassword());
				franchise.setOpeningDate(getFr.getOpeningDate());
				franchise.setOwnersBirthDay(getFr.getOwnersBirthDay());
				franchise.setPincode(getFr.getPincode());
				franchise.setUserId(userObj.getUserId());

				franchise.setIsActive(1);
				franchise.setDelStatus(1);
				franchise.setCompanyId(companyId);
				franchise.setCity("NA");

				// FDA& GST Detail
				franchise.setFdaLicenseDateExp(getFr.getFdaLicenseDateExp());
				franchise.setFdaNumber(getFr.getFdaNumber());
				franchise.setGstNumber(getFr.getGstNumber());
				franchise.setGstType(getFr.getGstType());
				franchise.setPincodeWeServed(getFr.getPincodeWeServed());

				try {
					franchise.setNoOfKmAreaCover(getFr.getNoOfKmAreaCover());
					franchise.setShopsLatitude(getFr.getShopsLatitude());
					franchise.setShopsLogitude(getFr.getShopsLogitude());

				} catch (Exception e) {
					franchise.setNoOfKmAreaCover(0);
					franchise.setShopsLatitude(0);
					franchise.setShopsLogitude(0);
					e.printStackTrace();
				}
			}
			Franchise res = Constants.getRestTemplate().postForObject(Constants.url + "saveFranchise", franchise,
					Franchise.class);

			if (res.getFrId() > 0) {
				if (frId == 0)
					session.setAttribute("successMsg", "Franchise Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Franchise  Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Franchise");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertFranchise : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showFranchises";

	}

	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Franchise
	@RequestMapping(value = "/deleteFranchise", method = RequestMethod.GET)
	public String deleteFranchise(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteFranchise", "showFranchises", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "redirect:/showFranchises";
				int frId = Integer.parseInt(request.getParameter("frId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("frId", frId);

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteFranchiseById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteFranchise : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*----------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Language List
	@RequestMapping(value = "/showLanguage", method = RequestMethod.GET)
	public String addBasicMaster(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showLanguage", "showLanguage", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/languageList";

				User userObj = (User) session.getAttribute("userObj");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userObj.getCompanyId());
				Language[] langArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllLanguages", map,
						Language[].class);
				List<Language> langList = new ArrayList<Language>(Arrays.asList(langArr));

				for (int i = 0; i < langList.size(); i++) {

					langList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(langList.get(i).getLangId())));
				}

				model.addAttribute("langList", langList);

				model.addAttribute("title", "Language List");

				Info add = AccessControll.checkAccess("showLanguage", "showLanguage", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showLanguage", "showLanguage", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showLanguage", "showLanguage", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showLanguage : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect To Add Language Page
	@RequestMapping(value = "/addLanguage", method = RequestMethod.GET)
	public String addLanguage(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		Language lang = new Language();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("addLanguage", "showLanguage", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addLanguage";
				model.addAttribute("lang", lang);
				model.addAttribute("title", "Add Language");
			}
		} catch (Exception e) {
			System.out.println("Execption in /addLanguage : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/insertLanguage", method = RequestMethod.POST)
	public String insertLanguage(HttpServletRequest request, HttpServletResponse response) {
		Language lang = new Language();

		HttpSession session = request.getSession();
		User userObj = (User) session.getAttribute("userObj");

		try {
			int langId = Integer.parseInt(request.getParameter("lang_id"));

			lang.setDelStatus(1);
			lang.setExInt1(0);
			lang.setExInt2(0);
			lang.setExVar1("NA");
			lang.setExVar2("NA");
			lang.setCompanyId(userObj.getCompanyId());
			lang.setIsActive(Integer.parseInt(request.getParameter("language")));
			lang.setLangCode(request.getParameter("language_code").toUpperCase());
			lang.setLangId(langId);
			lang.setLangName(request.getParameter("language_name"));

			Language langRes = Constants.getRestTemplate().postForObject(Constants.url + "addLanguage", lang,
					Language.class);

			if (langRes.getLangId() > 0) {
				if (langId == 0)
					session.setAttribute("successMsg", "Language Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Language Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Language");
			}
		} catch (Exception e) {
			System.out.println("Execption in /addLanguage : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showLanguage";
	}

	@RequestMapping(value = "/getLangInfoByCode", method = RequestMethod.GET)
	@ResponseBody
	public Info getLangInfoByCode(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			String code = request.getParameter("code");
			int langId = Integer.parseInt(request.getParameter("langId"));

			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("code", code);
			map.add("langId", langId);
			map.add("compId", userObj.getCompanyId());

			Language langRes = Constants.getRestTemplate().postForObject(Constants.url + "getLanguageByCode", map,
					Language.class);

			if (langRes != null) {
				info.setError(false);
				info.setMsg("Language Found");
			} else {
				info.setError(true);
				info.setMsg("Language Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getLangInfoByCode : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Edit Language
	@RequestMapping(value = "/editLang", method = RequestMethod.GET)
	public String editLang(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editLang", "showLanguage", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addLanguage";

				User userObj = (User) session.getAttribute("userObj");

				String base64encodedString = request.getParameter("langId");
				String langId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("langId", Integer.parseInt(langId));
				map.add("compId", userObj.getCompanyId());

				Language lang = Constants.getRestTemplate().postForObject(Constants.url + "getLanguageById", map,
						Language.class);
				model.addAttribute("lang", lang);

				model.addAttribute("title", "Edit Language");

			}
		} catch (Exception e) {
			System.out.println("Execption in /editLang : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Language
	@RequestMapping(value = "/deleteLang", method = RequestMethod.GET)
	public String deleteLang(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteUser", "showLanguage", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				String base64encodedString = request.getParameter("langId");
				String langId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("langId", Integer.parseInt(langId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteLanguageById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
				mav = "redirect:/showLanguage";
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteLang : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*----------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 15-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Cities
	@RequestMapping(value = "/showCities", method = RequestMethod.GET)
	public String showCities(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showCities", "showCities", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/cityList";

				int compId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", compId);

				City[] cityArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCities", map,
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));

				for (int i = 0; i < cityList.size(); i++) {

					cityList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(cityList.get(i).getCityId())));
				}

				model.addAttribute("cityList", cityList);

				model.addAttribute("title", "City/Village List");

				Info add = AccessControll.checkAccess("showCities", "showCities", "0", "1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showCities", "showCities", "0", "0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showCities", "showCities", "0", "0", "0", "1", newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showCities : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add New City Page
	@RequestMapping(value = "/addNewCity", method = RequestMethod.GET)
	public String addNewCity(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		City city = new City();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("addNewCity", "showCities", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addCity";
				model.addAttribute("city", city);
				model.addAttribute("title", "Add City/Village");
			}
		} catch (Exception e) {
			System.out.println("Execption in /showCities : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getCityInfoByCode", method = RequestMethod.GET)
	@ResponseBody
	public Info getCityInfoByCode(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			String code = request.getParameter("code");
			int cityId = Integer.parseInt(request.getParameter("cityId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("code", code);
			map.add("cityId", cityId);

			City cityRes = Constants.getRestTemplate().postForObject(Constants.url + "getCityByCode", map, City.class);

			if (cityRes != null) {
				info.setError(false);
				info.setMsg("City Found");
			} else {
				info.setError(true);
				info.setMsg("City Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getCityInfoByCode : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = "/insertCity", method = RequestMethod.POST)
	public String insertCity(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User userObj = (User) session.getAttribute("userObj");

		City city = new City();
		int cityId = Integer.parseInt(request.getParameter("city_id"));

		try {
			city.setCityCode(request.getParameter("city_code").toUpperCase());
			city.setCityId(cityId);
			city.setCityName(request.getParameter("city_name"));
			city.setCompanyId(userObj.getCompanyId());
			city.setDelStatus(1);
			city.setDescription(request.getParameter("city_decp"));
			city.setExInt1(Integer.parseInt(request.getParameter("type")));
			city.setExInt2(0);
			city.setExVar1("NA");
			city.setExVar2("NA");
			city.setIsActive(Integer.parseInt(request.getParameter("city")));

			City cityRes = Constants.getRestTemplate().postForObject(Constants.url + "addCity", city, City.class);

			if (cityRes.getCityId() > 0) {
				if (cityId == 0)
					session.setAttribute("successMsg", "City/Village Saved Sucessfully");
				else
					session.setAttribute("successMsg", "City/Village Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save City/Village");
			}
		} catch (Exception e) {
			System.out.println("Execption in /insertCity : " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showCities";
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add New City Page
	@RequestMapping(value = "/editCity", method = RequestMethod.GET)
	public String editCity(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editUser", "showCities", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addCity";

				String base64encodedString = request.getParameter("cityId");
				String cityId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("cityId", Integer.parseInt(cityId));

				City city = Constants.getRestTemplate().postForObject(Constants.url + "getCityById", map, City.class);
				model.addAttribute("city", city);

				model.addAttribute("title", "Edit City/Village");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editLang : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete City
	@RequestMapping(value = "/deleteCity", method = RequestMethod.GET)
	public String deleteCity(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteUser", "showCities", "0", "0", "0", "1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				String base64encodedString = request.getParameter("cityId");
				String cityId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("cityId", Integer.parseInt(cityId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteCityById", map, Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
				mav = "redirect:/showCities";
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteCity : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Area List
	@RequestMapping(value = "/showArea", method = RequestMethod.GET)
	public String showArea(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showArea", "showArea", "1", "0", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/areaList";

				User userObj = (User) session.getAttribute("userObj");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userObj.getCompanyId());

				AreaCityList[] areaArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllAreaCityList",
						map, AreaCityList[].class);
				List<AreaCityList> areaList = new ArrayList<AreaCityList>(Arrays.asList(areaArr));

				for (int i = 0; i < areaList.size(); i++) {

					areaList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(areaList.get(i).getAreaId())));
				}
				model.addAttribute("areaList", areaList);
				model.addAttribute("title", "Area List");
				Info add = AccessControll.checkAccess("showArea", "showArea", "0", "1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showArea", "showArea", "0", "0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showArea", "showArea", "0", "0", "0", "1", newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showArea : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add New Area Page
	@RequestMapping(value = "/addNewArea", method = RequestMethod.GET)
	public String addNewArea(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		Area area = new Area();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("addNewArea", "showArea", "0", "1", "0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addArea";

				User userObj = (User) session.getAttribute("userObj");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userObj.getCompanyId());

				City[] cityArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllCitiesOnly", map,
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));

				model.addAttribute("cityList", cityList);
				model.addAttribute("area", area);
				model.addAttribute("title", "Add Area");
			}
		} catch (Exception e) {
			System.out.println("Execption in /addNewArea : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/insertArea", method = RequestMethod.POST)
	public String insertArea(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Area area = new Area();

			int areaId = Integer.parseInt(request.getParameter("area_id"));

			area.setAreaCode(request.getParameter("area_code"));
			area.setAreaId(areaId);
			area.setAreaName(request.getParameter("area_name"));
			area.setCityId(Integer.parseInt(request.getParameter("city_name")));
			area.setCompanyId(userObj.getCompanyId());
			area.setDelStatus(1);
			area.setDescription(request.getParameter("area_decp"));
			area.setExInt1(0);
			area.setExInt2(0);
			area.setExVar1("NA");
			area.setExVar2("NA");
			area.setIsActive(Integer.parseInt(request.getParameter("area")));
			area.setLatitude(request.getParameter("latitude"));
			area.setLongitude(request.getParameter("latitude"));
			area.setPincode(request.getParameter("area_pincode"));

			Area areaRes = Constants.getRestTemplate().postForObject(Constants.url + "addArea", area, Area.class);

			if (areaRes.getAreaId() > 0) {
				if (areaId == 0)
					session.setAttribute("successMsg", "Area Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Area Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Area");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertArea : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showArea";

	}

	@RequestMapping(value = "/editArea", method = RequestMethod.GET)
	public String editArea(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editArea", "showArea", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addArea";

				User userObj = (User) session.getAttribute("userObj");

				String base64encodedString = request.getParameter("areaId");
				String areaId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("areaId", Integer.parseInt(areaId));
				map.add("compId", userObj.getCompanyId());

				Area area = Constants.getRestTemplate().postForObject(Constants.url + "getAreaById", map, Area.class);
				model.addAttribute("area", area);

				City[] cityArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCitiesOnly",
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));

				model.addAttribute("cityList", cityList);

				model.addAttribute("isEdit", 1);
				model.addAttribute("title", "Edit Area");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editArea : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getAreaInfoByCode", method = RequestMethod.GET)
	@ResponseBody
	public Info getAreaInfoByCode(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			String code = request.getParameter("code");
			int areaId = Integer.parseInt(request.getParameter("areaId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("code", code);
			map.add("areaId", areaId);
			map.add("compId", userObj.getCompanyId());

			Area cityRes = Constants.getRestTemplate().postForObject(Constants.url + "getAreaByCode", map, Area.class);
			System.out.println("Area  ------  " + cityRes);
			if (cityRes != null) {
				info.setError(false);
				info.setMsg("Area Found");
			} else {
				info.setError(true);
				info.setMsg("Area Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getAreaInfoByCode : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Area
	@RequestMapping(value = "/deleteArea", method = RequestMethod.GET)
	public String deleteArea(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteArea", "showArea", "0", "0", "1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				User userObj = (User) session.getAttribute("userObj");

				String base64encodedString = request.getParameter("areaId");
				String areaId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("areaId", Integer.parseInt(areaId));
				map.add("compId", userObj.getCompanyId());

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteAreaById", map, Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
				mav = "redirect:/showArea";
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteArea : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getCityBycityId", method = RequestMethod.GET)
	@ResponseBody
	public City getCityByCityId(HttpServletRequest request, HttpServletResponse response) {

		City city = new City();
		try {

			int cityId = Integer.parseInt(request.getParameter("cityId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("cityId", cityId);
			Integer areaNo = Constants.getRestTemplate().postForObject(Constants.url + "getAreaByCityId", map,
					Integer.class);
			String no = String.format("%03d", (areaNo + 1));

			map.add("cityId", cityId);
			city = Constants.getRestTemplate().postForObject(Constants.url + "getCityById", map, City.class);

			city.setCityCode(city.getCityCode() + "-" + no);
			// System.out.println("City ------ "+city);

		} catch (Exception e) {
			System.out.println("Execption in /getCityBycityId : " + e.getMessage());
			e.printStackTrace();
		}
		return city;
	}

	/*-----------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Deliver Instruction
	@RequestMapping(value = "/showDeliveryInstructn", method = RequestMethod.GET)
	public String showDeliveryInstructions(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showDeliveryInstructn", "showDeliveryInstructn", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/delvInsructList";

				User userObj = (User) session.getAttribute("userObj");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userObj.getCompanyId());

				DeliveryInstruction[] delvArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllDeliveryInstructions", map, DeliveryInstruction[].class);
				List<DeliveryInstruction> delvList = new ArrayList<DeliveryInstruction>(Arrays.asList(delvArr));

				for (int i = 0; i < delvList.size(); i++) {

					delvList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(delvList.get(i).getInstruId())));
				}
				model.addAttribute("delvList", delvList);
				model.addAttribute("title", "Delivery Instruction List");

				Info add = AccessControll.checkAccess("showDeliveryInstructn", "showDeliveryInstructn", "0", "1", "0",
						"0", newModuleList);
				Info edit = AccessControll.checkAccess("showDeliveryInstructn", "showDeliveryInstructn", "0", "0", "1",
						"0", newModuleList);
				Info delete = AccessControll.checkAccess("showDeliveryInstructn", "showDeliveryInstructn", "0", "0",
						"0", "1", newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showDeliveryInstructn : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add New Delivery Instruction Page
	@RequestMapping(value = "/addDeliveryInstruction", method = RequestMethod.GET)
	public String addDeliveryInstructn(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		DeliveryInstruction instruct = new DeliveryInstruction();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("addDeliveryInstruction", "showDeliveryInstructn", "0", "1", "0",
					"0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addDeliveryInstructn";

				model.addAttribute("instruct", instruct);
				model.addAttribute("title", "Add Delivery Instruction");
			}
		} catch (Exception e) {
			System.out.println("Execption in /addDeliveryInstruction : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getCaptionInfo", method = RequestMethod.GET)
	@ResponseBody
	public Info getCaptionInfo(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			String caption = request.getParameter("caption");
			int instructId = Integer.parseInt(request.getParameter("instructId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("caption", caption);
			map.add("instructId", instructId);
			map.add("compId", userObj.getCompanyId());

			DeliveryInstruction captionRes = Constants.getRestTemplate()
					.postForObject(Constants.url + "getDeliveryInstructionByCaptn", map, DeliveryInstruction.class);
			System.out.println("captionRes  ------  " + captionRes);
			if (captionRes != null) {
				info.setError(false);
				info.setMsg("Caption Found");
			} else {
				info.setError(true);
				info.setMsg("Caption Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getCaptionInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = "/insertDeliveryInstruction", method = RequestMethod.POST)
	public String insertDeliveryInstruction(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			DeliveryInstruction instructn = new DeliveryInstruction();

			int instructId = Integer.parseInt(request.getParameter("instruct_id"));

			instructn.setInstruId(instructId);
			instructn.setInstructnCaption(request.getParameter("instruct_cap"));
			instructn.setCompanyId(userObj.getCompanyId());
			instructn.setDelStatus(1);
			instructn.setDescription(request.getParameter("instruct_decp"));
			instructn.setAllowToCopy(Integer.parseInt(request.getParameter("allowCopy")));
			instructn.setIsParent(Integer.parseInt(request.getParameter("isParent")));
			instructn.setExInt1(0);
			instructn.setExInt2(0);
			instructn.setExVar1("NA");
			instructn.setExVar2("NA");
			instructn.setIsActive(Integer.parseInt(request.getParameter("instruction")));

			DeliveryInstruction instructRes = Constants.getRestTemplate()
					.postForObject(Constants.url + "addDeliveryInstrunctn", instructn, DeliveryInstruction.class);

			if (instructRes.getInstruId() > 0) {
				if (instructId == 0)
					session.setAttribute("successMsg", "Delivery Instruction Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Delivery Instruction Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Delivery Instruction");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertDeliveryInstruction : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showDeliveryInstructn";

	}

	@RequestMapping(value = "/editDeliveryInsrtuctn", method = RequestMethod.GET)
	public String editDeliveryInsrtuctn(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editDeliveryInsrtuctn", "showDeliveryInstructn", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/addDeliveryInstructn";

				String base64encodedString = request.getParameter("instructId");
				String instructId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("instructId", Integer.parseInt(instructId));

				DeliveryInstruction instruct = Constants.getRestTemplate()
						.postForObject(Constants.url + "getDeliveryInstructionById", map, DeliveryInstruction.class);
				model.addAttribute("instruct", instruct);

				model.addAttribute("title", "Edit Delivery Instruction");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editDeliveryInsrtuctn : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Delivery Instructions
	@RequestMapping(value = "/deleteInstructn", method = RequestMethod.GET)
	public String deleteInstructn(HttpServletRequest request, HttpServletResponse response) {
		String mav = new String();
		HttpSession session = request.getSession();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteInstructn", "showDeliveryInstructn", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				String base64encodedString = request.getParameter("instructId");
				String instructId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("instructId", Integer.parseInt(instructId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteDeliveryInstructnById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteInstructn : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showDeliveryInstructn";
	}

	/*-------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Grievance Type List
	@RequestMapping(value = "/showGrievencesTypeIntructn", method = RequestMethod.GET)
	public String GrievencesTypeInstructn(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showGrievencesTypeIntructn", "showGrievencesTypeIntructn", "1", "0",
					"0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/grievanceTypeList";

				User userObj = (User) session.getAttribute("userObj");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userObj.getCompanyId());

				GrievencesTypeInstructn[] grievArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllGrievTypeInstruct", map, GrievencesTypeInstructn[].class);
				List<GrievencesTypeInstructn> grievList = new ArrayList<GrievencesTypeInstructn>(
						Arrays.asList(grievArr));

				for (int i = 0; i < grievList.size(); i++) {

					grievList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(grievList.get(i).getGrevTypeId())));
				}
				model.addAttribute("grievList", grievList);
				model.addAttribute("title", "Grievances Type Instruction List");

				Info add = AccessControll.checkAccess("showGrievencesTypeIntructn", "showGrievencesTypeIntructn", "0",
						"1", "0", "0", newModuleList);
				Info edit = AccessControll.checkAccess("showGrievencesTypeIntructn", "showGrievencesTypeIntructn", "0",
						"0", "1", "0", newModuleList);
				Info delete = AccessControll.checkAccess("showGrievencesTypeIntructn", "showGrievencesTypeIntructn",
						"0", "0", "0", "1", newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}

		} catch (Exception e) {
			System.out.println("Execption in /showGrievencesTypeIntructn : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add New Grievance Page
	@RequestMapping(value = "/addGrievanceTypInstruct", method = RequestMethod.GET)
	public String addGrievanceTypInstruct(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		GrievencesTypeInstructn griev = new GrievencesTypeInstructn();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("addGrievanceTypInstruct", "showGrievencesTypeIntructn", "0", "1",
					"0", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addGrievInstruct";

				model.addAttribute("griev", griev);
				model.addAttribute("title", "Add Grievances Type Instruction");
			}
		} catch (Exception e) {
			System.out.println("Execption in /addGrievanceTypInstruct : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/insertGrievanceTypeInstruction", method = RequestMethod.POST)
	public String insertGrievanceTypeInstruction(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			GrievencesTypeInstructn griev = new GrievencesTypeInstructn();

			int grievId = Integer.parseInt(request.getParameter("griev_id"));

			griev.setGrevTypeId(grievId);
			griev.setCaption(request.getParameter("griev_cap"));
			griev.setCompanyId(userObj.getCompanyId());
			griev.setDelStatus(1);
			griev.setDescription(request.getParameter("griev_decp"));
			griev.setAllowToCopy(Integer.parseInt(request.getParameter("allowCopy")));
			griev.setIsParent(Integer.parseInt(request.getParameter("isParent")));
			griev.setExInt1(0);
			griev.setExInt2(0);
			griev.setExVar1("NA");
			griev.setExVar2("NA");
			griev.setIsActive(Integer.parseInt(request.getParameter("grievance")));

			GrievencesTypeInstructn instructRes = Constants.getRestTemplate()
					.postForObject(Constants.url + "addGrievTypeInstruct", griev, GrievencesTypeInstructn.class);

			if (instructRes.getGrevTypeId() > 0) {
				if (grievId == 0)
					session.setAttribute("successMsg", "Grievances Type Instruction Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Grievances Type Instruction Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Grievances Type Instruction");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertGrievanceTypeInstruction : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showGrievencesTypeIntructn";

	}

	@RequestMapping(value = "/getGrievanceCaptionInfo", method = RequestMethod.GET)
	@ResponseBody
	public Info getGrievanceCaptionInfo(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			String caption = request.getParameter("caption");
			int grievTypeId = Integer.parseInt(request.getParameter("grievId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("caption", caption);
			map.add("grievTypeId", grievTypeId);
			map.add("compId", companyId);

			GrievencesTypeInstructn captionRes = Constants.getRestTemplate()
					.postForObject(Constants.url + "getGrievTypeInstructByCaptn", map, GrievencesTypeInstructn.class);
			System.out.println("captionRes  ------  " + captionRes);
			if (captionRes != null) {
				info.setError(false);
				info.setMsg("Caption Found");
			} else {
				info.setError(true);
				info.setMsg("Caption Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getGrievanceCaptionInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Edit Grievance
	@RequestMapping(value = "/editGrievanceTypeInsrtuctn", method = RequestMethod.GET)
	public String editGrievanceTypeInsrtuctn(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editGrievanceTypeInsrtuctn", "showGrievencesTypeIntructn", "0", "0",
					"1", "0", newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				int companyId = (int) session.getAttribute("companyId");

				mav = "masters/addGrievInstruct";

				String base64encodedString = request.getParameter("grievId");
				String grievTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievTypeId", Integer.parseInt(grievTypeId));
				map.add("compId", companyId);

				GrievencesTypeInstructn griev = Constants.getRestTemplate()
						.postForObject(Constants.url + "getGrievTypeInstructById", map, GrievencesTypeInstructn.class);

				model.addAttribute("griev", griev);

				model.addAttribute("title", "Edit Grievances Type Instruction");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editGrievanceTypeInsrtuctn : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Grievance
	@RequestMapping(value = "/deleteGrievanceType", method = RequestMethod.GET)
	public String deleteGrievanceType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String mav = new String();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteGrievanceType", "showGrievencesTypeIntructn", "0", "0", "0",
					"1", newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				String base64encodedString = request.getParameter("grievId");
				String grievTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievTypeId", Integer.parseInt(grievTypeId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteGrievTypeInstructById", map,
						Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
				mav = "redirect:/showGrievencesTypeIntructn";
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteGrievanceType : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	/*-------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Grievances
	@RequestMapping(value = "/showGrievences", method = RequestMethod.GET)
	public String showGrievences(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showGrievences", "showGrievences", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/grievanceList";

				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				GrievencesInstruction[] grievArr = Constants.getRestTemplate().postForObject(
						Constants.url + "getAllGrievancesInstructns", map, GrievencesInstruction[].class);
				List<GrievencesInstruction> grievList = new ArrayList<GrievencesInstruction>(Arrays.asList(grievArr));

				for (int i = 0; i < grievList.size(); i++) {

					grievList.get(i)
							.setExVar1(FormValidation.Encrypt(String.valueOf(grievList.get(i).getGrievanceId())));
				}
				model.addAttribute("grievList", grievList);
				model.addAttribute("title", "Grievances Instruction List");

				Info add = AccessControll.checkAccess("showGrievences", "showGrievences", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showGrievences", "showGrievences", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showGrievences", "showGrievences", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showGrievences : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add New Grievance Page
	@RequestMapping(value = "/addGrievanceInstructn", method = RequestMethod.GET)
	public String addGrievanceInstructn(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		GrievencesInstruction grievance = new GrievencesInstruction();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("addGrievanceInstructn", "showGrievences", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addGrievances";

				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				GrievencesTypeInstructn[] grievArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllGrievTypeInstruct", map, GrievencesTypeInstructn[].class);

				List<GrievencesTypeInstructn> grievList = new ArrayList<GrievencesTypeInstructn>(
						Arrays.asList(grievArr));
				model.addAttribute("grievList", grievList);

				model.addAttribute("grievance", grievance);
				model.addAttribute("title", "Add Grievances Instruction");
			}
		} catch (Exception e) {
			System.out.println("Execption in /addGrievanceInstructn : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/insertGrievanceInstruction", method = RequestMethod.POST)
	public String insertGrievanceInstruction(HttpServletRequest request, HttpServletResponse response) {

		try {
			GrievencesInstruction grievance = new GrievencesInstruction();

			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			int grievanceId = Integer.parseInt(request.getParameter("grievances_id"));

			grievance.setGrievanceId(grievanceId);
			grievance.setGrievenceTypeId(Integer.parseInt(request.getParameter("griev_type")));
			grievance.setCaption(request.getParameter("griev_cap"));
			grievance.setCompanyId(companyId);
			grievance.setDelStatus(1);
			grievance.setDescription(request.getParameter("griev_decp"));
			grievance.setAllowToCopy(Integer.parseInt(request.getParameter("allowCopy")));
			grievance.setIsParent(Integer.parseInt(request.getParameter("isParent")));
			grievance.setExInt1(0);
			grievance.setExInt2(0);
			grievance.setExVar1("NA");
			grievance.setExVar2("NA");
			grievance.setIsActive(Integer.parseInt(request.getParameter("grievance")));

			GrievencesInstruction grievanceRes = Constants.getRestTemplate()
					.postForObject(Constants.url + "addGrievance", grievance, GrievencesInstruction.class);

			if (grievanceRes.getGrievanceId() > 0) {
				if (grievanceId == 0)
					session.setAttribute("successMsg", "Grievances Instruction Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Grievances Instruction Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Grievances Instruction");
			}

		} catch (Exception e) {
			System.out.println("Execption in /insertGrievanceInstruction : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showGrievences";

	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Edit Grievance
	@RequestMapping(value = "/editGrievance", method = RequestMethod.GET)
	public String editGrievance(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("editGrievance", "showGrievences", "0", "0", "1", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addGrievances";

				int companyId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("grievId");
				String grievanceId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievanceId", Integer.parseInt(grievanceId));
				map.add("compId", companyId);

				GrievencesInstruction grievance = Constants.getRestTemplate()
						.postForObject(Constants.url + "getGrievanceInstructnById", map, GrievencesInstruction.class);
				model.addAttribute("grievance", grievance);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				GrievencesTypeInstructn[] grievArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getAllGrievTypeInstruct", map, GrievencesTypeInstructn[].class);

				List<GrievencesTypeInstructn> grievList = new ArrayList<GrievencesTypeInstructn>(
						Arrays.asList(grievArr));
				model.addAttribute("grievList", grievList);

				model.addAttribute("title", "Edit Grievances Instruction");
			}
		} catch (Exception e) {
			System.out.println("Execption in /editGrievance : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Delete Grievance
	@RequestMapping(value = "/deleteGrievance", method = RequestMethod.GET)
	public String deleteGrievance(HttpServletRequest request, HttpServletResponse response) {

		String mav = new String();
		HttpSession session = request.getSession();
		try {
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("deleteGrievance", "showGrievences", "0", "0", "0", "1",
					newModuleList);
			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				String base64encodedString = request.getParameter("grievId");
				String grievanceId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievanceId", Integer.parseInt(grievanceId));

				Info res = Constants.getRestTemplate().postForObject(Constants.url + "deleteGrievanceInstructnById",
						map, Info.class);

				if (!res.isError()) {
					session.setAttribute("successMsg", res.getMsg());
				} else {
					session.setAttribute("errorMsg", res.getMsg());
				}
				mav = "redirect:/showGrievences";
			}
		} catch (Exception e) {
			System.out.println("Execption in /deleteGrievance : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getGrievanceInfo", method = RequestMethod.GET)
	@ResponseBody
	public Info getGrievanceInfo(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();
		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			String caption = request.getParameter("caption");
			int grievanceId = Integer.parseInt(request.getParameter("grievancesId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("caption", caption);
			map.add("grievanceId", grievanceId);
			map.add("compId", companyId);

			GrievencesInstruction captionRes = Constants.getRestTemplate()
					.postForObject(Constants.url + "getGrievancenstructnByCaptn", map, GrievencesInstruction.class);

			if (captionRes != null) {
				info.setError(false);
				info.setMsg("Caption Found");
			} else {
				info.setError(true);
				info.setMsg("Caption Not Found");
			}
		} catch (Exception e) {
			System.out.println("Execption in /getGrievanceInfo : " + e.getMessage());
			e.printStackTrace();
		}
		return info;
	}

	/*-------------------------------------------------------------------------------------*/
	// Created By :- Mahendra Singh
	// Created On :- 21-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Show Sp Day Home Page
	@RequestMapping(value = "/showSpHomePages", method = RequestMethod.GET)
	public String showSpHomePages(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("showSpHomePages", "showSpHomePages", "1", "0", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {

				mav = "masters/spDayHomePageList";

				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				SpDayHomePage[] grievArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getSpDayHomePages", map, SpDayHomePage[].class);
				List<SpDayHomePage> spDayList = new ArrayList<SpDayHomePage>(Arrays.asList(grievArr));

				for (int i = 0; i < spDayList.size(); i++) {

					spDayList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(spDayList.get(i).getSpDayId())));
				}
				model.addAttribute("spDayList", spDayList);
				model.addAttribute("title", "Sp Day Home Page List");

				Info add = AccessControll.checkAccess("showSpHomePages", "showSpHomePages", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showSpHomePages", "showSpHomePages", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showSpHomePages", "showSpHomePages", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addAttribute("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addAttribute("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addAttribute("deleteAccess", 0);

				}
			}
		} catch (Exception e) {
			System.out.println("Execption in /showSpHomePages : " + e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 16-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Redirect to Add New Sp Day Home Page
	@RequestMapping(value = "/newSpDayHomePage", method = RequestMethod.GET)
	public String addSpDayHomePage(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		GrievencesInstruction grievance = new GrievencesInstruction();
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info view = AccessControll.checkAccess("newSpDayHomePage", "showSpHomePages", "0", "1", "0", "0",
					newModuleList);

			if (view.isError() == true) {

				mav = "accessDenied";

			} else {
				mav = "masters/addSpDayHomePage";

				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
						Franchise[].class);
				List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));

				model.addAttribute("frList", frList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				map.add("filterTypeId", 7);

				MFilter[] filterArr = Constants.getRestTemplate()
						.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
				List<MFilter> tagsList = new ArrayList<MFilter>(Arrays.asList(filterArr));

				model.addAttribute("tagsList", tagsList);

				SpDayHomePage spDay = new SpDayHomePage();
				
				Date date = new Date();
				SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy ");
				
				spDay.setFromDate(sfd.format(date));
				spDay.setToDate(sfd.format(date));
				
				model.addAttribute("spDay", spDay);

				model.addAttribute("title", "Add Sp Day Home Page");
			}
		} catch (Exception e) {
			System.out.println("Execption in /newSpDayHomePage : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

	// Created By :- Mahendra Singh
	// Created On :- 12-09-2020
	// Modified By :- NA
	// Modified On :- NA
	// Description :- Add User
	@RequestMapping(value = "/insertSpHomePages", method = RequestMethod.POST)
	public String insertSpHomePages(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") MultipartFile doc) {
		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
			String profileImage = null;

			int companyId = (int) session.getAttribute("companyId");

			if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

				System.err.println("In If ");

				profileImage = sf.format(date) + "_" + doc.getOriginalFilename();

				try {
					new ImageUploadController().saveUploadedFiles(doc, 1, profileImage);
				} catch (Exception e) {
				}

			} else {
				System.err.println("In else ");
				profileImage = request.getParameter("editImg");

			}

			SpDayHomePage spDay = new SpDayHomePage();

			int spDayId =Integer.parseInt(request.getParameter("spDayId"));
			
			if(spDayId>0) {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("spDayId", spDayId);
				SpDayHomePage res = Constants.getRestTemplate().postForObject(Constants.url + "getSpDayHomePageById",
						map, SpDayHomePage.class);
				
				spDay.setInsertDateTime(res.getInsertDateTime());
				spDay.setInsertUserId(res.getInsertUserId());
			}
			
			String frIdsStr = "";
			String[] frIds = request.getParameterValues("frId");
			if (frIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : frIds) {
					sb.append(s).append(",");
				}
				frIdsStr = sb.deleteCharAt(sb.length() - 1).toString();
				
			}
			
			String tagsStr = "";
			String[] tagIds = request.getParameterValues("tag");
			if (tagIds.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : tagIds) {
					sb.append(s).append(",");
				}
				tagsStr = sb.deleteCharAt(sb.length() - 1).toString();
				
			}
			
			String strDate = request.getParameter("dates");
			String string = strDate;
			String[] parts = string.split("to");
			String part1 = parts[0]; 
			String part2 = parts[1]; 
			
			spDay.setSpDayId(spDayId);		
			spDay.setSpdayName(request.getParameter("spdayName"));
			spDay.setCaptionOnProductPage(request.getParameter("captionOnProductPage"));			
			spDay.setFrIds(frIdsStr);
			spDay.setTagIds(tagsStr);
			spDay.setFromDate(part1);
			spDay.setToDate(part2);
			spDay.setFromTime(request.getParameter("fromTime"));
			spDay.setToTime(request.getParameter("toTime")); 
			
			if(spDayId==0) {
				spDay.setInsertDateTime(sf.format(date));
				spDay.setInsertUserId(userObj.getUserId());	
			}else {				
				spDay.setUpdateDateTime(sf.format(date));
				spDay.setUpdateUserId(userObj.getUserId());				
			}
			spDay.setSpdayCaptionHomePage(request.getParameter("spdayCaption"));
			spDay.setSpdayCaptionImageHomePage(profileImage);	
			spDay.setSortNo(Integer.parseInt(request.getParameter("sortNo")));
			
			spDay.setIsActive(Integer.parseInt(request.getParameter("isActive")));
			spDay.setCompanyId(companyId);
			spDay.setDelStatus(1);
			spDay.setExInt1(0);
			spDay.setExInt2(0);
			spDay.setExVar1("NA");
			spDay.setExVar2("NA");
			

			SpDayHomePage res = Constants.getRestTemplate().postForObject(Constants.url + "saveSpDayHomePage", spDay, SpDayHomePage.class);

			if (res.getSpDayId() > 0) {
				if (spDayId == 0)
					session.setAttribute("successMsg", "Special Day Home Page Saved Sucessfully");
				else
					session.setAttribute("successMsg", "Special Day Home Page Update Sucessfully");
			} else {
				session.setAttribute("errorMsg", "Failed to Save Special Day Home Page");
			}
		} catch (Exception e) {
			System.out.println("Execption in /insertSpHomePages : " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showSpHomePages";

	}
	
	// Created By :- Mahendra Singh
		// Created On :- 16-09-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Redirect to Edit Sp Day Home Page
		@RequestMapping(value = "/editSpday", method = RequestMethod.GET)
		public String editSpday(HttpServletRequest request, HttpServletResponse response, Model model) {

			String mav = new String();
			GrievencesInstruction grievance = new GrievencesInstruction();
			try {
				HttpSession session = request.getSession();
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("editSpday", "showSpHomePages", "0", "1", "0", "0",
						newModuleList);

				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					mav = "masters/addSpDayHomePage";

					int companyId = (int) session.getAttribute("companyId");

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("compId", companyId);

					Franchise[] frArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFranchises", map,
							Franchise[].class);
					List<Franchise> frList = new ArrayList<Franchise>(Arrays.asList(frArr));

					model.addAttribute("frList", frList);

					map = new LinkedMultiValueMap<>();
					map.add("compId", companyId);
					map.add("filterTypeId", 7);

					MFilter[] filterArr = Constants.getRestTemplate()
							.postForObject(Constants.url + "getFiltersListByTypeId", map, MFilter[].class);
					List<MFilter> tagsList = new ArrayList<MFilter>(Arrays.asList(filterArr));

					model.addAttribute("tagsList", tagsList);

					
					String base64encodedString = request.getParameter("spDayId");
					String spDayId = FormValidation.DecodeKey(base64encodedString);
					
					map = new LinkedMultiValueMap<>();
					map.add("spDayId", spDayId);
					SpDayHomePage spDay = Constants.getRestTemplate().postForObject(Constants.url + "getSpDayHomePageById",
							map, SpDayHomePage.class);
					
					List<Integer> tagIds = new ArrayList<>();
					try {
						if (!spDay.getTagIds().isEmpty()) {
							tagIds = Stream.of(spDay.getTagIds().split(",")).map(Integer::parseInt)
									.collect(Collectors.toList());
						}
					} catch (Exception e) {
					}
					
					
					List<Integer> frIds = new ArrayList<>();
					try {
						if (!spDay.getFrIds().isEmpty()) {
							frIds = Stream.of(spDay.getFrIds().split(",")).map(Integer::parseInt)
									.collect(Collectors.toList());
						}
					} catch (Exception e) {
					}
					
					model.addAttribute("tagIds", tagIds);
					model.addAttribute("frIds", frIds);
					
					model.addAttribute("spDay", spDay);

					model.addAttribute("imgPath", Constants.showDocSaveUrl);
					model.addAttribute("title", "Edit Sp Day Home Page");
				}
			} catch (Exception e) {
				System.out.println("Execption in /editSpday : " + e.getMessage());
				e.printStackTrace();
			}
			return mav;
		}
		
		// Created By :- Mahendra Singh
		// Created On :- 21-09-2020
		// Modified By :- NA
		// Modified On :- NA
		// Description :- Delete Sp Day Home Page
		@RequestMapping(value = "/deleteSpDayHomePage", method = RequestMethod.GET)
		public String deleteSpDay(HttpServletRequest request, HttpServletResponse response) {

			String mav = new String();
			HttpSession session = request.getSession();
			try {
				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info view = AccessControll.checkAccess("deleteSpDayHomePage", "showSpHomePages", "0", "0", "0", "1",
						newModuleList);
				if (view.isError() == true) {

					mav = "accessDenied";

				} else {
					String base64encodedString = request.getParameter("spDayId");
					String spDayId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("spDayId", Integer.parseInt(spDayId));

					Info res =  Constants.getRestTemplate().postForObject(Constants.url + "deleteSpDayHomePage",
							map, Info.class);

					if (!res.isError()) {
						session.setAttribute("successMsg", res.getMsg());
					} else {
						session.setAttribute("errorMsg", res.getMsg());
					}
					
					mav = "redirect:/showSpHomePages";
				}
			} catch (Exception e) {
				System.out.println("Execption in /deleteSpDayHomePage : " + e.getMessage());
				e.printStackTrace();
			}
			return mav;
		}
}
