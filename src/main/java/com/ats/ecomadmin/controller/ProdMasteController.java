package com.ats.ecomadmin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class ProdMasteController {
	
	@RequestMapping(value = "/showAddProduct", method = RequestMethod.GET)
	public ModelAndView showAddProduct(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("product/addProd");
		
		try {
			model.addObject("isEdit", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
		
	}
	
}
