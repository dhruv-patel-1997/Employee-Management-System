package com.empmanagement.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.empmanagement.domain.EmployeeInfo;
import com.empmanagement.service.IEmployeeDirectoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Employee directory controller
 * @author Neel Patel
 */
@Controller
public class EmployeeDirectoryController {

	@Autowired
	private IEmployeeDirectoryService employeeDirectoryService;

	private Logger logger = LoggerFactory.getLogger(EmployeeDirectoryController.class);

	/**
	 * Retrieves List of employees based on query params
	 * @param session HttpSession
	 * @param model Model
	 * @param name name query param
	 * @param role role query param
	 * @param dept dept query param
	 * @return Template name
	 */
	@GetMapping("/ems/employee-directory")
	public String getEmployees(HttpSession session, Model model,
			@RequestParam(name = "name", defaultValue = "%", required = false) String name,
			@RequestParam(name = "role", defaultValue = "%", required = false) String role,
			@RequestParam(name = "dept", defaultValue = "%", required = false) String dept) {
		Long employeeId = (Long) session.getAttribute("EMP_ID");

		if (employeeId == null) {
			return "redirect:/ems/login";
		}

		List<String> roles = employeeDirectoryService.getAllRoles();
		model.addAttribute("roles", roles);
		List<String> depts = employeeDirectoryService.getAllDepts();
		model.addAttribute("depts", depts);
		List<EmployeeInfo> employeeInfos = employeeDirectoryService.getEmployeeInfos(name, role, dept);
		model.addAttribute("employeeInfos", employeeInfos);
		
		logger.info("List of employee infos retrieved.");
		return "employee-directory";
	}

}
