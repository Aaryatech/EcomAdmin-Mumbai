package com.ats.ecomadmin.controller;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ecomadmin.commons.AccessControll;
import com.ats.ecomadmin.commons.CommonUtility;
import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.GetUser;
import com.ats.ecomadmin.model.Info;
import com.ats.ecomadmin.model.User;
import com.ats.ecomadmin.model.acrights.AccessRightModule;
import com.ats.ecomadmin.model.acrights.AccessRightModuleList;
import com.ats.ecomadmin.model.acrights.AssignRoleDetailList;
import com.ats.ecomadmin.model.acrights.CreatedRoleList;
import com.ats.ecomadmin.model.acrights.ModuleJson;
import com.ats.ecomadmin.model.acrights.SubModuleJson;

@Controller 
@Scope("session")
public class AccessRightController {

	public AccessRightModuleList accessRightModuleList;
	int isError = 0;

	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public ModelAndView accessDenied(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessDenied");
		try {

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/showCreateRole", method = RequestMethod.GET)
	public ModelAndView showAccessRight(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("acc_right/createRole");
		try {
			accessRightModuleList = Constants.getRestTemplate().getForObject(Constants.url + "getAllModuleAndSubModule",
					AccessRightModuleList.class);
			model.addObject("moduleList", accessRightModuleList.getAccessRightModuleList());
			model.addObject("title", "Create Role");
			isError = 0;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/getSubmoduleList", method = RequestMethod.GET)
	public @ResponseBody List<Integer> getSubmoduleList(HttpServletRequest request, HttpServletResponse response) {

		List<Integer> list = new ArrayList<>();
		try {

			int moduleId = Integer.parseInt(request.getParameter("moduleId"));
			for (int i = 0; i < accessRightModuleList.getAccessRightModuleList().size(); i++) {

				if (accessRightModuleList.getAccessRightModuleList().get(i).getModuleId() == moduleId) {

					for (int j = 0; j < accessRightModuleList.getAccessRightModuleList().get(i)
							.getAccessRightSubModuleList().size(); j++) {

						list.add(accessRightModuleList.getAccessRightModuleList().get(i).getAccessRightSubModuleList()
								.get(j).getSubModuleId());
					}
					break;
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;

	}

	@RequestMapping(value = "/showRoleList", method = RequestMethod.GET)
	public ModelAndView showRoleList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("acc_right/showRoleList");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info info1 = AccessControll.checkAccess("showRoleList", "showRoleList", "1", "0", "0", "0", newModuleList);

			if (info1.isError() == false) {
				CreatedRoleList createdRoleList = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllAccessRole", CreatedRoleList.class);
				System.out.println("Access List " + createdRoleList.toString());
				model.addObject("createdRoleList", createdRoleList.getAssignRoleDetailList());
				model.addObject("title", "Roles List");

				Info add = AccessControll.checkAccess("showRoleList", "showRoleList", "0", "1", "0", "0",
						newModuleList);
				Info edit = AccessControll.checkAccess("showRoleList", "showRoleList", "0", "0", "1", "0",
						newModuleList);
				Info delete = AccessControll.checkAccess("showRoleList", "showRoleList", "0", "0", "0", "1",
						newModuleList);

				if (add.isError() == false) {
					// System.out.println(" add Accessable ");
					model.addObject("addAccess", 0);

				}
				if (edit.isError() == false) {
					// System.out.println(" edit Accessable ");
					model.addObject("editAccess", 0);
				}
				if (delete.isError() == false) {
					// System.out.println(" delete Accessable ");
					model.addObject("deleteAccess", 0);

				}
			} else {
				model = new ModelAndView("accessDenied");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
	public String deleteFlavour(HttpServletRequest request, HttpServletResponse response) {

		int roleId = Integer.parseInt(request.getParameter("accRole"));
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("roleId", roleId);
		HttpSession session = request.getSession();
		int usrCnt = Constants.getRestTemplate().postForObject(Constants.url + "getUserCntByRoleId", map,
				Integer.class);

		if (usrCnt > 0) {
			session.setAttribute("errorMsg",
					"You cannot delete this role," + usrCnt + " Users assigned for this role.");
			return "redirect:/showRoleList";
		} else {
			map.add("roleId", roleId);
			Info errorResponse = Constants.getRestTemplate().postForObject(Constants.url + "deleteRole", map,
					Info.class);
			System.out.println(errorResponse.toString());

			if (errorResponse.isError()) {
				session.setAttribute("errorMsg", "Failed to Delete");
				return "redirect:/showRoleList";

			} else {
				session.setAttribute("successMsg", "Deleted Successfully");
				return "redirect:/showRoleList";
			}
		}
	}

	@RequestMapping(value = "/submitCreateRole", method = RequestMethod.POST)
	public String submitAssignRole(HttpServletRequest request, HttpServletResponse response) {

		List<AccessRightModule> accessRightModule = accessRightModuleList.getAccessRightModuleList();

		List<ModuleJson> moduleJsonList = new ArrayList<>();
		HttpSession session = request.getSession();
		try {

			for (int i = 0; i < accessRightModule.size(); i++) {

				ModuleJson moduleJson = new ModuleJson();
				int isPresent = 0;

				List<SubModuleJson> subModuleJsonList = new ArrayList<>();

				for (int j = 0; j < accessRightModule.get(i).getAccessRightSubModuleList().size(); j++) {

					SubModuleJson subModuleJson = new SubModuleJson();

					String view = request
							.getParameter(accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
									+ "view" + accessRightModule.get(i).getModuleId());

					/* System.out.println("view " + view); */
					try {
						if (view.equals("1")) {
							isPresent = 1;

							subModuleJson.setView(String.valueOf(view));
							subModuleJson.setSubModuleId(
									accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleId());
							subModuleJson.setModuleId(accessRightModule.get(i).getModuleId());
							subModuleJson.setSubModulName(
									accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModulName());
							subModuleJson.setSubModuleDesc(
									accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleDesc());
							subModuleJson.setSubModuleMapping(accessRightModule.get(i).getAccessRightSubModuleList()
									.get(j).getSubModuleMapping());
							subModuleJson.setOrderBy(
									accessRightModule.get(i).getAccessRightSubModuleList().get(j).getOrderBy());

							try {
								int add = Integer.parseInt(request.getParameter(
										accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
												+ "add" + accessRightModule.get(i).getModuleId()));
								subModuleJson.setAddApproveConfig(String.valueOf(add));
							} catch (Exception e) {
								subModuleJson.setAddApproveConfig(String.valueOf(0));
							}

							try {
								int edit = Integer.parseInt(request.getParameter(
										accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
												+ "edit" + accessRightModule.get(i).getModuleId()));
								subModuleJson.setEditReject(String.valueOf(edit));
							} catch (Exception e) {
								subModuleJson.setEditReject(String.valueOf(0));
							}

							try {
								int delete = Integer.parseInt(request.getParameter(
										accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleId()
												+ "delete" + accessRightModule.get(i).getModuleId()));
								subModuleJson.setDeleteRejectApprove(String.valueOf(delete));
							} catch (Exception e) {
								subModuleJson.setDeleteRejectApprove(String.valueOf(0));
							}

							subModuleJsonList.add(subModuleJson);

						}
					} catch (Exception e) {

					}

				}

				if (isPresent == 1) {
					moduleJson.setModuleId(accessRightModule.get(i).getModuleId());
					moduleJson.setModuleName(accessRightModule.get(i).getModuleName());
					moduleJson.setModuleDesc(accessRightModule.get(i).getModuleDesc());
					moduleJson.setOrderBy(accessRightModule.get(i).getOrderBy());
					moduleJson.setIconDiv(accessRightModule.get(i).getIconDiv());
					moduleJson.setSubModuleJsonList(subModuleJsonList);
					moduleJsonList.add(moduleJson);
				}

			}

			if (moduleJsonList != null && !moduleJsonList.isEmpty()) {
				String roleName = request.getParameter("roleName");
				AssignRoleDetailList assignRoleDetailList = new AssignRoleDetailList();
				ObjectMapper mapper = new ObjectMapper();
				try {

					try {
						int roleId = Integer.parseInt(request.getParameter("roleId"));
						assignRoleDetailList.setRoleId(roleId);
					} catch (Exception e) {

					}

					String newsLetterJSON = mapper.writeValueAsString(moduleJsonList);

					System.out.println("JSON  " + newsLetterJSON);
					assignRoleDetailList.setRoleJson(newsLetterJSON);

				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}

				assignRoleDetailList.setRoleName(roleName);
				assignRoleDetailList.setDelStatus(1);
				Info info = Constants.getRestTemplate().postForObject(Constants.url + "saveAssignRole",
						assignRoleDetailList, Info.class);

				if (info.isError() == false) {
					session.setAttribute("successMsg", "Role Saved Successfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Role");
				}
			}

			System.out.println("saveAssignRole " + moduleJsonList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showRoleList";
	}

	@RequestMapping(value = "/editAccessRole/{roleId}", method = RequestMethod.GET)
	public ModelAndView editAccessRole(@PathVariable("roleId") int roleId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("acc_right/editRole");

		try {
			accessRightModuleList = Constants.getRestTemplate().getForObject(Constants.url + "getAllModuleAndSubModule",
					AccessRightModuleList.class);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("roleId", roleId);

			AssignRoleDetailList editRole = Constants.getRestTemplate().postForObject(Constants.url + "getRoleByRoleId",
					map, AssignRoleDetailList.class);
			// System.out.println("Access List " + accessRightModuleList.toString());
			// model.addObject("allModuleList",
			// accessRightModuleList.getAccessRightModuleList());
			model.addObject("editRole", editRole);

			ObjectMapper om = new ObjectMapper();
			ModuleJson[] moduleJson = om.readValue(editRole.getRoleJson(), ModuleJson[].class);

			// ModuleJson[] moduleJson = new Gson().fromJson(editRole.getRoleJson(),
			// ModuleJson[].class);
			List<ModuleJson> moduleJsonList = new ArrayList<ModuleJson>(Arrays.asList(moduleJson));

			// System.out.println("List" +
			// accessRightModuleList.getAccessRightModuleList());

			for (int i = 0; i < accessRightModuleList.getAccessRightModuleList().size(); i++) {

				for (int j = 0; j < moduleJsonList.size(); j++) {

					if (accessRightModuleList.getAccessRightModuleList().get(i).getModuleId() == moduleJsonList.get(j)
							.getModuleId()) {

						System.out.println("match Module "
								+ accessRightModuleList.getAccessRightModuleList().get(i).getModuleName());

						for (int k = 0; k < accessRightModuleList.getAccessRightModuleList().get(i)
								.getAccessRightSubModuleList().size(); k++) {

							for (int m = 0; m < moduleJsonList.get(j).getSubModuleJsonList().size(); m++) {

								if (accessRightModuleList.getAccessRightModuleList().get(i)
										.getAccessRightSubModuleList().get(k).getSubModuleId() == moduleJsonList.get(j)
												.getSubModuleJsonList().get(m).getSubModuleId()) {

									System.out.println(
											"match sub Module " + accessRightModuleList.getAccessRightModuleList()
													.get(i).getAccessRightSubModuleList().get(k).getSubModulName());

									accessRightModuleList.getAccessRightModuleList().get(i)
											.getAccessRightSubModuleList().get(k)
											.setAddApproveConfig(Integer.parseInt(moduleJsonList.get(j)
													.getSubModuleJsonList().get(m).getAddApproveConfig()));
									accessRightModuleList.getAccessRightModuleList().get(i)
											.getAccessRightSubModuleList().get(k).setView(Integer.parseInt(
													moduleJsonList.get(j).getSubModuleJsonList().get(m).getView()));
									accessRightModuleList.getAccessRightModuleList().get(i)
											.getAccessRightSubModuleList().get(k)
											.setEditReject(Integer.parseInt(moduleJsonList.get(j).getSubModuleJsonList()
													.get(m).getEditReject()));
									accessRightModuleList.getAccessRightModuleList().get(i)
											.getAccessRightSubModuleList().get(k)
											.setDeleteRejectApprove(Integer.parseInt(moduleJsonList.get(j)
													.getSubModuleJsonList().get(m).getDeleteRejectApprove()));
									break;
								}

							}

						}

						break;
					}

				}

			}

			model.addObject("title", "Edit Access Role");
			model.addObject("allModuleList", accessRightModuleList.getAccessRightModuleList());
			isError = 0;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return model;
	}

	// Need to use Sachin 13-08-19
	@RequestMapping(value = "/showAssignRole", method = RequestMethod.GET)
	public ModelAndView showAssignRloe(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("acc_right/assignRole");
//ass_role_test/assignRole
		try {
			model.addObject("title", "Assign Role");

			accessRightModuleList = Constants.getRestTemplate().getForObject(Constants.url + "getAllModuleAndSubModule",
					AccessRightModuleList.class);

			GetUser[] empArray = Constants.getRestTemplate().getForObject(Constants.url + "/getUserListForAssignRole",
					GetUser[].class);
			List<GetUser> empList = new ArrayList<>(Arrays.asList(empArray));

			CreatedRoleList createdRoleList = Constants.getRestTemplate()
					.getForObject(Constants.url + "getAllAccessRole", CreatedRoleList.class);

			// System.out.println("userList List " + userList.toString());
			model.addObject("empList", empList);
			model.addObject("createdRoleList", createdRoleList.getAssignRoleDetailList());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitAssignedRole", method = RequestMethod.POST)
	public String submitAssignedRole(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		String[] empIds = request.getParameterValues("empIds");

		try {

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < empIds.length; i++) {
				sb = sb.append(empIds[i] + ",");
			}

			String empIdList = sb.toString();
			empIdList = empIdList.substring(0, empIdList.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("userIdList", empIdList);
			// userIdList of List<Integer> Type
			map.add("roleId", roleId);
			map.add("makerUserId", (int) session.getAttribute("userId"));
			map.add("makerDttime", CommonUtility.getCurrentYMDDateTime());

			Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateRoleOfUser", map, Info.class);
			if (info.isError() == false) {
				session.setAttribute("successMsg", "Role Assigned Successfully");
			} else {
				session.setAttribute("errorMsg", "Failed Assign Role");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "redirect:/showAssignRole";
	}

	@RequestMapping(value = "/showAssignUserDetail/{userId}/{roleId}/{userName}/{roleName}", method = RequestMethod.GET)
	public ModelAndView showAssignUserDetail(@PathVariable int userId, @PathVariable int roleId,
			@PathVariable String userName, @PathVariable String roleName, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessRight/viewAssignRoleDetails");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("userId", userId);
		ParameterizedTypeReference<List<ModuleJson>> typeRef = new ParameterizedTypeReference<List<ModuleJson>>() {
		};
		ResponseEntity<List<ModuleJson>> responseEntity = Constants.getRestTemplate()
				.exchange(Constants.url + "getRoleJson", HttpMethod.POST, new HttpEntity<>(map), typeRef);

		List<ModuleJson> newModuleList = responseEntity.getBody();

		List<AccessRightModule> accessRightModuleListRes = accessRightModuleList.getAccessRightModuleList();

		for (int i = 0; i < accessRightModuleListRes.size(); i++) {
			for (int j = 0; j < newModuleList.size(); j++) {
				if (newModuleList.get(j).getModuleId() == accessRightModuleListRes.get(i).getModuleId()) {
					for (int l = 0; l < accessRightModuleListRes.get(i).getAccessRightSubModuleList().size(); l++) {
						boolean flag = false;
						for (int m = 0; m < newModuleList.get(j).getSubModuleJsonList().size(); m++) {
							if (accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleId() == newModuleList.get(j).getSubModuleJsonList().get(m)
											.getSubModuleId()) {
								flag = true;
							}
						}
						if (flag == false) {
							SubModuleJson sub = new SubModuleJson();
							sub.setSubModuleId(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleId());
							sub.setView("hidden");
							sub.setSubModuleMapping(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleMapping());
							sub.setEditReject("hidden");
							sub.setSubModuleDesc(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleDesc());
							sub.setSubModulName(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModulName());
							sub.setType(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l).getType());
							sub.setModuleId(
									accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l).getModuleId());
							sub.setDeleteRejectApprove("hidden");
							sub.setAddApproveConfig("hidden");
							newModuleList.get(j).getSubModuleJsonList().add(sub);
						}
					}

				}

			}

		}
		model.addObject("moduleJsonList", newModuleList);
		model.addObject("userName", userName);
		model.addObject("roleName", roleName);
		model.addObject("roleId", roleId);
		model.addObject("title", "View Access Role");

		return model;
	}

}
