package com.empmanagement.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.empmanagement.dao.ISalaryDAO;

/**
 * This class in the data access implementation for fetching and updating salary
 * and allowance related data in the database
 * 
 * @author Priti Sri Pandey
 *
 */
@Repository
public class SalaryDAOImpl implements ISalaryDAO {

	private static final String QUERY_BASIC_SAL = "SELECT salary.basic FROM salary INNER JOIN employee ON employee.grade = salary.grade where employee.empId = ?  ";
	private static final String QUERY_MONTHLY_ALLOWANCE = "SELECT redeemedMAllowance FROM employee where empId = ?  ";
	private static final String QUERY_SHIFT_ALLOWANCE = "SELECT shiftAllowance FROM employee where empId = ?  ";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public double getBasicSalary(Long empId) {
		double basic = 0;
		try {

			basic = jdbcTemplate.queryForObject(QUERY_BASIC_SAL, Double.class, empId);

		} catch (Exception e) {

			System.err.println(e);
		}

		return basic;
	}

	@Override
	public double getMonthlyAllowance(Long empId) {
		double monthlyAllowance = 0;
		try {

			monthlyAllowance = jdbcTemplate.queryForObject(QUERY_MONTHLY_ALLOWANCE, Double.class, empId);

		} catch (NullPointerException e) {
			monthlyAllowance = 0;
		} catch (Exception e) {

			System.err.println(e);
		}

		return monthlyAllowance;
	}

	@Override
	public double getShiftAllowance(Long empId) {
		double shiftAllowance = 0;
		try {

			shiftAllowance = jdbcTemplate.queryForObject(QUERY_SHIFT_ALLOWANCE, Double.class, empId);

		} catch (NullPointerException e) {
			shiftAllowance = 0;
		} catch (Exception e) {

			System.err.println(e);
		}

		return shiftAllowance;
	}

}
