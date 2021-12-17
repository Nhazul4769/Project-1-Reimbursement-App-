package com.revature.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement {

	private int reimburseId;
	private int reimburseAmount;
	private String reimburseSubmitted; 
	private String reimburseResolved; 
	private String reimburseStatus;
	private String reimburseType;
	private String reimburseDescription;
	private int financeManagerId;
	private int employeeId;

	public Reimbursement() {
		super();
	}

	public Reimbursement(int reimburseId, int reimburseAmount, String reimburseSubmitted, String reimburseResolved,
			String reimburseStatus, String reimburseType, String reimburseDescription,
			int employeeId, int financeManagerId) {
		super();
		this.reimburseId = reimburseId;
		this.reimburseAmount = reimburseAmount;
		this.reimburseSubmitted = reimburseSubmitted;
		this.reimburseResolved = reimburseResolved;
		this.reimburseStatus = reimburseStatus; 
		this.reimburseType = reimburseType;
		this.reimburseDescription = reimburseDescription;
		this.employeeId = employeeId;
		this.financeManagerId = financeManagerId;
	}

	public int getReimburseId() {
		return reimburseId;
	}

	public void setReimburseId(int reimburseId) {
		this.reimburseId = reimburseId;
	}

	public int getReimburseAmount() {
		return reimburseAmount;
	}

	public void setReimburseAmount(int reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}

	public String getReimburseSubmitted() {
		return reimburseSubmitted;
	}

	public void setReimburseSubmitted(String reimburseSubmitted) {
		this.reimburseSubmitted = reimburseSubmitted;
	}

	public String getReimburseResolved() {
		return reimburseResolved;
	}

	public void setReimburseResolved(String reimburseResolved) {
		this.reimburseResolved = reimburseResolved;
	}

	public String getReimburseStatus() {
		return reimburseStatus;
	}

	public void setReimburseStatus(String reimburseStatus) {
		this.reimburseStatus = reimburseStatus;
	}

	public String getReimburseType() {
		return reimburseType;
	}

	public void setReimburseType(String reimburseType) {
		this.reimburseType = reimburseType;
	}

	public String getReimburseDescription() {
		return reimburseDescription;
	}

	public void setReimburseDescription(String reimburseDescription) {
		this.reimburseDescription = reimburseDescription;
	}

	public int getFinanceManagerId() {
		return financeManagerId;
	}

	public void setFinanceManagerId(int financeManagerId) {
		this.financeManagerId = financeManagerId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, financeManagerId, reimburseAmount, reimburseDescription, reimburseId,
				reimburseResolved, reimburseStatus, reimburseSubmitted, reimburseType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return employeeId == other.employeeId && financeManagerId == other.financeManagerId
				&& reimburseAmount == other.reimburseAmount
				&& Objects.equals(reimburseDescription, other.reimburseDescription) && reimburseId == other.reimburseId
				&& Objects.equals(reimburseResolved, other.reimburseResolved)
				&& Objects.equals(reimburseStatus, other.reimburseStatus)
				&& Objects.equals(reimburseSubmitted, other.reimburseSubmitted)
				&& Objects.equals(reimburseType, other.reimburseType);
	}

	@Override
	public String toString() {
		return "Reimbursement [reimburseId=" + reimburseId + ", reimburseAmount=" + reimburseAmount
				+ ", reimburseSubmitted=" + reimburseSubmitted + ", reimburseResolved=" + reimburseResolved
				+ ", reimburseStatus=" + reimburseStatus + ", reimburseType=" + reimburseType
				+ ", reimburseDescription=" + reimburseDescription + ", financeManagerId=" + financeManagerId
				+ ", employeeId=" + employeeId + "]";
	}
}