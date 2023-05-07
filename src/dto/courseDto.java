package dto;

public class courseDto {

	private int courseNo;
	private int courseQuato;
	private int courseReqCnt;
	private String teacher_name;
	private String course_name;
	
	public courseDto () {}

	public int getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(int courseNo) {
		this.courseNo = courseNo;
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

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	
}
