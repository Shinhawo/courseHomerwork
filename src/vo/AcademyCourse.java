package vo;

import java.util.Date;

public class AcademyCourse {

	private int courseNo;
	private String name;
	private int courseQuato;
	private int courseReqCnt;
	private String status;
	private Date courseCreateDate;
	private String teacherId;
	
	public AcademyCourse() {}
	

	public int getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(int courseNo) {
		this.courseNo = courseNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCourseQuato() {
		return courseQuato;
	}

	public void setCourseQuato(int courseQuato) {
		this.courseQuato = courseQuato;
	}

	public int getCourseReqCnt() {
		return courseReqCnt;
	}

	public void setCourseReqCnt(int courseReqCnt) {
		this.courseReqCnt = courseReqCnt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCourseCreateDate() {
		return courseCreateDate;
	}

	public void setCourseCreateDate(Date courseCreateDate) {
		this.courseCreateDate = courseCreateDate;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
	@Override
	public String toString() {
		return "AcademyCourse [courseNo=" + courseNo + ", name=" + name + ", courseQuato=" + courseQuato
				+ ", courseReqCnt=" + courseReqCnt + ", status=" + status + ", courseCreateDate=" + courseCreateDate
				+ ", teacherId=" + teacherId + "]";
	}	
	
}
