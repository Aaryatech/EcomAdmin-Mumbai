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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import com.ats.ecomadmin.model.Info;
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
	// Descriprion :- Show UOM List
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

				int compId = (int) session.getAttribute("compId");
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
	// Descriprion :- Redirect to Add UOM JSP Page
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
	// Descriprion :- Insert UOM in database
	@RequestMapping(value = "/insertUom", method = RequestMethod.POST)
	public String insertUom(HttpServletRequest request, HttpServletResponse response) {

		try {
			Uom uom = new Uom();
			HttpSession session = request.getSession();

			int uomId = Integer.parseInt(request.getParameter("uomId"));
			int compId = (int) session.getAttribute("compId");

			uom.setAllowToCopy(0);
			uom.setCompanyId(compId);
			uom.setDelStatus(1);
			uom.setExInt1(0);
			uom.setExInt2(0);
			uom.setExInt3(0);
			uom.setExVar1("NA");
			uom.setExVar2("NA");
			uom.setExVar3("NA");
			uom.setExVar4("NA");
			uom.setIsActive(Integer.parseInt(request.getParameter("active_uom")));
			uom.setIsParent(0);
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
	// Descriprion :- Update UOM
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
	// Descriprion :- Delete UOM
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
	// Descriprion :- Show Tax List
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

				int compId = (int) session.getAttribute("compId");
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
	// Descriprion :- Redirect to Add Tax JSP Page
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
	// Descriprion :- Insert Tax
	@RequestMapping(value = "/insertTax", method = RequestMethod.POST)
	public String insertTax(HttpServletRequest request, HttpServletResponse response) {

		try {
			Tax tax = new Tax();
			HttpSession session = request.getSession();

			int taxId = Integer.parseInt(request.getParameter("taxId"));
			int compId = (int) session.getAttribute("compId");

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
			tax.setAllowToCopy(0);
			tax.setCompanyId(compId);
			tax.setDelStatus(1);
			tax.setExInt1(0);
			tax.setExInt2(0);
			tax.setExInt3(0);
			tax.setExVar1("NA");
			tax.setExVar2("NA");
			tax.setExVar3("NA");
			tax.setIsActive(Integer.parseInt(request.getParameter("active_tax")));
			tax.setIsParent(0);
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
	// Descriprion :- Update Tax
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
	// Descriprion :- Delete Tax
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
	// Descriprion :- Show All Users
	@RequestMapping(value = "/showUsers", method = RequestMethod.GET)
	public String showMnUsers(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
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
	// Descriprion :- Redirect To Add User page
	@RequestMapping(value = "/addNewUser", method = RequestMethod.GET)
	public String addNewUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		User user = new User();
		try {
			mav = "masters/addUser";

			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);

			UserTypeMaster[] userTypeArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllUserTypes",
					UserTypeMaster[].class);
			List<UserTypeMaster> usrTypList = new ArrayList<UserTypeMaster>(Arrays.asList(userTypeArr));

			model.addAttribute("userTypeList", usrTypList);

			model.addAttribute("user", user);
			model.addAttribute("title", "Add User");

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
	// Descriprion :- Add User
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
			user.setDeptId(Integer.parseInt(request.getParameter("user_type")));

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
	// Descriprion :- Update User
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public String editUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			mav = "masters/addUser";

			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");

			UserTypeMaster[] userTypeArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllUserTypes",
					UserTypeMaster[].class);
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
	// Descriprion :- Delete User
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		try {

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

		} catch (Exception e) {
			System.out.println("Execption in /deleteUser : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showUsers";
	}

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
	// Descriprion :- Show All Users Types
	@RequestMapping(value = "/showCategoryList", method = RequestMethod.GET)
	public String categoryList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			
			mav = "masters/categoryList";

			List<Category> catList = new ArrayList<>();
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", companyId);
			
			Category[] catArr = Constants.getRestTemplate().getForObject(Constants.url + "getAllCategory",
					Category[].class);
			catList = new ArrayList<Category>(Arrays.asList(catArr));

			model.addAttribute("catList", catList);

			model.addAttribute("imageUrl", Constants.showDocSaveUrl);
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
	// Descriprion :- Redirect To Add Category Page
	@RequestMapping(value = "/newCategory", method = RequestMethod.GET)
	public String newCategory(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();
		
		try {
			mav = "masters/addCategory";
			Category cat = new Category();
			
			model.addAttribute("cat", cat);
			model.addAttribute("title", "Add Category");

		} catch (Exception e) {
			System.out.println("Execption in /newCategory : " + e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}

}
