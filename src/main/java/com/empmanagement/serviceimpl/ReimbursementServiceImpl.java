package com.empmanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmanagement.dao.IReimbursementDao;
import com.empmanagement.daoimpl.ReimbursementDaoImpl;
import com.empmanagement.domain.ReimbursementDetails;
import com.empmanagement.service.IReimbursementService;
/**
 *This class contains the bsiness logic for Reimbursement Approval of Employee
 *
 * @author Prarthanaben Parmar
 *
 */


@Service
public class ReimbursementServiceImpl implements IReimbursementService {

	@Autowired
	IReimbursementDao reimbursementDao = new ReimbursementDaoImpl();
	
	static final int REIMBDECLINE = 0;
	static final double PERC = 0.1;
	static final String APPROVED = "approved";
	static final String DECLINED = "declined";

	private String updateReim;
	private String allow;
	
	@Override
	public String getAllRequests() {	

		List<ReimbursementDetails> details = reimbursementDao.getReimbursementDetails();
		for (ReimbursementDetails reimbDet : details) {

			IReimbursementService object = new ReimbursementServiceImpl();
		 	String status = reimbDet.getStatus();
			Long employeeID = reimbDet.getEmployeeId();
			int reimbAmount = reimbDet.getReimbursementAmount();
			
			if(status == null){				
				String grade = reimbursementDao.getGrade(employeeID);
				int baseSalary = reimbursementDao.getBasicSalary(grade);
				boolean valid = object.validity(baseSalary, reimbDet.getReimbursementAmount());
				if(valid){					
					updateReim = reimbursementDao.updateReimb(employeeID,APPROVED);
					allow = reimbursementDao.updateApprovedAllowance(employeeID,reimbAmount);
					if(updateReim == "success" && allow == "success") {
						continue;
					}
					else {
						return "error";
					}
					}
				else {					
					updateReim = reimbursementDao.updateReimb(reimbDet.getEmployeeId(),DECLINED);
					allow = reimbursementDao.updateApprovedAllowance(reimbDet.getEmployeeId(),REIMBDECLINE);
					if(updateReim == "success" && allow == "success") {
						continue;
					}
					else {
						return "error";
					}
					}
				}
			}
		return "success";
		}


	public boolean validity(int basic, int reimbAmount) {
		
		double reimbLimit = basic*PERC;
		if(reimbAmount <= reimbLimit) {
			return true;
		}
		return false;
	}

	@Override
	public String saveAppliedReimbursement(Long empId, Long appliedReimbursementAmt) {

		String dbSaveStatus = reimbursementDao.updateAppliedReimbursement(empId, appliedReimbursementAmt);
		return dbSaveStatus;
	}

}