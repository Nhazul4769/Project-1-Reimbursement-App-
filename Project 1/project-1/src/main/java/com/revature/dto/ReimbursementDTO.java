package com.revature.dto;
 
import java.util.Objects;

public class ReimbursementDTO {
	
	private String reimburseResolved; 
	private String reimburseStatus;

	public ReimbursementDTO() {
		super();
	}
	
	public ReimbursementDTO(String reimbursementResolved, String reimburseStatus) {
		super();
		this.reimburseResolved = reimburseResolved;
		this.reimburseStatus = reimburseStatus;
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

	@Override
	public int hashCode() {
		return Objects.hash(reimburseResolved, reimburseStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementDTO other = (ReimbursementDTO) obj;
		return Objects.equals(reimburseResolved, other.reimburseResolved)
				&& Objects.equals(reimburseStatus, other.reimburseStatus);
	}

	@Override
	public String toString() {
		return "ReimbursementDTO [reimburseResolved=" + reimburseResolved + ", reimburseStatus=" + reimburseStatus
				+ "]";
	}
	
}