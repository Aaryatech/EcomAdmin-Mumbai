package com.ats.ecomadmin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.User;
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
		
		model.addAttribute("serverTime", formattedDate );
		
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

				System.err.println("Key matched");
			}
			
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

				map.add("userName", name);
				map.add("pass", hashtext);
				//map.add("pass", password);
				
				Object checkLogin = Constants.getRestTemplate()
						.postForObject(Constants.url + "checkUserNamePassForLogin", map, Object.class);
				ObjectMapper objMapper = new ObjectMapper();

				Info info = objMapper.convertValue(checkLogin, Info.class);

				if (info.isError() == false) {

					User userObj = objMapper.readValue(info.getResponseObject1(), User.class);

					if (userObj.getIsEnrolled() == 0) {
						// new User First time login, send to change for password.
						mav = "redirect:/changePassPage";
					} else {
						// existing user login send to welcome page/dash board.
					
						mav = "redirect:/getPage/3";
					}
					session.setAttribute("userId", userObj.getUserId());
					session.setAttribute("userObj", userObj);
				} else {
					// Login Failed
					// show msg in jsp

					mav = "redirect:/";
					session.setAttribute("errorMsg", info.getMsg());

				}
			}
		} catch (Exception e) {
			mav = "redirect:/";
			session.setAttribute("errorMsg", "Login Failed");
			e.printStackTrace();
		}

		return mav;
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

	
}
