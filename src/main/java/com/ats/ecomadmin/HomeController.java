package com.ats.ecomadmin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.CompMaster;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.Uom;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "login";
	}

	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public String loginProcess(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.err.println("req loginProcess " + request.toString());
		String mav = new String();
		HttpSession session = request.getSession();

		try {
			String token = request.getParameter("token");
			String key = (String) session.getAttribute("generatedKey");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			if (token.trim().equals(key.trim())) {

				// System.err.println("Key matched");

				String name = request.getParameter("username");
				String password = request.getParameter("password");

				if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {

					mav = "redirect:/";
					session.setAttribute("errorMsg", "Login Failed - User name or password can not be null");
				} else {

					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] messageDigest = md.digest(password.getBytes());
					BigInteger number = new BigInteger(1, messageDigest);

					String hashtext = number.toString(16);
					System.out.println("Password---------->" + hashtext);

					map.add("userName", name);
					map.add("pass", hashtext);
					// map.add("pass", password);

					Object checkLogin = Constants.getRestTemplate()
							.postForObject(Constants.url + "checkUserNamePassForLogin", map, Object.class);
					ObjectMapper objMapper = new ObjectMapper();

					Info info = objMapper.convertValue(checkLogin, Info.class);

					if (info.isError() == false) {

						User userObj = objMapper.readValue(info.getResponseObject1(), User.class);
						// existing user login send to welcome page/dash board.

						mav = "home";

						map = new LinkedMultiValueMap<String, Object>();
						map.add("roleId", userObj.getRoleId());

						try {
							ParameterizedTypeReference<List<ModuleJson>> typeRef = new ParameterizedTypeReference<List<ModuleJson>>() {
							};
							ResponseEntity<List<ModuleJson>> responseEntity = Constants.getRestTemplate().exchange(
									Constants.url + "getRoleJsonByRoleId", HttpMethod.POST, new HttpEntity<>(map),
									typeRef);

							List<ModuleJson> newModuleList = responseEntity.getBody();

							session.setAttribute("newModuleList", newModuleList);

						} catch (Exception e) {
							System.err.println("Access Right get Exception  " + e.getMessage());
						}

						map = new LinkedMultiValueMap<>();
						map.add("compId", userObj.getCompanyId());

						CompMaster comp = Constants.getRestTemplate()
								.postForObject(Constants.url + "getCompanyByCompanyId", map, CompMaster.class);

						session.setAttribute("userId", userObj.getUserId());
						session.setAttribute("userObj", userObj);
						session.setAttribute("companyId", userObj.getCompanyId());

						session.setAttribute("company", comp);

					} else {
						// Login Failed
						// show msg in jsp

						mav = "redirect:/";
						session.setAttribute("errorMsg", info.getMsg());

					}
				}
			}
		} catch (Exception e) {
			mav = "redirect:/";
			session.setAttribute("errorMsg", "Login Failed");
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Locale locale, Model model) {

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/setSubModId", method = RequestMethod.GET)
	public @ResponseBody void setSubModId(HttpServletRequest request, HttpServletResponse response) {
		int subModId = Integer.parseInt(request.getParameter("subModId"));
		int modId = Integer.parseInt(request.getParameter("modId"));
		HttpSession session = request.getSession();
		session.setAttribute("sessionModuleId", modId);
		session.setAttribute("sessionSubModuleId", subModId);
		session.removeAttribute("exportExcelList");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {

		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/sessionTimeOut", method = RequestMethod.GET)
	public String sessionTimeOut(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request, HttpServletResponse response, Model model) {

		try {
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("userObj");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("userId", userObj.getUserId());
			User user = Constants.getRestTemplate().postForObject(Constants.url + "getUserById", map, User.class);

			model.addAttribute("userId", user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "acc_right/updatePassword";
	}

	@RequestMapping(value = "/renewPassword", method = RequestMethod.POST)
	public String renewPassword(HttpServletRequest request, HttpServletResponse response) {

		try {

			HttpSession session = request.getSession();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();			

			int userId = Integer.parseInt(request.getParameter("userId"));

			String password = request.getParameter("new_password");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			map.add("userId", userId);
			map.add("newPassword", hashtext);

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "updateUserPassword", map,
					Info.class);
			if (!info.isError()) {
				session.setAttribute("successMsg", info.getMsg());
			} else {
				session.setAttribute("errorMsg", info.getMsg());
			}

		} catch (Exception e) {
			System.out.println("Execption in /renewPassword : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/logout";

	}

	@RequestMapping(value = "/showForgetPass", method = RequestMethod.GET)
	public String showForgetPass(HttpServletRequest request, HttpServletResponse response) {

		return "login_fpass";
	}

	Instant start = null;

	@RequestMapping(value = "/getUserInfoByMobNo", method = RequestMethod.POST)
	public String getUserInfoByMobNo(HttpServletRequest request, HttpServletResponse response, Model model) {
		Info info = new Info();
		String mav = new String();
		try {
			HttpSession session = request.getSession();

			String mobEmail = request.getParameter("mobEmail");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("mobEmail", mobEmail);

			User user = Constants.getRestTemplate().postForObject(Constants.url + "getMnUserDetailByMobNo", map,
					User.class);
			System.err.println("User Info-----------" + user);
			if (user != null) {
				mav = "otpPage";
				model.addAttribute("contact", user.getUserMobileNo());
				info.setError(false);
				info.setMsg("User Found");
				System.err.println(info);

				start = Instant.now();
				;
			} else {
				info.setError(true);
				info.setMsg("User Not Found");
				System.err.println(info);
				session.setAttribute("invalidMob", "Invalid Mobile No.");
				mav = "login_fpass";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}

	@RequestMapping(value = "/otpVerification", method = RequestMethod.POST)
	public String OTPVerificationByContact(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		System.err.println("Hiii  OTPVerification  ");
		Info info = new Info();
		String mav = new String();
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			String otp = request.getParameter("otp");

			map.add("otp", otp);

			User user = Constants.getRestTemplate().postForObject(Constants.url + "verifyOTP", map, User.class);
			// System.err.println("OTP User--------------"+user);

			if (user.getUserId() == 0) {

				session.setAttribute("errorMsg", "Invalid OTP.");
				mav = "login_fpass";
				model.addAttribute("msg", "Incorrect OTP");

			} else {
				System.err.println("User" + user);
				mav = "changePass";
				model.addAttribute("userId", user.getUserId());

			}

		} catch (Exception e) {
			System.err.println("Exce in otpVerification  " + e.getMessage());
			e.printStackTrace();
		}

		return mav;

	}

}
