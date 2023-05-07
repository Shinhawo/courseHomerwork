package dto;

import java.util.Date;

public class RegistrationDto {

	private int regNo;
	private Date createDate;
	private String regCanceled;
	private String courseName;
	
	public RegistrationDto() {}

	public int getRegNo() {
		return regNo;
	}

	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRegCanceled() {
		return regCanceled;
	}

	public void setRegCanceled(String regCanceled) {
		this.regCanceled = regCanceled;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	};
	
	
}
