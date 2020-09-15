package com.ats.ecomadmin.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.commons.FormValidation;
import com.ats.ecomadmin.model.Category;
import com.ats.ecomadmin.model.FilterTypes;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.MFilter;
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
			System.out.println("---------------------" + request.getParameter("sgstPer"));
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

				MFilter[] filterArr = Constants.getRestTemplate().postForObject(Constants.url + "getAllFilter", map,
						MFilter[].class);
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

}
