package com.ats.ecomadmin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
public class ErrorHandler {
	@RequestMapping(value = "errors", method = RequestMethod.GET)
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

	ModelAndView errorPage = new ModelAndView("error404");
	String errorMsg = "";
	int httpErrorCode = getErrorCode(httpRequest);

	switch (httpErrorCode) {
	case 400: {
	errorMsg = "Http Error Code: 400. Bad Request";
	break;
	}
	case 401: {
	errorMsg = "Http Error Code: 401. Unauthorized";
	break;
	}
	case 404: {
	errorMsg = "Http Error Code: 404. Resource not found";
	break;
	}
	case 500: {
	errorMsg = "Http Error Code: 500. Internal Server Error";
	break;
	}
	}
	errorPage.addObject("errorMsg", errorMsg);
	return errorPage;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		System.err.println(" Req Status Code " +(Integer) httpRequest.getAttribute("javax.servlet.error.status_code"));
		
	return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}
}