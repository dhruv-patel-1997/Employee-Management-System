package com.empmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmanagement.dao.InvestmentDeclarationDAO;
import com.empmanagement.domain.Deductions;
import com.empmanagement.domain.Earnings;
import com.empmanagement.domain.Salary;
import com.empmanagement.domain.Taxes;

@Service
public class SalaryServiceImpl implements SalaryService {

	@Autowired
	EarningCalculationService earningService;
	
	@Autowired
	TaxCalculationService taxService;
	
	@Autowired
	InvestmentDeclarationDAO dao;
	
	double basicPay = 0;
	double totalEarning = 0;
	double totalDeductions = 0;
	double netPay = 0;
	Earnings earnings;
	
	
	@Override
	public Salary getSalaryForEmployee(Long empId) {
		
		earnings = earningService.calculateTotalEarnings(getBasicPayForEmployee(empId));
		totalEarning = earnings.getBasic() + earnings.getHra() + earnings.getAllowances();		
		totalDeductions = taxService.caculateIncomeTax(totalEarning) + taxService.calculateProfessionalTax(totalEarning);
		
		netPay = totalEarning - totalDeductions;
		
		Taxes tax = new Taxes();
		tax.setIncomeTax(taxService.caculateIncomeTax(totalEarning));
		tax.setProfessionalTax( taxService.calculateProfessionalTax(totalEarning));
		
		Deductions deduction = new Deductions();
		deduction.setTax(tax);
		
		Salary salary = new Salary();
		salary.setEarnings(earnings);
		salary.setNetPay(netPay);
		salary.setDeductions(deduction);
		
		return salary;
		

		
	}

	@Override
	public double getBasicPayForEmployee(Long empId) {
		basicPay = dao.getBasicSalary(empId);
		return basicPay;
	}

}
