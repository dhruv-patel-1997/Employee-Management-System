package com.empmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmanagement.dao.SalaryDAO;
import com.empmanagement.domain.Earnings;

@Service
public class EarningCalculationServiceImpl implements EarningCalculationService {

	@Autowired
	SalaryDAO salaryDAO;

	double hra = 0;
	double monthlyAllowances = 0;
	double shiftAllowances = 0;

	@Override
	public Earnings calculateTotalEarnings(double basicPay, Long empId) {

		Earnings earnings = new Earnings();
		earnings.setBasic(basicPay);
		earnings.setHra(getHRAForEmp(basicPay));
		earnings.setAllowances(getMonthlyAllowances(empId));
		earnings.setShiftAllowance(getShiftAllowance(empId));

		return earnings;

	}

	@Override
	public double getMonthlyAllowances(long empId) {
		monthlyAllowances = salaryDAO.getMonthlyAllowance(empId);

		return monthlyAllowances;
	}
	
	@Override
	public double getHRAForEmp(double basicPay) {
		hra = 0.3 * basicPay;

		return hra;
	}

	@Override
	public double getShiftAllowance(long empId) {

		shiftAllowances = salaryDAO.getShiftAllowance(empId);

		return shiftAllowances;

	}

}