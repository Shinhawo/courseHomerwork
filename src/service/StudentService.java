package service;

import java.util.List;

import javax.swing.plaf.multi.MultiInternalFrameUI;

import controller.LoginUser;
import dao.RegistrationsDao;
import dao.StudentDao;
import dao.courseDao;
import dto.RegistrationDto;
import dto.courseDto;
import vo.AcademyCourse;
import vo.AcademyCourseRegistration;
import vo.AcademyStudent;

public class StudentService {

	private StudentDao studentDao = new StudentDao();
	private courseDao courseDao = new courseDao();
	private RegistrationsDao registrationsDao = new RegistrationsDao();
	
	
	
	public void registerStudent(AcademyStudent student) {
		
		AcademyStudent savedStudent = studentDao.getStudentById(student.getId());
		if(savedStudent != null) {
			throw new RuntimeException("["+student.getId()+"]는 이미 사용중인 아이디 입니다.");
		}
		
		studentDao.insertStudent(student);
	}
	
	
	public LoginUser login(String id, String password, String type) {
		
		AcademyStudent savedStudent = studentDao.getStudentById(id);
		if(savedStudent == null) {
			throw new RuntimeException("가입되어 있지 않은 아이디 입니다.");
		}
		
		if(!savedStudent.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
		
		return new LoginUser(savedStudent.getId(), savedStudent.getPassword(), savedStudent.getName(), type);
	}
	
	
	public List<courseDto> getAllCourses(){
		
		return courseDao.getCourses();

	}

	
	public void registerCourse(int courseNo, String studentId) {
		
//		List<AcademyCourseRegistration> savedRegistrations =
//				registrationsDao.getCourseReg(studentId);
//		if(savedRegistrations.isEmpty()) {
//			throw new RuntimeException("등록된 신청정보가 존재하지 않습니다.");
//		}
//		
		AcademyCourseRegistration savedRegistrations =
				registrationsDao.getCourseReg(courseNo,studentId);
		
//		if(savedRegistrations.getCourseNo() == courseNo && savedRegistrations.getStudentId() == studentId) {
//			throw new RuntimeException("신청한 강의를 다시 신청하실 수 없습니다.");
//		}
//		
		
		registrationsDao.insertCourseReg(courseNo, studentId);
		
		AcademyCourse course = courseDao.getCousersByNo(courseNo);
		int courseReqCnt = course.getCourseReqCnt() + 1;
		course.setCourseReqCnt(courseReqCnt);
		course.setStatus(course.getStatus());
		if(courseReqCnt == course.getCourseQuato()) {
			course.setStatus("모집완료");
		}

		courseDao.updateCourse(course);

	}

	
	public List<RegistrationDto> getAllMyRegistrations(String studentId){
		
		return registrationsDao.getCourseRegDto(studentId);
	}
	
	
	public void caceledCourse(int regNo, String studentId) {
		
		AcademyCourseRegistration registration = registrationsDao.getCourseReg(regNo, studentId);
		registration.setRegCanceled("Y");
		registrationsDao.updateRegistraion(registration);
		
		AcademyCourse course = new AcademyCourse();
		course.setCourseReqCnt(course.getCourseReqCnt() - 1);
		if(course.getStatus() == "모집완료") {
			course.setStatus("모집중");
		}
		courseDao.updateCourse(course);
	}
	
}
