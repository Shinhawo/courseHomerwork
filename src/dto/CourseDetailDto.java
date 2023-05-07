package dto;

import java.util.List;

import vo.AcademyCourse;
import vo.AcademyStudent;

public class CourseDetailDto {

	private AcademyCourse academyCourse;
	private List<AcademyStudent> students;
	
	public CourseDetailDto () {}

	public AcademyCourse getAcademyCourse() {
		return academyCourse;
	}

	public void setAcademyCourse(AcademyCourse academyCourse) {
		this.academyCourse = academyCourse;
	}

	public List<AcademyStudent> getStudents() {
		return students;
	}

	public void setStudents(List<AcademyStudent> students) {
		this.students = students;
	}
	
	
}
